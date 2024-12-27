package com.panda.system.service.impl;

import com.panda.system.domin.Review;
import com.panda.system.domin.SysMovie;
import com.panda.system.domin.vo.MovieBoxOfficeVO;
import com.panda.system.domin.vo.SysMovieVo;
import com.panda.system.mapper.SysMovieMapper;
import com.panda.system.service.SysMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j  // 用于生成日志记录
@Service  // 将该类标记为一个 Spring Service，用于业务逻辑处理
public class SysMovieServiceImpl implements SysMovieService {

    @Autowired
    private SysMovieMapper sysMovieMapper;  // 注入 SysMovieMapper 用于与数据库进行交互

    /**
     * 获取新电影列表
     * @return 返回新上映的电影列表
     */
    @Override
    public List<SysMovie> getNewReleases() {
        // 调用 mapper 获取新上映的电影列表
        return sysMovieMapper.findNewReleases();
    }

    /**
     * 获取按评分从高到低的推荐电影列表
     * @return 返回按评分从高到低排序的电影列表
     */
    @Override
    public List<SysMovie> getTopRatedMovies() {
        // 调用 mapper 获取按评分排序的电影列表
        return sysMovieMapper.getTopRatedMovies();
    }

    /**
     * 计算指定电影的平均评分
     * @param movieId 电影ID
     * @return 返回该电影的平均评分
     */
    @Override
    public Double calculateAverageRating(Long movieId) {
        return sysMovieMapper.calculateAverageRating(movieId);
    }

    /**
     * 根据电影ID获取评论列表，支持按照不同排序方式
     * @param movieId 电影ID
     * @param sortType 排序方式（latest、highest、lowest）
     * @return 返回电影的评论列表
     */
    @Override
    public List<Review> getReviewsByMovieId(Long movieId, String sortType) {
        // 根据不同的排序方式返回相应的评论列表
        switch (sortType) {
            case "latest":  // 按照最新的评论排序
                return sysMovieMapper.getReviewsByDateDesc(movieId);
            case "highest":  // 按照评分从高到低排序
                return sysMovieMapper.getReviewsByRatingDesc(movieId);
            case "lowest":  // 按照评分从低到高排序
                return sysMovieMapper.getReviewsByRatingAsc(movieId);
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);  // 如果排序方式无效，则抛出异常
        }
    }

    /**
     * 获取电影的所有评论
     * @param movieId 电影ID
     * @return 返回该电影的所有评论列表
     */
    @Override
    public List<Review> getReviewsByMovieId(Long movieId) {
        // 调用 mapper 获取该电影的所有评论
        return sysMovieMapper.getReviewsByMovieId(movieId);
    }

    /**
     * 提交评论并更新电影评分
     * @param movieId 电影ID
     * @param userId 用户ID
     * @param rating 用户评分
     * @param comment 用户评论内容
     */
    @Override
    public void submitReview(Long movieId, Long userId, Integer rating, String comment) {
        // 插入评论到数据库
        sysMovieMapper.insertReview(movieId, userId, rating, comment);
        log.info("Successfully submitted review for movieId: {}", movieId);  // 打印日志

        // 更新电影评分
        updateMovieRating(movieId);
    }

    /**
     * 更新电影评分
     * @param movieId 电影ID
     */
    private void updateMovieRating(Long movieId) {
        // 计算该电影的新平均评分
        Double avgRating = sysMovieMapper.calculateAverageRating(movieId);
        if (avgRating == null) {
            avgRating = 0.0;  // 如果没有评分，则设置为 0
        }
        sysMovieMapper.updateMovieRating(movieId, avgRating);  // 更新电影的评分
        log.info("Updated movie rating for movieId: {} to {}", movieId, avgRating);  // 打印日志
    }

    /**
     * 获取所有电影的分页列表
     * @param sysMovieVo 包含查询条件的对象
     * @return 返回符合条件的电影列表
     */
    @Override
    public List<SysMovie> findAllMovies(SysMovieVo sysMovieVo) {
        return sysMovieMapper.findAllMovies(sysMovieVo);
    }

    /**
     * 根据电影ID查询电影信息
     * @param id 电影ID
     * @return 返回电影信息
     */
    @Override
    public SysMovie findMovieById(Long id) {
        return sysMovieMapper.findMovieById(id);
    }

    /**
     * 查询一个电影的信息，不包含相关的其他信息
     * @param id 电影ID
     * @return 返回电影的基本信息
     */
    @Override
    public SysMovie findOneMovie(Long id) {
        return sysMovieMapper.findOneMovie(id);
    }

    /**
     * 添加电影
     * @param sysMovie 电影信息对象
     * @return 返回操作结果（影响的行数）
     */
    @Override
    public int addMovie(SysMovie sysMovie) {
        return sysMovieMapper.addMovie(sysMovie);
    }

    /**
     * 更新电影信息
     * @param sysMovie 电影信息对象
     * @return 返回操作结果（影响的行数）
     */
    @Override
    public int updateMovie(SysMovie sysMovie) {
        return sysMovieMapper.updateMovie(sysMovie);
    }

    /**
     * 删除电影记录
     * @param ids 电影ID列表
     * @return 返回删除操作的影响行数
     */
    @Override
    public int deleteMovie(Long[] ids) {
        int rows = 0;
        // 对每个电影ID进行删除操作，统计总影响行数
        for (Long id : ids) {
            rows += sysMovieMapper.deleteMovie(id);
        }
        return rows;
    }

    /**
     * 获取总票房榜
     * @return 返回总票房排序的电影列表
     */
    @Override
    public List<SysMovie> totalBoxOfficeList() {
        return sysMovieMapper.totalBoxOfficeList();
    }

    /**
     * 获取国内票房榜，按票房排序
     * @return 返回国内票房排序的电影列表
     */
    @Override
    public List<SysMovie> domesticBoxOfficeList() {
        return sysMovieMapper.domesticBoxOfficeList();
    }

    /**
     * 获取国外票房榜，按票房排序
     * @return 返回国外票房排序的电影列表
     */
    @Override
    public List<SysMovie> foreignBoxOfficeList() {
        return sysMovieMapper.foreignBoxOfficeList();
    }

    /**
     * 获取票房统计数据
     * @return 返回票房统计数据列表
     */
    @Override
    public List<MovieBoxOfficeVO> getBoxOfficeStatistics() {
        log.info("开始获取票房统计数据");
        try {
            List<MovieBoxOfficeVO> result = sysMovieMapper.selectBoxOfficeStatistics();
            log.info("获取到票房数据: {}", result);
            return result;
        } catch (Exception e) {
            log.error("获取票房统计数据失败", e);  // 捕获并打印日志
            throw e;  // 抛出异常
        }
    }

    /**
     * 检查电影是否已存在
     * @param movieName 电影名称
     * @return 返回电影是否存在的布尔值
     */
    @Override
    public boolean checkMovieExists(String movieName) {
        // 如果返回值大于0，则表示电影存在
        return sysMovieMapper.checkMovieExists(movieName) > 0;
    }
}
