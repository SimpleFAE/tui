package com.panda.system.mapper;

import com.panda.system.domin.SysRole;

import java.util.List;


public interface SysRoleMapper {
    // 查询所有角色的列表
    List<SysRole> findAllRoles();
    // 根据ID查找特定的角色
    SysRole findRoleById(Long id);

    // 向数据库添加一个新的角色，并返回添加操作影响的行数
    int addRole(SysRole sysRole);
    // 更新数据库中的现有角色，并返回更新操作影响的行数
    int updateRole(SysRole sysRole);
    // 根据ID删除角色，并返回删除操作影响的行数
    int deleteRole(Long id);

    // 给指定的角色分配权限，'right'在这里指的是权限
    int addRight(Long roleId, Long resourceId);
    // 从指定的角色中移除权限
    int deleteRight(Long roleId, Long resourceId);

    // 查询指定角色拥有的所有三级权限的ID列表
    List<Long> findAllRights(Long roleId);

}


/**interface: 接口，在Java中，接口是一种引用类型，定义了一组方法规范，可以被类实现（implements）。
 SysRoleMapper: 一个自定义的接口名，通常遵循命名约定，表明这个接口与系统角色（System Role）的操作相关。
 List<SysRole>: 一个包含SysRole对象的列表，SysRole是一个假设的类，代表系统中的一个角色。
 findAllRoles: 一个方法名，表示查找所有角色。
 findRoleById: 一个方法名，表示根据ID查找角色。
 addRole: 一个方法名，表示添加新角色。
 sysRole: 一个参数名，代表要添加或更新的角色对象。
 updateRole: 一个方法名，表示更新现有角色。
 deleteRole: 一个方法名，表示删除角色。
 id: 一个参数名，通常指数据库记录的唯一标识符。
 int: Java的基本数据类型之一，表示整数。
 addRight: 一个方法名，表示给角色添加权限。
 roleId: 一个参数名，表示角色的ID。
 resourceId: 一个参数名，表示权限资源的ID。
 deleteRight: 一个方法名，表示从角色中删除权限。
 findAllRights: 一个方法名，表示查询所有权限ID。*/