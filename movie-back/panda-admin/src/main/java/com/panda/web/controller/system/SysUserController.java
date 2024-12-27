package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;
import com.panda.system.domin.SysUser;
import com.panda.system.domin.vo.SysUserVo;
import com.panda.system.service.impl.SysUserServiceImpl;
import com.panda.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 定义一个REST风格的控制器，用于处理与系统用户相关的请求
@RestController
public class SysUserController extends BaseController {

    // 使用@Autowired注解自动装配SysUserServiceImpl实例
    @Autowired
    private SysUserServiceImpl sysUserService;

    // 处理GET请求，获取所有用户信息
    // 通过startPage()方法可能是在此处设置了分页参数（注意：此方法未在代码中定义，可能是BaseController中的方法）
    @GetMapping("/sysUser")
    public ResponseResult findAllUsers(SysUser sysUser) {
        startPage(); // 设置分页参数（假设此方法在BaseController中定义）
        List<SysUser> data = sysUserService.findAllUsers(sysUser); // 调用服务层方法获取用户列表
        return getResult(data); // 调用通用方法处理结果并返回
    }

    // 处理GET请求，根据用户ID获取用户信息
    @GetMapping("/sysUser/{id}")
    public ResponseResult findUserById(@PathVariable Long id) {
        // 调用服务层方法，根据ID获取用户信息，并处理结果返回
        return getResult(sysUserService.findUserById(id));
    }

    // 处理POST请求，添加新用户（注册也使用此方法）
    // @Validated注解用于校验请求体中的SysUser对象是否符合规范
    @PostMapping("/sysUser")
    public ResponseResult addUser(@Validated @RequestBody SysUser sysUser) {
        // 调用服务层方法添加用户，并处理结果返回
        return getResult(sysUserService.addUser(sysUser));
    }

    // 处理PUT请求，更新用户信息
    // 类似添加用户，但这里是更新操作
    @PutMapping("/sysUser")
    public ResponseResult updateUser(@Validated @RequestBody SysUser sysUser) {
        // 调用服务层方法更新用户，并处理结果返回
        return getResult(sysUserService.updateUser(sysUser));
    }

    // 处理DELETE请求，根据用户ID数组删除用户
    @DeleteMapping("/sysUser/{ids}")
    public ResponseResult deleteUser(@PathVariable Long[] ids) {
        // 调用服务层方法根据ID数组删除用户，并处理结果返回
        return getResult(sysUserService.deleteUser(ids));
    }

    // 用户登录请求处理
    // 注意：这里使用SysUserVo而非SysUser，可能是为了区分登录时所需的信息与用户信息表不完全一致
    @RequestMapping("/sysUser/login")
    public ResponseResult login(@RequestBody SysUserVo sysUserVo) {
        // 调用服务层方法处理登录逻辑，并处理结果返回
        return getResult(sysUserService.login(sysUserVo));
    }

    // 用户注册请求处理
    // 虽然addUser方法已经用于添加用户，但注册时可能只需要部分信息
    // 因此，这里重新构造了一个SysUser对象，只包含注册所需的信息
    @PostMapping("/sysUser/register")
    public ResponseResult register(@Validated @RequestBody SysUser sysUser) {
        // 创建一个新的SysUser对象，仅包含注册所需的信息
        SysUser registerUserInfo = new SysUser();
        registerUserInfo.setUserName(sysUser.getUserName());
        registerUserInfo.setPassword(sysUser.getPassword());
        registerUserInfo.setSex(sysUser.getSex());
        registerUserInfo.setPhoneNumber(sysUser.getPhoneNumber());
        // 调用服务层方法添加用户（注册），并处理结果返回
        return getResult(sysUserService.addUser(registerUserInfo));
    }

    // 注意：getResult()方法未在代码中定义，可能是BaseController中的一个通用方法，用于处理服务层方法的返回结果，并统一封装成ResponseResult格式返回
}