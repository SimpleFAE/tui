package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;
import com.panda.system.domin.SysRole;
import com.panda.system.service.impl.SysRoleServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 声明这是一个REST风格的控制器，用于处理HTTP请求并返回JSON或XML等格式的数据
public class SysRoleController extends BaseController { // 继承自BaseController，可能包含了一些通用的方法或属性

    @Autowired // 自动装配SysRoleServiceImpl的实例，以便在控制器中使用
            SysRoleServiceImpl sysRoleService; // SysRole业务逻辑的服务层实现

    /**
     * 查询所有角色
     * @return 包含所有角色信息的ResponseResult对象
     */
    @GetMapping("/sysRole") // 处理GET请求，路径为/sysRole
    public ResponseResult findAllRoles() {
        startPage(); // 调用分页设置方法（假设在BaseController中定义），但在查询所有角色的场景中可能不需要
        List<SysRole> data = sysRoleService.findAllRoles(); // 调用服务层方法，查询所有角色信息
        return getResult(data); // 调用通用方法处理结果并返回，将角色信息封装成ResponseResult对象
    }

    /**
     * 根据角色ID查询角色信息
     * @param id 角色ID
     * @return 包含角色信息的ResponseResult对象
     */
    @GetMapping("/sysRole/{id}") // 处理GET请求，路径为/sysRole/{id}
    public ResponseResult findRoleById(@PathVariable Long id) {
        return getResult(sysRoleService.findRoleById(id)); // 调用服务层方法查询指定ID的角色信息，并处理结果返回
    }

    /**
     * 添加新角色
     * @param sysRole 要添加的角色信息
     * @return 包含操作结果的ResponseResult对象
     */
    @PostMapping("/sysRole") // 处理POST请求，路径为/sysRole
    public ResponseResult addRole(@Validated @RequestBody SysRole sysRole) {
        // @Validated 注解用于校验请求体中的SysRole对象是否符合约束条件
        // @RequestBody 注解用于将请求体中的JSON或XML数据绑定到SysRole对象上
        return getResult(sysRoleService.addRole(sysRole)); // 调用服务层方法添加新角色，并处理结果返回
    }

    /**
     * 更新角色信息
     * @param sysRole 更新后的角色信息
     * @return 包含操作结果的ResponseResult对象
     */
    @PutMapping("/sysRole") // 处理PUT请求，路径为/sysRole
    public ResponseResult updateRole(@Validated @RequestBody SysRole sysRole) {
        // 与addRole方法类似，但此方法是用于更新已存在的角色信息
        return getResult(sysRoleService.updateRole(sysRole)); // 调用服务层方法更新角色信息，并处理结果返回
    }

    /**
     * 删除指定ID的角色
     * @param ids 要删除的角色ID数组
     * @return 包含操作结果的ResponseResult对象
     */
    @DeleteMapping("/sysRole/{ids}") // 处理DELETE请求，但通常我们期望单个ID，这里使用数组可能是为了支持批量删除
    public ResponseResult deleteRole(@PathVariable Long[] ids) {
        return getResult(sysRoleService.deleteRole(ids)); // 调用服务层方法删除指定ID的角色，并处理结果返回
    }

    /**
     * 给指定ID的角色分配权限
     * 包括增加或者删除该角色的权限
     * @param roleId 角色ID
     * @param keys 权限ID数组，表示要分配给该角色的权限
     * @return 包含操作结果的ResponseResult对象
     */
    @PostMapping("/sysRole/{roleId}") // 注意：这里与addRole方法的路径冲突，通常我们会为分配权限操作设计一个不同的路径
    public ResponseResult allotRight(@PathVariable Long roleId, @RequestBody Long[] keys) {
        return getResult(sysRoleService.allotRight(roleId, keys)); // 调用服务层方法给指定角色分配权限，并处理结果返回
    }

    // 注意：getResponseResult()和startPage()方法未在代码中定义，它们可能是BaseController或某个工具类中的方法
    // ResponseResult是一个封装了响应结果的类，可能包含了状态码、消息和数据等字段
    // 在实际应用中，可能还需要对请求参数进行验证（如ID的合法性、权限的存在等），这里为了简洁没有展示
}