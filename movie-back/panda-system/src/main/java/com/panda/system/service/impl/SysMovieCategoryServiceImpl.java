package com.panda.system.service.impl;

import com.panda.system.domin.SysMovieCategory;  // 引入电影分类实体类
import com.panda.system.domin.SysMovieToCategory;  // 引入电影与分类的关联实体类
import com.panda.system.mapper.SysMovieCategoryMapper;  // 引入Mapper接口
import com.panda.system.service.SysMovieCategoryService;  // 引入服务接口
import org.springframework.beans.factory.annotation.Autowired;  // 自动注入依赖的注解
import org.springframework.stereotype.Service;  // 标记为服务类，Spring管理的组件

import java.util.List;
import java.util.Map;

@Service  // 将该类标记为Spring的服务类，可以被自动扫描和注入
public class SysMovieCategoryServiceImpl implements SysMovieCategoryService {

    @Autowired  // 自动注入SysMovieCategoryMapper实例
    private SysMovieCategoryMapper sysMovieCategoryMapper;

    // 获取所有电影分类
    @Override
    public List<SysMovieCategory> findAllCategorys() {
        return sysMovieCategoryMapper.findAllCategorys();  // 调用Mapper层的方法查询所有电影分类
    }

    // 根据ID查找电影分类
    @Override
    public SysMovieCategory findCategoryById(Long id) {
        return sysMovieCategoryMapper.findCategoryById(id);  // 调用Mapper层的方法根据ID查找电影分类
    }

    // 添加电影分类
    @Override
    public int addCategory(SysMovieCategory sysMovieCategory) {
        return sysMovieCategoryMapper.addCategory(sysMovieCategory);  // 调用Mapper层的方法插入电影分类
    }

    // 更新电影分类
    @Override
    public int updateCategory(SysMovieCategory sysMovieCategory) {
        return sysMovieCategoryMapper.updateCategory(sysMovieCategory);  // 调用Mapper层的方法更新电影分类
    }

    // 删除多个电影分类
    @Override
    public int deleteCategory(Long[] ids) {
        int rows = 0;  // 记录影响的行数
        for (Long id : ids) {  // 遍历所有要删除的ID
            rows += sysMovieCategoryMapper.deleteCategory(id);  // 调用Mapper层的方法删除分类
        }
        return rows;  // 返回删除操作影响的行数
    }

    // 添加电影到分类
    @Override
    public int addMovieToCategory(SysMovieToCategory sysMovieToCategory) {
        return sysMovieCategoryMapper.addMovieToCategory(sysMovieToCategory);  // 调用Mapper层的方法添加电影与分类的关联
    }

    // 删除电影与分类的关联
    @Override
    public int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory) {
        return sysMovieCategoryMapper.deleteMovieToCategory(sysMovieToCategory);  // 调用Mapper层的方法删除电影与分类的关联
    }

    // 获取电影分类的统计信息
    @Override
    public List<Map<String, Object>> getCategoryStatistics() {
        List<Map<String, Object>> result = sysMovieCategoryMapper.getCategoryStatistics();  // 调用Mapper层的方法获取分类统计数据
        System.out.println("Statistics result: " + result);  // 调试日志，打印统计结果
        return result;  // 返回统计数据
    }

}
/*
详细功能说明：
findAllCategorys():

调用 sysMovieCategoryMapper.findAllCategorys() 来获取所有电影分类的列表。
返回一个 List<SysMovieCategory> 类型的结果。
findCategoryById(Long id):

根据传入的 id 查找单个电影分类。
调用 sysMovieCategoryMapper.findCategoryById(id)，返回一个 SysMovieCategory 对象。
addCategory(SysMovieCategory sysMovieCategory):

向数据库中添加一个新的电影分类。
调用 sysMovieCategoryMapper.addCategory()，并返回受影响的行数。
updateCategory(SysMovieCategory sysMovieCategory):

更新电影分类的信息。
调用 sysMovieCategoryMapper.updateCategory()，并返回受影响的行数。
deleteCategory(Long[] ids):

批量删除电影分类。
遍历传入的 ids 数组，调用 sysMovieCategoryMapper.deleteCategory(id) 来删除每个分类。
返回删除操作影响的行数。
addMovieToCategory(SysMovieToCategory sysMovieToCategory):

将电影与分类进行关联。
调用 sysMovieCategoryMapper.addMovieToCategory()，并返回受影响的行数。
deleteMovieToCategory(SysMovieToCategory sysMovieToCategory):

删除电影与分类之间的关联。
调用 sysMovieCategoryMapper.deleteMovieToCategory()，并返回受影响的行数。
getCategoryStatistics():

获取电影分类的统计信息。
调用 sysMovieCategoryMapper.getCategoryStatistics()，返回一个包含统计信息的 List<Map<String, Object>>。
在返回结果之前打印了调试日志 System.out.println("Statistics result: " + result)，用于输出统计结果。
注释说明：
@Autowired: 自动注入 SysMovieCategoryMapper，这是一个 MyBatis Mapper 接口，用于与数据库交互。
@Service: 将该类标记为 Spring 服务类，使其可以被 Spring 容器管理。
int rows: 在 deleteCategory() 方法中，rows 记录了批量删除操作的受影响行数。
List<Map<String, Object>> getCategoryStatistics(): 返回一个包含统计信息的列表，每个 Map 表示一项统计数据。
使用场景：
Controller 层调用: 这个服务类主要是为 Controller 层提供业务处理的实现，Controller 层通过调用这些方法来处理请求。
批量操作: 在删除多个电影分类时，deleteCategory() 方法支持批量删除，依次调用 Mapper 层的删除方法并返回删除的行数。
数据统计: getCategoryStatistics() 可以用于展示电影分类的统计数据，可能在前端显示每个分类下的电影数量或其他统计信息。
总结：
这段代码实现了电影分类相关的业务逻辑，利用 MyBatis 的 Mapper 接口与数据库交互，处理了增、删、改、查和统计等功能。通过将业务逻辑与数据库操作分离，提高了代码的可维护性和可扩展性。
 */