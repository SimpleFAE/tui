package com.panda.system.service.impl;

import com.panda.system.domin.SysResource;
import com.panda.system.mapper.SysResourceMapper;
import com.panda.system.service.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    /**
     * 查询所有资源
     * @return 返回所有资源列表
     */
    @Override
    public List<SysResource> findAllResources() {
        return sysResourceMapper.findAllResources();
    }

    /**
     * 查询所有资源，并包含其直接的子菜单
     * @return 返回包含直接子菜单的资源列表
     */
    @Override
    public List<SysResource> findWithChildren() {
        return sysResourceMapper.findWithChildren();
    }

    /**
     * 查询所有资源，并包含完整的父子关系
     * @return 返回包含完整父子关系的资源列表
     */
    @Override
    public List<SysResource> findAllWithAllChildren() {
        return sysResourceMapper.findAllWithAllChildren();
    }

    /**
     * 根据资源ID查询单个资源
     * @param id 资源ID
     * @return 查询到的资源对象
     */
    @Override
    public SysResource findResourceById(Long id) {
        return sysResourceMapper.findResourceById(id);
    }

    /**
     * 添加资源
     * @param sysResource 资源对象
     * @return 插入结果，返回受影响的行数
     */
    @Override
    public int addResource(SysResource sysResource) {
        // 如果父菜单ID为0，设置当前资源的等级为1
        if (sysResource.getParentId() == 0) {
            sysResource.setLevel(1);
        } else {
            // 查找父菜单，并根据父菜单的等级来设置当前资源的等级
            SysResource parent = this.findResourceById(sysResource.getParentId());
            if (parent != null) {
                sysResource.setLevel(parent.getLevel() + 1);
            }
        }
        return sysResourceMapper.addResource(sysResource);
    }

    /**
     * 更新资源
     * @param sysResource 资源对象
     * @return 更新结果，返回受影响的行数
     */
    @Override
    public int updateResource(SysResource sysResource) {
        // 如果父菜单ID为0，设置当前资源的等级为1
        if (sysResource.getParentId() == 0) {
            sysResource.setLevel(1);
        } else {
            // 查找父菜单，并根据父菜单的等级来设置当前资源的等级
            SysResource parent = this.findResourceById(sysResource.getParentId());
            if (parent != null) {
                sysResource.setLevel(parent.getLevel() + 1);
            }
        }
        // 打印资源对象的调试信息
        log.debug(sysResource.toString());
        return sysResourceMapper.updateResource(sysResource);
    }

    /**
     * 删除资源
     * @param ids 资源ID数组
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteResource(Long[] ids) {
        int rows = 0;
        // 批量删除资源
        for (Long id : ids) {
            rows += sysResourceMapper.deleteResource(id);
        }
        return rows;
    }
}
/*
详细解释
1. 类和依赖注入
java
复制代码
@Slf4j
@Service
public class SysResourceServiceImpl implements SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;
@Slf4j：这是 Lombok 提供的注解，用来自动生成 log 对象，方便记录日志。
@Service：这是 Spring 的注解，表示该类是一个服务类，Spring 会将其注册为 Spring 容器中的一个 Bean。
@Autowired：用于自动注入 SysResourceMapper，从而实现与数据库的交互。
2. 方法实现
findAllResources
java
复制代码
@Override
public List<SysResource> findAllResources() {
    return sysResourceMapper.findAllResources();
}
功能：查询所有资源。
实现：调用 SysResourceMapper 中的 findAllResources 方法来从数据库获取所有资源。
findWithChildren
java
复制代码
@Override
public List<SysResource> findWithChildren() {
    return sysResourceMapper.findWithChildren();
}
功能：查询所有资源，并包含每个资源的直接子菜单。
实现：调用 SysResourceMapper 中的 findWithChildren 方法来从数据库获取所有资源，并将每个资源的子菜单也一并查询出来。
findAllWithAllChildren
java
复制代码
@Override
public List<SysResource> findAllWithAllChildren() {
    return sysResourceMapper.findAllWithAllChildren();
}
功能：查询所有资源，并返回完整的父子关系（包括所有级别的子菜单）。
实现：调用 SysResourceMapper 中的 findAllWithAllChildren 方法来获取包含完整父子关系的资源列表。
findResourceById
java
复制代码
@Override
public SysResource findResourceById(Long id) {
    return sysResourceMapper.findResourceById(id);
}
功能：根据资源的 ID 查询单个资源。
实现：调用 SysResourceMapper 中的 findResourceById 方法来获取指定 ID 的资源。
addResource
java
复制代码
@Override
public int addResource(SysResource sysResource) {
    if (sysResource.getParentId() == 0) {
        sysResource.setLevel(1);
    } else {
        SysResource parent = this.findResourceById(sysResource.getParentId());
        if (parent != null) {
            sysResource.setLevel(parent.getLevel() + 1);
        }
    }
    return sysResourceMapper.addResource(sysResource);
}
功能：添加一个新资源。
实现：
如果资源的 parentId 为 0（表示没有父菜单），则设置其 level 为 1。
如果资源有父菜单（parentId 不为 0），则查找父菜单并根据父菜单的 level 设置当前资源的 level。
最后，调用 SysResourceMapper 的 addResource 方法将资源插入数据库。
updateResource
java
复制代码
@Override
public int updateResource(SysResource sysResource) {
    if (sysResource.getParentId() == 0) {
        sysResource.setLevel(1);
    } else {
        SysResource parent = this.findResourceById(sysResource.getParentId());
        if (parent != null) {
            sysResource.setLevel(parent.getLevel() + 1);
        }
    }
    log.debug(sysResource.toString());
    return sysResourceMapper.updateResource(sysResource);
}
功能：更新一个已有的资源。
实现：
如果资源的 parentId 为 0，设置 level 为 1；否则，查找父菜单并根据父菜单的 level 设置当前资源的 level。
通过 log.debug() 打印更新后的资源对象。
调用 SysResourceMapper 的 updateResource 方法更新数据库中的资源。
deleteResource
java
复制代码
@Override
public int deleteResource(Long[] ids) {
    int rows = 0;
    for (Long id : ids) {
        rows += sysResourceMapper.deleteResource(id);
    }
    return rows;
}
功能：删除指定 ID 的资源。
实现：接收一个资源 ID 数组，循环调用 SysResourceMapper 的 deleteResource 方法删除每个资源，返回删除的行数。
总结
该类实现了与 SysResource 资源相关的 CRUD 操作，包括查询、添加、更新和删除。
在添加和更新资源时，系统会根据父菜单的 ID 来决定资源的 level，确保菜单的层级关系正确。
使用 @Autowired 自动注入 Mapper，利用 MyBatis 与数据库进行交互。
 */