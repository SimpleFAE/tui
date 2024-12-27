package com.panda.system.service;

import com.panda.system.domin.SysRole;
import java.util.List;

/**
 * 系统角色服务接口
 * 该接口定义了与角色相关的业务逻辑操作，包括查询、添加、更新、删除角色以及分配权限等。
 */
public interface SysRoleService {

    /**
     * 查询所有角色
     *
     * @return 返回一个包含所有角色信息的列表
     */
    List<SysRole> findAllRoles();

    /**
     * 根据角色ID查询角色信息
     *
     * @param id 角色ID
     * @return 返回对应ID的角色信息
     */
    SysRole findRoleById(Long id);

    /**
     * 添加新的角色
     *
     * @param sysRole 要添加的角色对象，包含角色的所有信息
     * @return 返回影响的行数，成功时返回1，失败时返回0
     */
    int addRole(SysRole sysRole);

    /**
     * 更新已有角色的信息
     *
     * @param sysRole 更新后的角色对象，包含更新后的角色信息
     * @return 返回影响的行数，成功时返回1，失败时返回0
     */
    int updateRole(SysRole sysRole);

    /**
     * 删除角色
     *
     * @param ids 要删除的角色ID数组
     * @return 返回影响的行数，成功时返回删除的数量，失败时返回0
     */
    int deleteRole(Long[] ids);

    /**
     * 分配权限给角色
     *
     * @param roleId 角色ID
     * @param resourceIds 要分配的资源ID数组，这些资源ID表示角色可以访问的功能或数据
     * @return 返回影响的行数，成功时返回1，失败时返回0
     */
    int allotRight(Long roleId, Long[] resourceIds);
}
/*
详细注释说明：
findAllRoles(): 获取系统中所有角色的信息，返回一个角色列表。
findRoleById(Long id): 根据提供的角色ID，查找并返回对应角色的详细信息。
addRole(SysRole sysRole): 添加一个新的角色，角色信息通过 sysRole 对象传递给方法。成功后会返回1，表示角色添加成功。
updateRole(SysRole sysRole): 更新一个已有的角色，更新后的角色数据通过 sysRole 对象传递。成功后返回更新的行数（通常为1）。
deleteRole(Long[] ids): 删除指定的角色，可以一次删除多个角色。删除的角色ID通过 ids 数组传递。返回删除的角色数量。
allotRight(Long roleId, Long[] resourceIds): 给指定的角色分配资源或权限，roleId 是角色ID，resourceIds 是需要分配的资源ID列表。
这种注释风格可以帮助开发人员快速了解每个方法的功能和使用方式，特别是当项目规模增大时，清晰的注释会显著提高代码的可读性和可维护性。
 */