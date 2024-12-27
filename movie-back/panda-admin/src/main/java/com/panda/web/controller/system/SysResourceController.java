package com.panda.web.controller.system;

import com.panda.common.response.ResponseResult;  // 引入响应结果类，统一返回格式的响应
import com.panda.system.domin.SysResource;  // 引入系统资源类，表示系统中的资源
import com.panda.system.service.impl.SysResourceServiceImpl;  // 引入资源服务类，实现了具体的资源管理逻辑
import com.panda.web.controller.BaseController;  // 引入基础控制器类，包含一些公共功能（例如分页处理）

import org.springframework.beans.factory.annotation.Autowired;  // 用于自动注入依赖
import org.springframework.validation.annotation.Validated;  // 用于验证请求体中的数据是否合法
import org.springframework.web.bind.annotation.*;  // 引入注解，用于定义RESTful API

import java.util.List;  // 引入List类，用于存储多个资源对象

@RestController  // 声明这个类是一个RESTful风格的控制器，返回数据格式为JSON
public class SysResourceController extends BaseController {  // 继承自基础控制器，提供一些通用的控制器功能

    @Autowired
    SysResourceServiceImpl sysResourceService;  // 自动注入资源服务类，调用具体的业务逻辑

    // 获取所有资源列表
    @GetMapping("/sysResource")  // 处理GET请求，路径为 "/sysResource"
    public ResponseResult findAllResources() {
        startPage();  // 开启分页功能，通常用于处理大数据量的查询
        List<SysResource> data = sysResourceService.findAllResources();  // 调用服务层获取资源列表
        return getResult(data);  // 返回响应结果，包含资源列表
    }

    // 获取所有资源及其子资源
    @GetMapping("/sysResource/children")  // 处理GET请求，路径为 "/sysResource/children"
    public ResponseResult findWithChildren() {
        return getResult((Object) sysResourceService.findWithChildren());  // 获取资源及其子资源，并返回
    }

    // 根据资源ID获取单个资源
    @GetMapping("/sysResource/{id}")  // 处理GET请求，路径为 "/sysResource/{id}"
    public ResponseResult findResourceById(@PathVariable Long id) {
        return getResult(sysResourceService.findResourceById(id));  // 根据资源ID查找资源
    }

    // 获取所有资源及其所有子资源的树形结构
    @GetMapping("/sysResource/tree")  // 处理GET请求，路径为 "/sysResource/tree"
    public ResponseResult findAllWithAllChildren() {
        return getResult(sysResourceService.findAllWithAllChildren());  // 获取所有资源及其所有子资源，以树形结构返回
    }

    // 添加资源
    @PostMapping("/sysResource")  // 处理POST请求，路径为 "/sysResource"
    public ResponseResult addResource(@Validated @RequestBody SysResource sysResource) {
        return getResult(sysResourceService.addResource(sysResource));  // 提交新资源数据并调用服务层添加
    }

    // 更新资源
    @PutMapping("/sysResource")  // 处理PUT请求，路径为 "/sysResource"
    public ResponseResult updateResource(@Validated @RequestBody SysResource sysResource) {
        return getResult(sysResourceService.updateResource(sysResource));  // 提交修改后的资源数据并调用服务层更新
    }

    // 删除资源
    @DeleteMapping("/sysResource/{ids}")  // 处理DELETE请求，路径为 "/sysResource/{ids}"
    public ResponseResult deleteResource(@PathVariable Long[] ids) {
        return getResult(sysResourceService.deleteResource(ids));  // 根据传入的ID数组删除资源
    }
}
/*
详细解释
1. 包 (Package)
package com.panda.web.controller.system;
这是控制器类所在的包，用于组织项目中的类文件。在这个例子中，控制器属于 web.controller.system 包。
2. 引入的类 (Imports)
ResponseResult
用于统一格式化接口的返回结果。通过 ResponseResult 可以返回各种数据类型（如列表、单个对象、错误信息等）。

SysResource
表示系统中的资源（例如文件、URL、菜单、权限等）。SysResource 类通常包含资源的ID、名称、描述、父资源ID等属性。

SysResourceServiceImpl
SysResourceServiceImpl 是服务层的实现类，提供了具体的业务逻辑方法，处理数据库操作等。控制器通过它来调用资源管理的相关功能。

BaseController
基础控制器类，提供通用的功能方法。这个类可能包含分页处理、响应结果格式化等通用操作。控制器继承它，以便获得这些通用功能。

Autowired
Spring的自动注入机制，用于将 SysResourceServiceImpl 类的实例注入到控制器中，以便控制器能够调用服务层的方法。

Validated
用于验证请求体中的数据是否合法。如果请求体中的数据不符合规定的格式或约束，Spring会自动抛出异常并返回错误响应。

RestController 和 RequestMapping 注解
@RestController 注解表示该类是一个RESTful风格的控制器，默认返回JSON格式的数据。所有的请求方法（如 @GetMapping）都会返回JSON响应。

3. 请求方法 (Request Methods)
@GetMapping
用于处理HTTP GET请求。一般用于获取资源，例如 findAllResources() 方法获取所有资源，findResourceById() 方法根据ID获取单个资源。

@PostMapping
用于处理HTTP POST请求。一般用于提交数据，例如 addResource() 方法用于添加资源。

@PutMapping
用于处理HTTP PUT请求，通常用于更新资源。updateResource() 方法就是用来更新资源数据的。

@DeleteMapping
用于处理HTTP DELETE请求。deleteResource() 方法根据传入的资源ID删除资源。

@PathVariable
用于获取URL中路径部分的参数，例如 /sysResource/{id} 中的 id。通过 @PathVariable Long id 将请求路径中的 id 映射为方法的参数。

@RequestBody
用于绑定HTTP请求体中的数据到Java对象中。在 addResource() 和 updateResource() 方法中，使用 @RequestBody SysResource sysResource 来接收前端提交的资源数据。

4. 返回值 (Return Value)
ResponseResult
每个方法都返回一个 ResponseResult 对象，它用于统一格式化接口的返回结果。getResult() 是一个通用的方法，可能用于将业务数据封装成统一格式的响应。
5. 常见功能
分页 (startPage())
startPage() 方法通常用于启动分页功能，限制返回的数据条数。在 findAllResources() 方法中调用该方法，表示该请求支持分页查询。

树形结构 (findAllWithAllChildren())
在 findAllWithAllChildren() 方法中，控制器会调用服务层获取所有资源及其子资源，以树形结构展示。这通常用于呈现层级关系数据，如菜单或权限结构。

6. 业务逻辑
查找资源
findAllResources() 方法获取所有资源，findResourceById() 根据ID查找资源，findWithChildren() 和 findAllWithAllChildren() 获取具有层级结构的资源信息。

资源操作
addResource() 用于添加资源，updateResource() 用于更新资源，deleteResource() 用于删除资源。

总结
SysResourceController 是一个典型的RESTful API控制器，处理关于系统资源的请求。它包括了资源的增、删、改、查功能，并支持分页、树形结构的展示。控制器通过服务类 SysResourceServiceImpl 来实现具体的业务逻辑，每个方法的返回值都通过 ResponseResult 统一封装成标准格式，确保前后端的一致性。
 */