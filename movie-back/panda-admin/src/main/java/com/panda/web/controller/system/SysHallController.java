package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;
import com.panda.system.domin.SysHall;
import com.panda.system.service.impl.SysHallServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysHallController 类用于处理与影院大厅 (SysHall) 相关的 HTTP 请求。
 * 提供了增、删、改、查的接口方法，操作数据库中的影院大厅信息。
 */
@RestController
public class SysHallController extends BaseController {

    // 自动注入 SysHallServiceImpl 服务类，用于业务逻辑处理
    @Autowired
    private SysHallServiceImpl sysHallService;

    /**
     * 获取所有影院大厅列表，并进行分页显示。
     *
     * @param sysHall 用于接收查询条件，例如大厅名称、位置等
     * @return 返回分页后的影院大厅列表
     */
    @GetMapping("/sysHall")
    public ResponseResult findAllHalls(SysHall sysHall) {
        // 初始化分页信息，可能会设定当前页和每页条数
        startPage();

        // 调用 SysHallServiceImpl 的 findAllHalls 方法获取影院大厅列表，并返回结果
        return getResult(sysHallService.findAllHalls(sysHall));
    }

    /**
     * 根据影院大厅 ID 获取指定的影院大厅信息。
     *
     * @param hallId 影院大厅的 ID
     * @return 返回指定 hallId 的影院大厅信息
     */
    @GetMapping("/sysHall/{hallId}")
    public ResponseResult findHallById(@PathVariable Long hallId) {
        // 创建一个 SysHall 对象，并设置 hallId 进行查询
        SysHall sysHall = new SysHall();
        sysHall.setHallId(hallId);  // 设置 hallId 来查询指定的大厅

        // 调用 SysHallServiceImpl 的 findHallById 方法，根据 hallId 获取具体的影院大厅信息
        return getResult(sysHallService.findHallById(sysHall));
    }

    /**
     * 添加一个新的影院大厅信息。
     *
     * @param sysHall 包含影院大厅信息的 SysHall 对象
     * @return 返回添加结果
     */
    @PostMapping("/sysHall")
    public ResponseResult addHall(@Validated @RequestBody SysHall sysHall) {
        // 调用 SysHallServiceImpl 的 addHall 方法，将新影院大厅信息保存到数据库
        return getResult(sysHallService.addHall(sysHall));
    }

    /**
     * 更新一个已有的影院大厅信息。
     *
     * @param sysHall 包含更新信息的 SysHall 对象
     * @return 返回更新操作的影响行数
     */
    @PutMapping("/sysHall")
    public ResponseResult updateHall(@Validated @RequestBody SysHall sysHall) {
        // 调用 SysHallServiceImpl 的 updateHall 方法更新影院大厅信息
        int rows = sysHallService.updateHall(sysHall);

        // 返回更新的结果，影响的行数
        return getResult(rows);
    }

    /**
     * 批量删除影院大厅信息。
     *
     * @param sysHalls 需要删除的多个 SysHall 对象
     * @return 返回删除操作的结果
     */
    @PostMapping("/sysHall/delete")
    public ResponseResult deleteHall(@RequestBody SysHall[] sysHalls) {
        // 调用 SysHallServiceImpl 的 deleteHall 方法批量删除影院大厅
        return getResult(sysHallService.deleteHall(sysHalls));
    }
}
/*
代码解释：
类注释：

该类是一个控制器类，处理所有关于影院大厅的请求（增、删、改、查）。使用 @RestController 注解表明这是一个 Spring MVC 控制器，返回的数据会自动转为 JSON 格式。
成员变量：

SysHallServiceImpl sysHallService：服务层实例，用于处理业务逻辑。
方法注释：

每个方法的注释详细描述了该方法的功能、输入参数和返回值。例如，findAllHalls 方法用于获取所有影院大厅，支持分页功能；findHallById 方法用于根据 hallId 获取指定影院大厅的信息；addHall 用于添加一个新的影院大厅等。
方法逻辑：

在方法体内，首先会执行一些如分页设置（startPage()）或者构建查询条件（如 sysHall.setHallId(hallId)）等，然后调用服务层的相应方法处理业务逻辑，最后返回一个统一格式的响应结果。
额外说明：
startPage() 是一个分页功能的初始化方法，通常在查询方法前调用，用于设置分页参数（如当前页、每页条数）。
getResult() 是一个统一的响应封装方法，返回一个标准格式的响应结果（可能包括数据、成功标志、错误信息等）
 */