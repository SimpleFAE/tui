package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;  // 引入自定义的响应结果类
import com.panda.system.domin.SysMovieCategory;  // 引入电影分类实体类
import com.panda.system.domin.SysMovieToCategory;  // 引入电影与分类的关联实体类
import com.panda.system.service.impl.SysMovieCategoryServiceImpl;  // 引入电影分类服务实现类
import com.panda.web.controller.BaseController;  // 引入基类控制器类
import org.springframework.beans.factory.annotation.Autowired;  // 自动注入依赖的注解
import org.springframework.validation.annotation.Validated;  // 验证注解
import org.springframework.web.bind.annotation.*;  // 引入Spring的Web注解

import java.util.List;
import java.util.Map;


@RestController  // 标注为Restful风格的控制器，返回JSON格式的响应
public class SysMovieCategoryController extends BaseController {

    @Autowired  // 自动注入SysMovieCategoryServiceImpl实例
    private SysMovieCategoryServiceImpl sysMovieCategoryService;

    // 查询所有电影分类
    @GetMapping("/sysMovieCategory/find")  // 处理GET请求，获取电影分类
    public ResponseResult findAllCategorys() {
        startPage();  // 启动分页功能（假设在基类中定义）
        List<SysMovieCategory> data = sysMovieCategoryService.findAllCategorys();  // 调用服务方法获取所有分类
        return getResult(data);  // 返回查询结果
    }

    // 根据分类ID查询单个电影分类
    @GetMapping("/sysMovieCategory/{id}")  // 处理GET请求，通过id查找电影分类
    public ResponseResult findCategoryById(@PathVariable Long id) {
        return getResult(sysMovieCategoryService.findCategoryById(id));  // 返回单个分类的信息
    }

    // 添加电影分类
    @PostMapping("/sysMovieCategory")  // 处理POST请求，添加电影分类
    public ResponseResult addCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.addCategory(sysMovieCategory));  // 调用服务层方法添加分类
    }

    // 更新电影分类
    @PutMapping("/sysMovieCategory")  // 处理PUT请求，更新电影分类
    public ResponseResult updateCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.updateCategory(sysMovieCategory));  // 调用服务层方法更新分类
    }

    // 删除电影分类
    @DeleteMapping("/sysMovieCategory/{ids}")  // 处理DELETE请求，删除电影分类
    public ResponseResult deleteCategory(@PathVariable Long[] ids) {
        return getResult(sysMovieCategoryService.deleteCategory(ids));  // 调用服务层方法删除分类
    }

    // 将电影与分类关联
    @PostMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")  // 处理POST请求，电影与分类的关联
    public ResponseResult addMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.addMovieToCategory(new SysMovieToCategory(movieId, movieCategoryId)));  // 调用服务层方法进行关联
    }

    // 删除电影与分类的关联
    @DeleteMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")  // 处理DELETE请求，删除电影与分类的关联
    public ResponseResult deleteMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.deleteMovieToCategory(new SysMovieToCategory(movieId, movieCategoryId)));  // 调用服务层方法删除关联
    }

    // 获取电影分类的统计信息
    @GetMapping("/sysMovieCategory/statistics")  // 处理GET请求，获取分类统计信息
    public ResponseResult getCategoryStatistics() {
        List<Map<String, Object>> statistics = sysMovieCategoryService.getCategoryStatistics();  // 调用服务方法获取统计数据
        return getResult(statistics);  // 返回统计数据
    }
}
/*
代码详细解释：
@RestController: 这个注解表明这是一个Spring MVC的控制器，且它的所有方法都会返回JSON格式的响应。

@Autowired: Spring自动注入服务类实例到控制器中，这里注入了SysMovieCategoryServiceImpl，用于处理与电影分类相关的业务逻辑。

@GetMapping、@PostMapping、@PutMapping、@DeleteMapping: 这些注解分别对应HTTP的GET、POST、PUT、DELETE请求，用于处理对应的操作。

@PathVariable: 用于提取URL中的路径参数，例如/sysMovieCategory/{id}中的id。

@RequestBody: 用于绑定请求体中的JSON数据到方法的参数对象中。

ResponseResult: 这是一个自定义的响应结果类，通常用来统一API的响应格式。getResult()是控制器中的一个方法，用来封装查询或操作的结果。

startPage(): 这个方法可能在基类BaseController中定义，用于处理分页逻辑。它的作用通常是设置分页参数，准备分页查询。

电影与分类关联: SysMovieToCategory类用来表示电影与分类之间的关系，两个路径参数movieId和movieCategoryId表示电影与分类的ID。

主要功能：
增、删、改、查电影分类。
电影与分类的关联与解除。
获取分类的统计信息。
这段代码是典型的Spring MVC应用，控制器负责接收请求、调用服务层处理业务逻辑、返回响应结果。
 */