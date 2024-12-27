package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;
import com.panda.system.domin.SysSession;
import com.panda.system.domin.vo.SysSessionVo;
import com.panda.system.service.impl.SysSessionServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 声明这是一个REST风格的控制器，适用于接收HTTP请求并返回JSON响应
public class SysSessionController extends BaseController { // 继承自BaseController，BaseController可能包含一些通用的方法或属性

    @Autowired // 自动注入SysSessionServiceImpl实例
    private SysSessionServiceImpl sysSessionService; // SysSessionServiceImpl用于处理与SysSession相关的业务逻辑

    /**
     * 根据SysSessionVo中的条件查询所有场次信息
     * 在调用时，前端可以通过SysSessionVo传递查询条件，例如电影名称、放映时间等。
     * 另外，如果前台购票部分需要查询大量数据，建议设置较大的`pageSize`来分页显示更多数据。
     *
     * @param sysSessionVo 查询条件封装对象，包含了电影、时间等筛选条件
     * @return 包含查询结果的ResponseResult对象，其中封装了查询的场次信息
     */
    @GetMapping("/sysSession") // 处理GET请求，路径为/sysSession
    public ResponseResult findByVo(SysSessionVo sysSessionVo) {
        startPage(); // 调用分页方法，假设在BaseController中定义。用于设置分页参数（如页码、每页数量等）
        List<SysSession> list = sysSessionService.findByVo(sysSessionVo); // 调用服务层方法，查询符合条件的场次列表
        return getResult(list); // 调用getResult()方法，可能是用于处理结果并返回统一格式的ResponseResult对象
    }

    /**
     * 根据场次ID查询场次信息
     * 该方法主要用于查询具体场次的详细信息，调用之前系统可能会执行某些预处理操作（如取消超时订单等）。
     *
     * @param id 场次ID，用于唯一标识要查询的场次
     * @return 包含查询结果的ResponseResult对象，返回场次详细信息
     */
    @GetMapping("/sysSession/find/{id}") // 处理GET请求，路径为/sysSession/find/{id}
    public ResponseResult findSessionById(@PathVariable Long id) {
        return getResult(sysSessionService.findSessionById(id)); // 调用服务层的查询方法，根据ID查询场次信息，并返回封装结果
    }

    /**
     * 根据电影ID或放映厅ID查询场次信息，并判断是否可以编辑
     * 该方法不仅查询场次信息，还会结合电影ID或放映厅ID判断该场次是否可编辑。
     * 注意：方法命名可能不够清晰，最好重命名为`checkIfEditable`等更具描述性的方法名。
     *
     * @param sysSession 包含了电影ID或放映厅ID的SysSession对象，作为查询条件
     * @return 包含查询结果的ResponseResult对象，返回是否可以编辑的信息
     */
    @GetMapping("/sysSession/isAbleEdit") // 处理GET请求，路径为/sysSession/isAbleEdit
    public ResponseResult findSessionByMovieIdOrHallId(SysSession sysSession) {
        return getResult(sysSessionService.findSessionByMovieIdOrHallId(sysSession)); // 调用服务层方法，根据电影ID或放映厅ID查询场次信息并返回结果
    }

    /**
     * 添加新的场次信息
     * 该方法用于接收前端传入的场次信息并将其添加到数据库中。
     *
     * @param sysSession 要添加的场次信息，作为请求体传递过来
     * @return 包含操作结果的ResponseResult对象，表示场次添加是否成功
     */
    @PostMapping("/sysSession") // 处理POST请求，路径为/sysSession
    public ResponseResult addSession(@RequestBody SysSession sysSession) {
        return getResult(sysSessionService.addSession(sysSession)); // 调用服务层方法将场次信息添加到数据库，并返回操作结果
    }

    /**
     * 更新场次信息
     * 该方法用于接收前端传入的更新后的场次信息，并更新对应的数据库记录。
     *
     * @param sysSession 更新后的场次信息，作为请求体传递过来
     * @return 包含操作结果的ResponseResult对象，表示场次更新是否成功
     */
    @PutMapping("/sysSession") // 处理PUT请求，路径为/sysSession
    public ResponseResult updateSession(@RequestBody SysSession sysSession) {
        return getResult(sysSessionService.updateSession(sysSession)); // 调用服务层方法更新场次信息，并返回操作结果
    }

    /**
     * 删除指定ID的场次信息
     * 该方法用于根据传入的场次ID数组，删除对应的场次信息。
     * 注意：在前端调用时，可以传递多个ID进行批量删除。
     *
     * @param ids 要删除的场次ID数组
     * @return 包含操作结果的ResponseResult对象，表示删除操作是否成功
     */
    @DeleteMapping("/sysSession/{ids}") // 处理DELETE请求，路径为/sysSession/{ids}，{ids}是路径参数
    public ResponseResult deleteSession(@PathVariable Long[] ids) {
        return getResult(sysSessionService.deleteSession(ids)); // 调用服务层方法删除指定ID的场次信息，并返回操作结果
    }

    // 注意：`getResponseResult()` 和 `startPage()` 方法未在代码中定义，它们可能是 BaseController 或某个工具类中的通用方法
    // `ResponseResult` 是一个封装了响应结果的类，通常包含了状态码、消息和数据等字段
    // 通过这些通用方法，控制器能够高效地进行分页、查询、插入、更新和删除操作，同时统一返回结果格式
}
