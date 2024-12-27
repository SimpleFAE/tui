package com.panda.web.controller.system;

import com.panda.common.constant.MovieRankingList;
import com.panda.common.response.ResponseResult;
import com.panda.system.domin.Review;
import com.panda.system.domin.ReviewRequest;
import com.panda.system.domin.SysMovie;
import com.panda.system.domin.vo.MovieBoxOfficeVO;
import com.panda.system.domin.vo.SysMovieVo;
import com.panda.system.service.impl.SysMovieServiceImpl;
import com.panda.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j  // Lombok提供的注解，用于自动生成一个日志记录器（log），可以简化日志的记录操作。
@RestController  // Spring的注解，表示该类为控制器，并且每个方法的返回值会自动转化为JSON格式的响应。
public class SysMovieController extends BaseController {  // 定义一个公开的控制器类，用于处理与电影相关的请求。继承BaseController类以便复用通用方法。

    @Autowired  // Spring的注解，表示自动注入一个SysMovieServiceImpl实例，这样Spring会在容器中查找并自动注入该服务实例。
    private SysMovieServiceImpl sysMovieService;  // 声明一个SysMovieServiceImpl类型的成员变量，用来处理电影的相关业务逻辑。

    // 获取新上映的电影列表
    @GetMapping("/newReleases")  // 这个注解指定了处理GET请求，并且URL为/newReleases，表示获取新上映的电影。
    public ResponseEntity<List<SysMovie>> getNewReleases() {  // 方法返回ResponseEntity类型，包含一个电影列表（List<SysMovie>）。
        List<SysMovie> newReleases = sysMovieService.getNewReleases();  // 调用SysMovieService中的getNewReleases方法，获取新上映的电影列表。
        return new ResponseEntity<>(newReleases, HttpStatus.OK);  // 将电影列表返回，状态码为200（OK）。
    }

    // 获取推荐电影列表（按评分从高到低排序）
    @GetMapping("/topRatedList")  // 处理GET请求，URL为/topRatedList，表示获取推荐的电影。
    public ResponseEntity<List<SysMovie>> getTopRatedMovies() {  // 返回一个电影列表，按评分排序。
        List<SysMovie> movies = sysMovieService.getTopRatedMovies();  // 调用SysMovieService中的getTopRatedMovies方法，获取按评分排序的电影列表。

        if (movies == null || movies.isEmpty()) {  // 判断如果返回的电影列表为空或null，说明没有找到推荐电影。
            log.warn("未找到推荐电影。");  // 记录一条警告日志，表示未找到推荐电影。
            return ResponseEntity.noContent().build();  // 返回204状态码，表示没有内容。
        }
        return ResponseEntity.ok(movies);  // 如果有推荐电影，返回200状态码和电影列表。
    }

    // 获取指定电影的评论列表
    @GetMapping("/sysMovie/{movieId}/reviews")  // 处理GET请求，URL为/sysMovie/{movieId}/reviews，表示获取某电影的评论。
    public ResponseResult getReviewsByMovieId(  // 返回评论的统一格式ResponseResult，包含评论列表。
                                                @PathVariable Long movieId,  // 从URL中获取电影ID，使用@PathVariable注解。
                                                @RequestParam(value = "sortType", defaultValue = "latest") String sortType) {  // 可选的查询参数，默认为"latest"，用于决定评论排序类型。

        List<Review> reviews = sysMovieService.getReviewsByMovieId(movieId, sortType);  // 调用SysMovieService获取该电影的评论列表，并根据sortType进行排序。
        return getResult(reviews);  // 返回评论列表，封装成统一的ResponseResult格式。
    }

    // 获取电影的平均评分
    @GetMapping("/sysMovie/{movieId}/rating")  // 处理GET请求，URL为/sysMovie/{movieId}/rating，获取电影的评分。
    public ResponseResult getMovieRating(@PathVariable Long movieId) {  // 从URL中获取电影ID。
        Double avgRating = sysMovieService.calculateAverageRating(movieId);  // 调用SysMovieService计算该电影的平均评分。
        return ResponseResult.success(avgRating != null ? avgRating : 0.0);  // 如果评分存在，返回评分；否则返回0.0。
    }

    // 提交评论和评分
    @PostMapping("/sysMovie/{movieId}/reviews")  // 处理POST请求，URL为/sysMovie/{movieId}/reviews，用于提交评论和评分。
    public ResponseResult submitReview(@PathVariable Long movieId,  // 从URL中获取电影ID。
                                       @RequestBody ReviewRequest reviewRequest) {  // 使用@RequestBody注解从请求体中获取评论请求数据。
        sysMovieService.submitReview(movieId, reviewRequest.getUserId(), reviewRequest.getRating(), reviewRequest.getComment());  // 调用服务层方法提交评论和评分。
        return ResponseResult.success("Review submitted successfully");  // 返回成功响应，表示评论已提交。
    }

