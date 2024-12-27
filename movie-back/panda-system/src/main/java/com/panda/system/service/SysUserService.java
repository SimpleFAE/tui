package com.panda.system.service;

import com.panda.system.domin.LoginUser;
import com.panda.system.domin.SysUser;
import com.panda.system.domin.vo.SysUserVo;
import java.util.List;

/**
 * 系统用户服务接口
 * 该接口定义了与用户相关的业务逻辑操作，包括查询、添加、更新、删除用户信息，以及用户登录等操作。
 */
public interface SysUserService {

    /**
     * 查询所有用户
     *
     * @param sysUser 查询条件封装对象，包含可能用于过滤的用户信息（如用户名、角色等）
     * @return 返回一个包含所有用户信息的列表
     */
    List<SysUser> findAllUsers(SysUser sysUser);

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 返回对应ID的用户信息
     */
    SysUser findUserById(Long id);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return 返回对应用户名的用户信息
     */
    SysUser findUserByName(String userName);

    /**
     * 添加新的用户
     *
     * @param sysUser 要添加的用户对象，包含用户的所有信息（如用户名、密码、角色等）
     * @return 返回影响的行数，成功时返回1，失败时返回0
     */
    int addUser(SysUser sysUser);

    /**
     * 更新已有用户的信息
     *
     * @param sysUser 更新后的用户对象，包含更新后的用户信息
     * @return 返回影响的行数，成功时返回1，失败时返回0
     */
    int updateUser(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param ids 要删除的用户ID数组
     * @return 返回影响的行数，成功时返回删除的数量，失败时返回0
     */
    int deleteUser(Long[] ids);

    /**
     * 用户登录
     *
     * @param sysUserVo 登录参数封装对象，包含用户名和密码等信息
     * @return 返回登录用户的详细信息，如果登录成功则返回 `LoginUser` 对象，失败则返回null或抛出异常
     */
    LoginUser login(SysUserVo sysUserVo);

    /**
     * 根据条件查询登录用户信息
     *
     * @param sysUserVo 查询条件封装对象，包含用户名、密码等信息
     * @return 返回登录用户的详细信息
     */
    LoginUser findLoginUser(SysUserVo sysUserVo);

    /**
     * 检查用户名是否唯一
     *
     * @param userName 用户名
     * @param userId 用户ID，用于排除修改时与自身用户名冲突
     * @return 如果用户名唯一，返回 true；如果用户名已存在，返回 false
     */
    boolean isUserNameUnique(String userName, Long userId);
}
/*
详细注释说明：
findAllUsers(SysUser sysUser): 根据传入的 sysUser 查询条件，返回所有符合条件的用户列表。例如，可以根据用户名、角色等信息进行筛选。
findUserById(Long id): 根据用户ID查询用户的详细信息，返回 SysUser 对象。
findUserByName(String userName): 根据用户名查询用户信息，返回对应的 SysUser 对象。
addUser(SysUser sysUser): 添加新用户，sysUser 对象包含了用户的各项信息（如用户名、密码等）。方法返回添加操作的影响行数，通常为1表示成功。
updateUser(SysUser sysUser): 更新已有用户的信息，传入更新后的 sysUser 对象。返回影响的行数，通常为1表示成功。
deleteUser(Long[] ids): 批量删除用户，根据传入的用户ID数组执行删除操作。返回删除的用户数量。
login(SysUserVo sysUserVo): 执行用户登录操作，sysUserVo 是封装了登录所需信息（如用户名和密码）的对象。成功返回登录用户的详细信息，失败返回 null 或抛出异常。
findLoginUser(SysUserVo sysUserVo): 根据封装的登录信息查找登录用户的详细信息，通常用于检查登录状态。
isUserNameUnique(String userName, Long userId): 检查用户名是否唯一。userName 是要检查的用户名，userId 是当前用户ID，用于排除编辑时与自身的用户名冲突。
这样详细的注释可以帮助开发人员更好地理解每个方法的功能，确保系统的可维护性和可扩展性
 */