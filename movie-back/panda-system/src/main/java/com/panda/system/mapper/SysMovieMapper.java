package com.panda.system.mapper;

import com.panda.system.domin.Review;
import com.panda.system.domin.SysMovie;
import com.panda.system.domin.vo.MovieBoxOfficeVO;
import com.panda.system.domin.vo.SysMovieVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMovieMapper {

    /**
     * 按照传入对象的参数进行条件分页查询
     * @param sysMovieVo 查询条件的封装对象，包含电影的各种筛选条件
     * @return 返回符合条件的电影列表
     */
    List<SysMovie> findAllMovies(SysMovieVo sysMovieVo);

    /**
     * 根据电影ID查询电影信息
     * @param id 电影ID
     * @return 返回对应ID的电影信息
     */
    SysMovie findMovieById(Long id);

    /**
     * 查询一个电影的信息，不查询相关的其他信息
     * @param id 电影ID
     * @return 返回指定电影的基本信息（不包含其他相关信息，如评论、评分等）
     */
    SysMovie findOneMovie(Long id);

    /**
     * 插入电影记录
     * @param sysMovie 电影信息对象
     * @return 返回操作影响的行数（1表示成功）
     */
    int addMovie(SysMovie sysMovie);

    /**
     * 更新电影记录
     * @param sysMovie 电影信息对象，包含更新的字段
     * @return 返回操作影响的行数（1表示成功）
     */
    int updateMovie(SysMovie sysMovie);

    /**
     * 删除电影记录
     * @param id 电影ID
     * @return 返回操作影响的行数（1表示成功）
     */
    int deleteMovie(Long id);

    /**
     * 查询某个影院上映的所有电影
     * @param id 影院ID
     * @return 返回指定影院上映的所有电影列表
     */
    List<SysMovie> findMovieByCinemaId(Long id);

    /**
     * 获取总票房榜，按电影的总票房进行排序，获取前几名
     * @return 返回总票房前几名的电影列表
     */
    List<SysMovie> totalBoxOfficeList();

    /**
     * 获取国内票房榜，已上映的国内电影中，按票房排序，取前10名
     * @return 返回前10名的国内票房电影
     */
    List<SysMovie> domesticBoxOfficeList();

    /**
     * 获取国外票房榜，已上映的国外电影中，按票房排序，取前10名
     * @return 返回前10名的国外票房电影
     */
    List<SysMovie> foreignBoxOfficeList();

    /**
     * 获取票房统计数据
     * @return 返回包含票房统计数据的列表
     */
    List<MovieBoxOfficeVO> selectBoxOfficeStatistics();

    /**
     * 获取指定电影的所有评论
     * @param movieId 电影ID
     * @return 返回该电影所有的评论列表
     */
    List<Review> getReviewsByMovieId(Long movieId);

    /**
     * 插入电影的评论
     * @param movieId 电影ID
     * @param userId 用户ID
     * @param rating 用户给电影的评分
     * @param comment 用户的评论内容
     */
    void insertReview(Long movieId, Long userId, Integer rating, String comment);

    /**
     * 计算电影的平均评分
     * @param movieId 电影ID
     * @return 返回该电影的平均评分
     */
    Double calculateAverageRating(Long movieId);

    /**
     * 更新电影的评分
     * @param movieId 电影ID
     * @param avgRating 更新后的平均评分
     */
    void updateMovieRating(Long movieId, Double avgRating);

    /**
     * 获取电影按时间倒序排列的评论
     * @param movieId 电影ID
     * @return 返回该电影按时间倒序排列的评论列表
     */
    List<Review> getReviewsByDateDesc(@Param("movieId") Long movieId);

    /**
     * 获取电影按评分从高到低排列的评论
     * @param movieId 电影ID
     * @return 返回该电影按评分从高到低排列的评论列表
     */
    List<Review> getReviewsByRatingDesc(@Param("movieId") Long movieId);

    /**
     * 获取电影按评分从低到高排列的评论
     * @param movieId 电影ID
     * @return 返回该电影按评分从低到高排列的评论列表
     */
    List<Review> getReviewsByRatingAsc(@Param("movieId") Long movieId);

    /**
     * 获取按评分从高到低的电影列表
     * @return 返回按评分从高到低排序的电影列表
     */
    List<SysMovie> getTopRatedMovies();

    /**
     * 获取新上映的电影列表
     * @return 返回新上映的电影列表
     */
    List<SysMovie> findNewReleases();

    /**
     * 检查电影是否已存在
     * @param movieName 电影名称
     * @return 返回电影是否存在的布尔值（true表示存在，false表示不存在）
     */
    int checkMovieExists(@Param("movieName") String movieName);
}