    // 获取电影列表
    @GetMapping("/sysMovie/find")  // 处理GET请求，URL为/sysMovie/find，表示查询电影列表。
    public ResponseResult findAllMovies(SysMovieVo sysMovieVo) {  // 通过SysMovieVo封装查询条件。
        startPage();  // 调用BaseController中的分页方法，启动分页查询。
        List<SysMovie> data = sysMovieService.findAllMovies(sysMovieVo);  // 获取电影列表数据。
        return getResult(data);  // 返回查询结果，封装为统一格式的ResponseResult。
    }

    // 根据电影ID获取电影详情
    @GetMapping("/sysMovie/find/{id}")  // 处理GET请求，URL为/sysMovie/find/{id}，根据电影ID查询电影。
    public ResponseResult findMovieById(@PathVariable Long id) {  // 从URL中获取电影ID。
        return getResult(sysMovieService.findMovieById(id));  // 调用服务层方法获取电影详情并返回。
    }

    // 添加新电影
    @PostMapping("/sysMovie")  // 处理POST请求，URL为/sysMovie，用于添加新电影。
    public ResponseResult addMovie(@Validated @RequestBody SysMovie sysMovie) {  // 使用@Validated注解对请求体中的电影数据进行验证。
        return getResult(sysMovieService.addMovie(sysMovie));  // 调用服务层方法添加电影，并返回操作结果。
    }

    // 更新电影信息
    @PutMapping("/sysMovie")  // 处理PUT请求，URL为/sysMovie，用于更新电影信息。
    public ResponseResult updateMovie(@Validated @RequestBody SysMovie sysMovie) {  // 使用@Validated注解对请求体中的更新数据进行验证。
        return getResult(sysMovieService.updateMovie(sysMovie));  // 调用服务层方法更新电影，并返回操作结果。
    }

    // 删除电影
    @DeleteMapping("/sysMovie/{ids}")  // 处理DELETE请求，URL为/sysMovie/{ids}，用于删除电影。
    public ResponseResult deleteMovie(@PathVariable Long[] ids) {  // 从URL中获取电影ID数组，用于批量删除。
        return getResult(sysMovieService.deleteMovie(ids));  // 调用服务层方法删除电影，并返回操作结果。
    }

    // 获取电影排行榜
    @GetMapping("/sysMovie/find/rankingList/{listId}")  // 处理GET请求，URL为/sysMovie/find/rankingList/{listId}，根据榜单ID获取电影排行。
    public ResponseResult findRankingList(@PathVariable Integer listId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {  // 从URL中获取榜单ID。
        if (listId <= 0 || listId > 4) {  // 判断榜单ID是否有效，当前仅支持4个榜单。
            return ResponseResult.error("抱歉，暂时只支持4种榜单，id为[1,4]");  // 如果无效，返回错误信息。
        }
        // 动态调用对应的榜单方法
        Method getList = sysMovieService.getClass().getMethod(MovieRankingList.listNames[listId - 1]);  // 通过反射获取对应的榜单方法。
        startPage();  // 启动分页查询。
        List<SysMovie> data = (List<SysMovie>) getList.invoke(sysMovieService);  // 调用方法获取对应的排行榜数据。
        return getResult(data);  // 返回查询结果。
    }

    // 获取票房统计数据
    @GetMapping("/sysMovie/boxOffice")  // 处理GET请求，URL为/sysMovie/boxOffice，获取票房统计数据。
    public ResponseResult getBoxOfficeStatistics() {  // 返回统一格式的票房统计数据。
        List<MovieBoxOfficeVO> statistics = sysMovieService.getBoxOfficeStatistics();  // 调用服务层方法获取票房数据。
        return getResult(statistics);  // 返回查询结果。
    }

    // 检查电影是否存在
    @GetMapping("/sysMovie/checkExists")  // 处理GET请求，URL为/sysMovie/checkExists，检查电影是否存在。
    public ResponseResult checkMovieExists(@RequestParam String movieName) {  // 从请求参数中获取电影名称。
        boolean exists = sysMovieService.checkMovieExists(movieName);  // 调用服务层方法检查电影是否存在。
        return ResponseResult.success(exists);  // 返回检查结果，存在为true，不存在为false。
    }
}

