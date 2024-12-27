package com.panda.system.mapper;

import com.panda.system.domin.SysMovieCategory;  // 引入电影分类实体类
import com.panda.system.domin.SysMovieToCategory;  // 引入电影与分类的关联实体类

import java.util.List;
import java.util.Map;

public interface SysMovieCategoryMapper {

    // 查询所有电影分类
    List<SysMovieCategory> findAllCategorys();
    // 返回所有电影分类的列表。查询数据库中的所有电影分类记录。

    // 根据ID查找单个电影分类
    SysMovieCategory findCategoryById(Long id);
    // 通过电影分类的ID查找对应的电影分类记录。

    // 根据电影ID查找相关分类
    List<SysMovieCategory> findOneMovieCategorys(Long id);
    // 通过电影的ID查询该电影所属的所有分类。

    // 添加电影分类
    int addCategory(SysMovieCategory sysMovieCategory);
    // 向数据库插入一个新的电影分类记录，返回插入操作影响的行数。

    // 更新电影分类
    int updateCategory(SysMovieCategory sysMovieCategory);
    // 更新电影分类信息，返回更新操作影响的行数。

    // 删除电影分类
    int deleteCategory(Long id);
    // 删除指定ID的电影分类，返回删除操作影响的行数。

    // 将电影添加到指定分类
    int addMovieToCategory(SysMovieToCategory sysMovieToCategory);
    // 将电影与分类关联起来，返回关联操作影响的行数。通常用于表示一个电影属于某个分类。

    // 删除电影与分类的关联
    int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory);
    // 删除电影与分类之间的关联关系，返回操作影响的行数。

    // 获取电影分类的统计信息
    List<Map<String, Object>> getCategoryStatistics();
    // 查询电影分类的统计数据，通常返回每个分类下的电影数量等信息，结果通常是一个`Map`集合，里面包含不同字段的统计数据。

}
/*
解释：
findAllCategorys(): 查询所有的电影分类，返回一个SysMovieCategory对象的列表。

findCategoryById(Long id): 根据分类ID查找单个电影分类。

findOneMovieCategorys(Long id): 通过电影ID查询该电影所属于的所有分类。返回一个SysMovieCategory列表，表示电影所属的分类。

addCategory(SysMovieCategory sysMovieCategory): 向数据库插入一条新的电影分类记录。返回一个整数，表示影响的行数（通常是1表示成功，0表示失败）。

updateCategory(SysMovieCategory sysMovieCategory): 更新电影分类信息。返回一个整数，表示影响的行数（通常是1表示更新成功，0表示没有更新）。

deleteCategory(Long id): 删除一个电影分类，传入分类的ID，返回一个整数，表示影响的行数。

addMovieToCategory(SysMovieToCategory sysMovieToCategory): 将一个电影与分类关联。传入SysMovieToCategory对象，表示电影与分类之间的关系。返回影响的行数。

deleteMovieToCategory(SysMovieToCategory sysMovieToCategory): 删除电影与分类之间的关联关系。

getCategoryStatistics(): 查询电影分类的统计信息，返回一个包含统计数据的Map列表。比如，分类下电影的数量、收入统计等。

Mapper与MyBatis的关系：
该接口的作用是定义与数据库交互的SQL操作，但并不直接包含SQL语句。MyBatis会根据这些方法的签名自动生成SQL语句，并将结果映射到对应的对象中。

例如，findAllCategorys()方法会查询数据库中的所有电影分类记录并将其返回为List<SysMovieCategory>对象，MyBatis会根据方法名和参数推断出对应的SQL语句，或者你可以使用XML配置文件编写SQL。

其他注意点：
int 返回值: 许多方法（如addCategory、updateCategory等）返回int类型，表示数据库操作影响的行数。如果返回值是1，通常表示操作成功；如果是0，表示没有任何数据被修改。

Map<String, Object>: getCategoryStatistics返回的是一个Map，其中String表示统计项的名称，Object表示统计值。例如，可以是分类名称和对应电影数量的映射。

这个接口和你前面给出的Controller代码密切相关。Controller层会调用这些Mapper层的方法来执行实际的数据库操作，从而实现电影分类相关的增、删、改、查功能。
 */