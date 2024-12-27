package com.panda.system.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SysHall 类用于表示影院的影厅信息。
 * 包括影厅名称、座位数、座位状态等字段。
 * 提供了必要的验证规则（如排数、座位数限制等）以及与影院 (SysCinema) 的关联。
 */
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
@Data                 // 自动生成 getter、setter、equals、hashCode、toString 方法
@ToString             // 自动生成 toString 方法
public class SysHall implements Serializable {

    private static final Long serialVersionUID = 1L;  // 序列化版本 ID，确保反序列化的一致性

    private Long cinemaId; // 影院ID，表示该影厅所属的影院

    private Long hallId;  // 影厅ID，唯一标识一个影厅

    @NotBlank(message = "影厅名称不能为空")  // 校验影厅名称不能为空
    private String hallName;  // 影厅名称

    private String hallCategory;  // 影厅类别，如“IMAX”、“普通影厅”等

    // 排开始标号，如“1”或“A”，表示影厅的排号起始
    private String rowStart;

    // 影厅排数，限制排数在 3 到 50 之间
    @Min(value = 3, message = "排数不能小于3")  // 校验排数不能小于 3
    @Max(value = 50, message = "排数不能大于50")  // 校验排数不能大于 50
    private Integer rowNums;  // 影厅排数

    // 每排座位数，限制每排座位数在 3 到 50 之间
    @Min(value = 3, message = "每排座位数不能小于3")  // 校验每排座位数不能小于 3
    @Max(value = 50, message = "每排座位数不能大于50")  // 校验每排座位数不能大于 50
    private Integer seatNumsRow;  // 每排座位数

    // 总可用座位数，限制在 9 到 2500 之间，可以通过安排座位禁用指定座位
    @Min(value = 9, message = "座位数不能小于9")  // 校验总座位数不能小于 9
    @Max(value = 2500, message = "座位数不能大于2500")  // 校验总座位数不能大于 2500
    private Integer seatNums;  // 总座位数

    // 座位的状态，使用 JSON 存储每个座位的状态（0：可用，2：禁用，1：售出）
    // 这个字段存储的是所有座位的状态信息，通常会是一个 JSON 格式的字符串
    private String seatState;

    private Boolean delState;  // 影厅的删除状态，标记该影厅是否被删除（例如：软删除）

    // 影厅表的多表连接，关联到影院（SysCinema）表
    private SysCinema sysCinema;  // 影厅所在的影院信息

}
/*
代码解析：
类注释：

SysHall 类是一个表示影厅（Cinema Hall）实体的类，包含影厅的基本信息，如名称、座位数、座位状态、影厅类型等。
@AllArgsConstructor, @NoArgsConstructor, @Data, @ToString 是 Lombok 提供的注解，用于简化代码生成，避免手动编写构造函数、getter/setter、toString 方法等。
字段注释：

cinemaId：关联影院的 ID，表明该影厅所属哪个影院。
hallId：影厅的唯一标识符。
hallName：影厅名称，使用了 @NotBlank 注解进行验证，确保不能为空。
hallCategory：影厅的类别，如 IMAX 影厅、普通影厅等。
rowStart：影厅的排开始标号，如“1”或“A”。
rowNums：影厅排数，设置了最大值为 50，最小值为 3。
seatNumsRow：每排座位数，限制在 3 到 50 之间。
seatNums：影厅总的座位数，限制在 9 到 2500 之间。
seatState：一个 JSON 字符串，用于表示各座位的状态（例如可用、禁用或已售出）。
delState：删除状态，用于标记该影厅是否被删除，通常用于软删除功能。
sysCinema：关联的影院对象，表示该影厅属于哪个影院。
注解说明：

@NotBlank：用于确保字段值不能为空。
@Min 和 @Max：用于设置字段的最小值和最大值，并附带错误信息，保证数据的有效性和一致性。
通过这些注解和字段定义，你可以确保 SysHall 对象在创建或修改时，符合业务规则和数据约束。
 */