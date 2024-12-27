package com.panda.system.domin;

import lombok.AllArgsConstructor;  // 自动生成包含所有字段的构造函数
import lombok.Data;  // 自动生成getters、setters、toString等方法
import lombok.NoArgsConstructor;  // 自动生成无参构造函数
import lombok.ToString;  // 自动生成toString方法

import javax.validation.constraints.NotBlank;  // 用于验证字段不能为空
import java.io.Serializable;  // 用于序列化对象
import java.util.List;  // 用于处理多个子资源

@Data  // Lombok注解，自动生成getters、setters、toString等方法
@AllArgsConstructor  // Lombok注解，自动生成包含所有字段的构造函数
@NoArgsConstructor  // Lombok注解，自动生成无参构造函数
@ToString  // Lombok注解，自动生成toString方法
public class SysResource implements Serializable {  // 表示该类是一个可序列化的对象

    private static final Long serialVersionUID = 1L;  // 序列化版本号，用于版本控制

    private Long id;  // 资源ID

    @NotBlank(message = "菜单名称不能为空")  // 验证菜单名称不能为空
    private String name;  // 菜单名称

    private String path;  // 资源路径（例如URL或文件路径）

    private Integer level;  // 菜单权限等级（例如：1代表顶级菜单，2代表二级菜单）

    private Long parentId;  // 父菜单的ID，用于表示该资源的父菜单关系

    private SysResource parent;  // 父菜单对象，用于表示父资源

    private List<SysResource> children;  // 子菜单列表，表示该资源的下级资源
}
/*
详细解释
1. 类注解：
@Data：Lombok注解，自动为该类生成：

getter 和 setter 方法
toString() 方法
equals() 和 hashCode() 方法
RequiredArgsConstructor：为类中 final 或 @NonNull 字段生成构造函数。
@AllArgsConstructor：Lombok注解，自动生成包含所有字段的构造函数，允许通过所有字段来创建对象。

@NoArgsConstructor：Lombok注解，自动生成无参构造函数，允许通过无参构造来创建对象。

@ToString：Lombok注解，自动为类生成 toString() 方法，方便打印该类的对象时输出其所有字段的字符串。

2. 接口实现：
Serializable：实现 Serializable 接口，意味着该类的对象可以被转换为字节流，以便于传输或持久化存储。serialVersionUID 是序列化的版本控制标识符，用于确保在反序列化时类的版本一致。
3. 字段（Fields）：
private Long id;：资源的唯一标识符，用于区分不同的资源。
@NotBlank(message = "菜单名称不能为空") private String name;：name 字段表示资源的名称。使用 @NotBlank 注解表示该字段不能为空，验证用户提交的菜单名称是否为空。
private String path;：资源的路径，可以是URL或者文件路径，用于定位资源。
private Integer level;：资源的权限等级，通常用于表示菜单的层级关系。例如，level=1 代表顶级菜单，level=2 代表二级菜单等。
private Long parentId;：该资源的父菜单ID，表示该资源是某个父资源的子菜单，形成菜单的层级关系。
private SysResource parent;：该资源的父菜单对象，表示该资源的父资源信息。通过这个字段可以获取父菜单的具体信息。
private List<SysResource> children;：该资源的子菜单列表，表示该资源下有多少子资源。List<SysResource> 类型表示可以有多个子菜单。
4. 字段的设计目的：
SysResource 类用于描述系统中的一个资源（例如菜单、权限项等），并且设计为支持层级关系的资源。
parentId 和 parent 字段共同表示资源的父子关系，可以支持树形结构表示菜单或权限等系统资源。
children 字段则表示当前资源下的所有子资源（例如子菜单）。
5. 验证：
使用 @NotBlank 注解对 name 字段进行验证，确保资源的名称不能为空。在表单提交时，如果没有填写名称，Spring会自动抛出错误并返回相应的提示信息（"菜单名称不能为空"）。
总结
SysResource 类用于表示一个系统资源，具有基本的属性如ID、名称、路径、权限等级等。它还支持树形结构的组织方式，资源可以有父菜单（parent）和子菜单（children）。类通过Lombok注解简化了常用方法的编写，如构造函数、getter、setter、toString等。通过 Serializable 接口，它还支持对象的序列化与反序列化，方便数据的传输和存储
 */