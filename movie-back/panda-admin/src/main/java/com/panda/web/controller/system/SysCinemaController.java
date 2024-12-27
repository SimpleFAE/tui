package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;
import com.panda.system.domin.SysCinema;
import com.panda.system.domin.SysSession;
import com.panda.system.service.impl.SysCinemaServiceImpl;
import com.panda.system.service.impl.SysSessionServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class SysCinemaController extends BaseController {

    // 自动注入电影服务
    @Autowired
    private SysCinemaServiceImpl sysCinemaService;

    // 自动注入场次服务
    @Autowired
    // Declare a SysSessionServiceImpl object
    private SysSessionServiceImpl sysSessionService;

    /**
     * 查询所有电影院的信息
     * @return 返回影院数据的响应结果
     */
    @GetMapping("/sysCinema")
    public ResponseResult findCinema() {
        // 调用服务层方法获取影院数据，并返回结果
        return getResult(sysCinemaService.findCinema());
    }

    /**
     * 更新指定电影院的信息
     * @param sysCinema 需要更新的影院对象
     * @return 更新结果的响应
     */
    @PutMapping("/sysCinema/update")
    public ResponseResult updateCinema(@Validated @RequestBody SysCinema sysCinema) {
        // 调用服务层方法更新影院信息，并返回结果
        return getResult(sysCinemaService.updateCinema(sysCinema));
    }

    /**
     * 根据影院ID（cinemaId）和可选的电影ID（movieId）查询该影院及其放映的场次信息
     * @param cinemaId 影院ID
     * @param movieId 电影ID（可选，如果不提供则返回默认电影的场次）
     * @return 包含影院信息和场次信息的响应结果
     */
    @GetMapping(value = {"/sysCinema/find/{cinemaId}/{movieId}", "/sysCinema/find/{cinemaId}"})
    public ResponseResult findCinemaById(@PathVariable Long cinemaId, @PathVariable(required = false) Long movieId) {
        // 查找指定影院的信息
        SysCinema cinema = sysCinemaService.findCinemaById(cinemaId);

        // 如果没有提供 movieId，则默认选择该影院的第一部电影
        if (movieId == null || movieId == 0) {
            movieId = cinema.getSysMovieList().size() > 0 ? cinema.getSysMovieList().get(0).getMovieId() : 0;
        }

        List<SysSession> sessions = null;
        // 如果提供了有效的 movieId，则查询对应电影的所有放映场次
        if (movieId != null && movieId != 0) {
            sessions = sysSessionService.findSessionByMovieId(movieId);
        }

        // 创建一个响应的 HashMap，存储影院信息和对应的场次数据
        HashMap<String, Object> response = new HashMap<>();
        response.put("cinema", cinema); // 放入影院信息
        response.put("sessions", sessions); // 放入该电影的所有场次信息

        // 返回封装好的响应结果
        return getResult(response);
    }

}
