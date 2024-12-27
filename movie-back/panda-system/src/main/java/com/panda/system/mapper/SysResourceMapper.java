package com.panda.system.mapper;

import com.panda.system.domin.SysResource;

import java.util.List;

public interface SysResourceMapper {

    /**
     * 查询所有并包含其父菜单
     *
     * @return 所有资源列表，包含父菜单信息
     */
    List<SysResource> findAllResources();

    /**
     * 查出所有菜单并包含其直接children
     *
     * @return 所有资源列表，包含直接的子菜单
     */
    List<SysResource> findWithChildren();

    /**
     * 查询所有按父子关系的权限
     *
     * @return 所有资源列表，包含完整的父子菜单关系
     */
    List<SysResource> findAllWithAllChildren();

    /**
     * 根据ID查询单个资源
     *
     * @param id 资源ID
     * @return 资源对象
     */
    SysResource findResourceById(Long id);

    /**
     * 添加新资源
     *
     * @param sysResource 资源对象
     * @return 执行结果，通常返回受影响的行数
     */
    int addResource(SysResource sysResource);

    /**
     * 更新已有资源
     *
     * @param sysResource 资源对象
     * @return 执行结果，通常返回受影响的行数
     */
    int updateResource(SysResource sysResource);

    /**
     * 删除指定ID的资源
     *
     * @param id 资源ID
     * @return 执行结果，通常返回受影响的行数
     */
    int deleteResource(Long id);

}
/*
详细解释
1. 接口定义：
SysResourceMapper 是一个 MyBatis 的 Mapper 接口，用于定义对 SysResource 资源的数据库操作。Mapper 接口是 MyBatis 框架与数据库交互的核心，通常与 XML 文件或注解结合使用。此接口主要涉及以下操作：

查询资源
添加、更新、删除资源
2. 方法解释：
findAllResources 方法：
java
复制代码
List<SysResource> findAllResources();
功能：查询所有资源并包含其父菜单信息。这个方法会返回所有的 SysResource 实体，包含每个资源的父菜单信息。假设一个资源是多级菜单的一部分，这个方法能够包含每个菜单的父级菜单。
返回值：List<SysResource>，所有资源对象的列表。
findWithChildren 方法：
java
复制代码
List<SysResource> findWithChildren();
功能：查询所有资源并包含其直接子菜单。这个方法会返回所有的 SysResource 实体，并且每个资源对象包含直接的子资源（子菜单）。这个方法适用于展示一个菜单树结构，只包含每个资源的直接子级。
返回值：List<SysResource>，所有资源对象的列表，其中每个资源对象包含其直接的子菜单。
findAllWithAllChildren 方法：
java
复制代码
List<SysResource> findAllWithAllChildren();
功能：查询所有资源，并包含完整的父子关系。这个方法将返回一个包含所有资源的列表，其中每个资源对象会包含完整的父子菜单关系，形成一个完整的树形结构。
返回值：List<SysResource>，包含完整父子关系的所有资源列表。
findResourceById 方法：
java
复制代码
SysResource findResourceById(Long id);
功能：根据资源的 ID 查询单个资源。传入资源的 ID，返回该资源的详细信息。
参数：Long id，要查询的资源 ID。
返回值：SysResource，查询到的资源对象。
addResource 方法：
java
复制代码
int addResource(SysResource sysResource);
功能：添加一个新资源。这个方法用于向数据库插入一个新的 SysResource 记录。
参数：SysResource sysResource，包含要添加的资源信息。
返回值：int，表示执行结果，通常返回影响的行数（例如，插入成功时返回 1）。
updateResource 方法：
java
复制代码
int updateResource(SysResource sysResource);
功能：更新一个已有的资源。这个方法用于更新数据库中已有的 SysResource 记录。通常会根据资源的 ID 进行更新。
参数：SysResource sysResource，包含要更新的资源信息。
返回值：int，表示执行结果，通常返回影响的行数（例如，更新成功时返回 1）。
deleteResource 方法：
java
复制代码
int deleteResource(Long id);
功能：删除指定 ID 的资源。根据给定的资源 ID 删除对应的资源。
参数：Long id，要删除的资源 ID。
返回值：int，表示执行结果，通常返回影响的行数（例如，删除成功时返回 1）。
3. 总结：
功能：SysResourceMapper 接口定义了与资源相关的 CRUD 操作（查询、添加、更新、删除），并支持层级结构（父子菜单关系）。
查询方法：
findAllResources：查询所有资源。
findWithChildren：查询所有资源及其直接子菜单。
findAllWithAllChildren：查询所有资源及其完整的父子菜单关系。
CRUD 方法：
findResourceById：通过 ID 查询单个资源。
addResource、updateResource、deleteResource：用于添加、更新和删除资源
 */