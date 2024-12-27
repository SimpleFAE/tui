package com.panda.system.service;

import com.panda.system.domin.SysResource;

import java.util.List;

/**
 * SysResourceService 接口，定义了资源管理相关的业务逻辑方法。
 * 该接口的实现类将负责资源（如菜单）增、删、改、查等操作的具体实现。
 */
public interface SysResourceService {

    /**
     * 查询所有资源
     *
     * 该方法将从数据库中查询所有资源记录，并返回资源列表。它通常用于列出所有资源。
     *
     * @return 返回所有资源的列表（List<SysResource>）。
     *         如果没有资源，返回一个空列表。
     */
    List<SysResource> findAllResources();

    /**
     * 查询所有资源并包含其直接子菜单
     *
     * 该方法将返回所有资源以及每个资源的直接子菜单。
     * 这适用于显示带有子菜单的树状结构，父菜单包含直接的子菜单。
     *
     * @return 返回所有资源及其直接子菜单的列表。
     *         每个资源的 `children` 属性将包含直接子菜单的列表。
     */
    List<SysResource> findWithChildren();

    /**
     * 查询所有资源并返回完整的父子关系
     *
     * 该方法将从数据库中查询所有资源，并返回包含所有父子菜单层级关系的数据。
     * 它通常用于生成一个完整的资源树，其中每个菜单都会包含它的子菜单。
     *
     * @return 返回所有资源及其完整的父子关系（即每个资源包含它的所有子资源）。
     *         该列表将是一个深度嵌套的结构，支持多层子菜单。
     */
    List<SysResource> findAllWithAllChildren();

    /**
     * 根据资源 ID 查询单个资源
     *
     * 该方法根据传入的资源 ID 查询对应的资源。
     * 如果该 ID 存在，返回对应的资源信息；否则，返回 null。
     *
     * @param id 资源的唯一标识符。
     * @return 返回查询到的资源对象（SysResource）。
     *         如果资源不存在，返回 null。
     */
    SysResource findResourceById(Long id);

    /**
     * 添加新的资源
     *
     * 该方法用于向数据库中插入新的资源。方法会根据资源的父 ID 设置资源的层级（`level`）。
     * 如果资源没有父菜单，`level` 将为 1；否则，`level` 会根据父菜单的层级加 1 来设置。
     *
     * @param sysResource 需要添加的资源对象（SysResource）。
     *                    该对象包含资源的所有信息，如名称、路径、父菜单 ID 等。
     * @return 返回受影响的行数（int）。成功时返回 1，失败时返回 0。
     */
    int addResource(SysResource sysResource);

    /**
     * 更新资源
     *
     * 该方法用于更新已存在的资源。更新时，系统会根据资源的父 ID 重新计算资源的层级（`level`）。
     *
     * @param sysResource 需要更新的资源对象（SysResource）。该对象包含更新后的资源信息。
     *                    其中包括资源的 ID 和其他需要更新的字段。
     * @return 返回受影响的行数（int）。成功时返回 1，失败时返回 0。
     */
    int updateResource(SysResource sysResource);

    /**
     * 删除资源
     *
     * 该方法用于删除指定的资源。方法接受一个资源 ID 数组，批量删除资源。
     * 如果某个 ID 对应的资源不存在，删除操作会忽略该 ID。
     *
     * @param ids 需要删除的资源 ID 数组（Long[]）。每个 ID 对应一个资源。
     * @return 返回删除操作影响的行数（int）。即删除成功的资源数量。
     */
    int deleteResource(Long[] ids);
}
/*
详细解释
1. findAllResources 方法
功能：该方法用于查询数据库中所有的资源数据，并返回这些资源的列表。这个列表包含了所有资源，但不涉及父子关系。
返回值：返回一个包含所有资源对象的列表（List<SysResource>）。如果没有资源记录，返回空列表。
2. findWithChildren 方法
功能：这个方法查询所有资源，同时包含每个资源的直接子菜单。它通常用于树形结构展示，例如显示菜单及其直接子菜单。
返回值：返回一个列表，其中每个资源对象包含它的子菜单（如果有的话）。通过 SysResource 的 children 属性，可以获取该资源的所有直接子资源。
3. findAllWithAllChildren 方法
功能：这个方法查询所有资源，并返回完整的父子菜单层级关系，适用于树形结构展示所有父子关系。
返回值：返回一个资源列表，包含每个资源及其所有子资源。children 属性包含该资源的所有子菜单，不止是直接子菜单。
4. findResourceById 方法
功能：根据资源的唯一 ID 查询指定的资源信息。
参数：
id：资源的唯一标识符，用来查询特定的资源。
返回值：返回一个 SysResource 对象，表示查询到的资源。如果资源 ID 不存在，则返回 null。
5. addResource 方法
功能：该方法用于向数据库中添加新的资源。添加时会自动计算资源的层级（level），取决于父菜单的层级。如果资源是根菜单（即 parentId = 0），其层级为 1。
参数：
sysResource：包含新资源信息的 SysResource 对象，应该包括资源的名称、路径、父菜单 ID 等。
返回值：返回受影响的行数（int），通常是 1，表示成功插入一条资源记录。如果失败则返回 0。
6. updateResource 方法
功能：该方法用于更新现有资源的字段值。更新时会重新计算资源的层级。
参数：
sysResource：包含更新后资源信息的 SysResource 对象，应该包括资源的 ID 和其他需要更新的字段。
返回值：返回受影响的行数（int），通常是 1，表示成功更新一条资源记录。如果失败则返回 0。
7. deleteResource 方法
功能：该方法用于批量删除指定的资源。方法接受一个资源 ID 数组，删除这些资源。每个 ID 对应一个资源，资源将被永久删除。
参数：
ids：一个 Long[] 数组，包含要删除的资源 ID。
返回值：返回删除操作影响的行数（int），即删除的资源数量。
总结
SysResourceService 接口提供了与资源（例如菜单）管理相关的业务逻辑方法。
它通过 CRUD 操作实现对资源的管理，包括查询、添加、更新和删除。
在添加和更新资源时，会根据父菜单自动计算资源的层级（level）
 */