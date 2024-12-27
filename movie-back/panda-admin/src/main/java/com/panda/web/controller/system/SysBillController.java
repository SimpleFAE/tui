package com.panda.web.controller.system;

import com.panda.common.exception.DataNotFoundException;
import com.panda.common.response.ResponseResult;
import com.panda.common.utils.ApplicationContextUtils;
import com.panda.system.domin.SysBill;
import com.panda.system.domin.SysMovie;
import com.panda.system.domin.SysSession;
import com.panda.system.domin.vo.SysBillVo;
import com.panda.system.service.impl.SysBillServiceImpl;
import com.panda.system.service.impl.SysMovieServiceImpl;
import com.panda.system.service.impl.SysSessionServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysBillController extends BaseController {

    // 注入服务类
    @Autowired
    private SysBillServiceImpl sysBillService;  // 订单服务

    @Autowired
    private SysSessionServiceImpl sysSessionService;  // 场次服务

    @Autowired
    private SysMovieServiceImpl sysMovieService;  // 电影服务

    /**
     * 查询所有电影订单
     * @param sysBill 订单查询条件
     * @return 查询结果
     */
    @GetMapping("/sysBill")
    public ResponseResult findAllBills(SysBill sysBill) {
        startPage();  // 分页查询开始
        // 取消所有超时订单并释放占座资源
        ApplicationContextUtils.getBean("cancelTimeoutBill");  // 获取取消超时订单的Bean并执行
        List<SysBill> data = sysBillService.findAllBills(sysBill);  // 查询订单
        System.out.println("-------------------------------------");
        System.out.println(data);  // 打印订单数据（调试用）
        return getResult(data);  // 返回查询结果
    }

    /**
     * 根据订单ID查询订单信息
     * @param id 订单ID
     * @return 查询结果
     */
    @GetMapping("/sysBill/{id}")
    public ResponseResult findBillById(@PathVariable Long id) {
        return getResult(sysBillService.findBillById(id));  // 根据ID查询订单
    }

    /**
     * 添加电影订单
     * @param sysBillVo 包含订单信息的VO对象
     * @return 添加结果
     */
    @PostMapping("/sysBill")
    public ResponseResult addBill(@Validated @RequestBody SysBillVo sysBillVo) {
        // 获取当前场次信息
        SysSession curSession = sysSessionService.findOneSession(sysBillVo.getSysBill().getSessionId());
        if (curSession == null) {
            throw new DataNotFoundException("添加订单的场次没找到");  // 如果场次不存在，抛出异常
        }
        System.out.println(curSession.getSessionSeats());
        curSession.setSessionSeats(sysBillVo.getSessionSeats());  // 更新场次座位信息

        // 获取新订座位的数量，并更新场次总座位数
        int addSallNums = sysBillVo.getSysBill().getSeats().split(",").length;
        curSession.setSallNums(curSession.getSallNums() + addSallNums);  // 更新场次的总座位数
        // 更新场次座位信息
        sysSessionService.updateSession(curSession);

        // 添加订单
        Object obj = sysBillService.addBill(sysBillVo.getSysBill());
        if (obj instanceof Integer) {
            return getResult((Integer) obj);  // 返回结果
        }

        return getResult(obj);  // 返回结果
    }

    /**
     * 订单支付操作
     * @param sysBill 订单信息
     * @return 支付结果
     */
    @PutMapping("/sysBill")
    public ResponseResult pay(@RequestBody SysBill sysBill) {
        int rows = sysBillService.updateBill(sysBill);  // 更新订单
        if (rows > 0 && sysBill.getPayState()) {  // 如果支付成功
            // 更新场次的座位状态
            SysSession curSession = sysSessionService.findOneSession(sysBill.getSessionId());
            if (curSession == null) {
                throw new DataNotFoundException("支付订单的场次没找到");
            }

            // 更新电影票房
            SysMovie curMovie = sysMovieService.findOneMovie(curSession.getMovieId());
            if (curMovie == null) {
                throw new DataNotFoundException("支付订单的电影没找到");
            }

            // 获取订单的座位数和价格
            int seatNum = sysBill.getSeats().split(",").length;
            double price = curSession.getSessionPrice();

            // 更新电影票房
            curMovie.setMovieBoxOffice(curMovie.getMovieBoxOffice() + seatNum * price);
            sysMovieService.updateMovie(curMovie);  // 更新电影信息
        }
        return getResult(rows);  // 返回更新结果
    }

    /**
     * 取消订单操作
     * @param sysBillVo 订单信息
     * @return 取消结果
     */
    @PutMapping("/sysBill/cancel")
    public ResponseResult cancel(@RequestBody SysBillVo sysBillVo) {
        // 取消订单，更新订单状态
        int rows = sysBillService.updateBill(sysBillVo.getSysBill());
        if (rows > 0 && sysBillVo.getSysBill().getCancelState()) {
            // 订单取消座位不再占用，更新场次的座位状态
            SysSession curSession = sysSessionService.findOneSession(sysBillVo.getSysBill().getSessionId());
            // 获取取消的订单座位数
            int cancelSallNums = sysBillVo.getSysBill().getSeats().split(",").length;
            curSession.setSallNums(curSession.getSallNums() - cancelSallNums);  // 更新总座位数
            if (curSession == null) {
                throw new DataNotFoundException("添加订单的场次没找到");
            }
            curSession.setSessionSeats(sysBillVo.getSessionSeats());  // 更新场次座位状态
            sysSessionService.updateSession(curSession);  // 更新场次信息
        }
        return getResult(rows);  // 返回结果
    }

    /**
     * 删除订单
     * @param ids 订单ID数组
     * @return 删除结果
     */
    @DeleteMapping("/sysBill/{ids}")
    public ResponseResult deleteBill(@PathVariable Long[] ids) {
        return getResult(sysBillService.deleteBill(ids));  // 删除订单
    }
}
/*
代码详细解读：
findAllBills:

用于查询所有订单，并取消所有超时订单。调用 ApplicationContextUtils.getBean("cancelTimeoutBill") 获取 Bean 执行超时订单取消操作。
返回 ResponseResult 封装查询结果。
findBillById:

根据订单 ID 查询指定的电影订单。
addBill:

添加新订单。
首先查询指定的场次 SysSession，更新其座位数，并更新电影票房。
如果场次或电影未找到，抛出 DataNotFoundException。
pay:

更新订单的支付状态。
支付成功后，更新场次的座位状态和电影票房。
cancel:

取消订单，并释放已占用的座位，更新场次座位信息。
同样会通过 DataNotFoundException 抛出异常。
deleteBill:

根据订单 ID 数组删除多个订单。
异常处理：
DataNotFoundException: 用于抛出数据未找到的异常。例如在添加订单时，如果指定的场次或电影不存在，则抛出该异常。
总结：
这个控制器处理了电影票订单的管理功能，包括查询、添加、支付、取消、删除等操作，并与电影和场次信息进行了交互，如更新座位信息、电影票房等。每个方法都进行了详细的异常处理和状态更新，确保数据的一致性和完整性。
 */