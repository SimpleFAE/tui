package com.panda.system.service;

import com.panda.system.domin.Review;  // 引入评论类，用于表示电影的评论
import com.panda.system.domin.SysMovie;  // 引入电影类，用于表示电影数据
import com.panda.system.domin.vo.MovieBoxOfficeVO;  // 引入票房统计数据类，用于表示票房统计
import com.panda.system.domin.vo.SysMovieVo;  // 引入电影查询条件类，用于分页和筛选电影

import java.util.List;  // 引入集合类 List，用于存储多个对象

public interface SysMovieService {  // 定义一个接口，所有实现该接口的类都需要实现这些方法

    // 查找符合条件的所有电影列表
    List<SysMovie> findAllMovies(SysMovieVo sysMovieVo);  // 通过 SysMovieVo 提供的查询条件返回电影列表

    // 根据电影ID查找电影详情
    SysMovie findMovieById(Long id);  // 通过电影的唯一ID返回电影的完整信息

    // 查找电影信息（仅包含基本信息，不包含其他相关信息）
    SysMovie findOneMovie(Long id);  // 仅返回电影的基本信息，可能是获取不包含评论等附加信息的电影数据

    // 添加一部电影
    int addMovie(SysMovie sysMovie);  // 向数据库中添加一部新的电影，返回受影响的行数

    // 更新电影信息
    int updateMovie(SysMovie sysMovie);  // 更新电影的信息，返回受影响的行数

    // 删除电影
    int deleteMovie(Long[] ids);  // 根据提供的电影ID数组删除对应的电影，返回受影响的行数

    // 获取票房统计数据
    List<MovieBoxOfficeVO> getBoxOfficeStatistics();  // 返回包含票房统计信息的数据列表

    // 获取单个影院上映的所有电影信息
//    List<SysMovie> findByCinemaId(Long id);  // 该方法被注释掉了，原本是根据影院ID查找该影院上映的所有电影（如果需要可以实现）

    // 获取各种榜单
    List<SysMovie> totalBoxOfficeList();  // 获取总票房榜（即所有电影的票房排名）

    List<SysMovie> domesticBoxOfficeList();  // 获取国内票房榜（按票房排序的国内电影）

    List<SysMovie> foreignBoxOfficeList();  // 获取国外票房榜（按票房排序的国外电影）

    // 电影评论相关的方法
    List<Review> getReviewsByMovieId(Long movieId);  // 根据电影ID获取该电影的所有评论

    void submitReview(Long movieId, Long userId, Integer rating, String comment);  // 提交电影评论，包含评分和评论内容

    // 根据排序方式获取电影的评论
    List<Review> getReviewsByMovieId(Long movieId, String sortType);  // 根据评论的不同排序方式（如按日期、按评分等）获取电影评论

    // 计算电影的平均评分
    Double calculateAverageRating(Long movieId);  // 计算电影的平均评分，返回一个 Double 类型的平均评分值

    // 获取评分最高的电影列表
    List<SysMovie> getTopRatedMovies();  // 获取评分从高到低排序的电影列表

    // 获取新上映的电影
    List<SysMovie> getNewReleases();  // 获取最新上映的电影列表

    // 检查电影是否已经存在
    boolean checkMovieExists(String movieName);  // 检查给定名称的电影是否已经存在，返回布尔值（true 表示存在，false 表示不存在）
}
/*
详细解释
接口 (Interface)
public interface SysMovieService:
这是一个接口，它定义了一组方法，这些方法提供电影管理服务。接口不实现具体的业务逻辑，而是由实现该接口的类提供具体实现。

泛型 (Generic)
List<SysMovie>, List<Review>, List<MovieBoxOfficeVO> 等:
使用了 List 泛型容器，表示可以存储多个同类型的对象。例如，List<SysMovie> 表示一个存储 SysMovie 对象的列表。泛型是 Java 中的一种机制，用于在编译时保证类型安全。

方法签名 (Method Signature)

List<SysMovie> findAllMovies(SysMovieVo sysMovieVo)
这个方法接受一个 SysMovieVo 类型的参数，它可能包含一些查询条件（例如电影的分类、上映时间等），并返回一个电影列表。

SysMovie findMovieById(Long id)
根据电影的 id 查找一部电影，Long id 是电影的唯一标识。

int addMovie(SysMovie sysMovie)
该方法接受一个 SysMovie 类型的对象，表示要添加的电影，返回一个整数，表示影响的行数（通常是 1 表示成功，0 表示失败）。

void submitReview(Long movieId, Long userId, Integer rating, String comment)
提交评论方法，接受 movieId（电影ID）、userId（用户ID）、rating（评分）和 comment（评论内容）作为参数，方法不返回值。

返回值类型 (Return Type)

List<SysMovie>：表示返回多个 SysMovie 对象的列表，常用于返回多个电影。
SysMovie：表示一个电影对象，通常用于返回单个电影的详细信息。
int：通常表示方法操作的影响行数。例如，插入、更新或删除电影时，返回影响的行数。
Double：用于返回浮动的评分，例如电影的平均评分。
boolean：表示一个布尔值，通常用于返回是否存在某种条件，如电影是否已存在。
注解 (Annotations)

@Override：用来标记方法重写父类的方法，确保子类提供了正确的实现。
@Param("movieName")：用于 MyBatis 框架，在 SQL 查询时绑定方法参数到 SQL 语句中的命名参数。
分页和排序

分页 (Pagination)：在实际使用中，某些方法如 findAllMovies 可能涉及分页查询。分页通常通过传入一个分页对象（如 SysMovieVo）来实现。该对象可能包含页码、每页条数、排序条件等。
排序 (Sorting)：如 getReviewsByMovieId(Long movieId, String sortType)，可以根据不同的排序方式（按日期、评分高低等）返回评论。
核心概念总结
电影管理：接口提供了对电影的基本 CRUD 操作（创建、读取、更新、删除），包括查找电影、添加电影、删除电影和更新电影。
票房数据：提供了获取电影的票房信息，包括总票房、国内票房和国外票房。
评论系统：允许用户对电影进行评分和评论，并支持根据不同的排序方式查看评论。
评分系统：通过方法 calculateAverageRating 计算电影的平均评分，并根据评分来推荐电影。
电影查重：通过 checkMovieExists 方法检查数据库中是否已经存在某部电影。
这种设计能够高效地管理电影数据、处理电影评论、进行评分计算，并提供多种电影排行榜和统计数据。
 */