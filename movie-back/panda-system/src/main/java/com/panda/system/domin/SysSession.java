package com.panda.system.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 场次实体类
 *
 * 该类表示一个电影的放映场次，包含了电影、影厅、时间等相关信息。
 * 通过该实体类，系统可以管理每个电影场次的详细数据，例如电影的语言版本、场次日期、票价、座位等信息。
 */
@Data // 自动生成getter、setter、toString、equals、hashCode方法
@AllArgsConstructor // 生成包含所有属性的构造方法
@NoArgsConstructor // 生成无参构造方法
@ToString // 自动生成toString方法，便于打印输出
public class SysSession implements Serializable {

    private static final Long serialVersionUID = 1L; // serialVersionUID，用于序列化版本控制

    // 场次编号（唯一标识一个场次）
    private Long sessionId;

    // 影厅编号，指示该场次在那个影厅放映，不能为空
    @NotNull(message = "场次所在影厅不能为空")
    private Long hallId;

    // 该场次电影的语言版本，不能为空
    @NotBlank(message = "场次电影语言版本不能为空")
    private String languageVersion;

    // 电影编号，指示该场次放映的是哪部电影，不能为空
    @NotNull(message = "场次安排电影不能为空")
    private Long movieId;

    // 该场次的放映时间，采用时间格式 (HH:mm)，表示开始播放的时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm") // 使用JsonFormat注解指定日期格式
    private String playTime;

    // 该场次的结束时间，采用时间格式 (HH:mm)，表示放映结束的时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private String endTime;

    // 截止时间，指该场次信息的最后修改时间，超过此时间无法删除或修改该场次的电影、影厅信息
    private String deadline;

    // 场次日期，指该场次的具体放映日期，不能为空
    @NotNull(message = "场次日期不能为空")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd") // 使用JsonFormat注解指定日期格式
    private LocalDate sessionDate;

    // 场次票价，不能为空，且不能为负数
    @NotNull(message = "场次票价不能为空")
    @Size(min = 0, message = "场次票价不能为负数") // 确保票价不能为负数
    private Double sessionPrice;

    // 场次提示，提示用户该场次的特殊信息或公告
    private String sessionTips;

    // 场次座位信息，描述该场次的座位布局或座位安排，不能为空
    @NotBlank(message = "场次座位信息不能为空")
    private String sessionSeats;

    // 总座位数，表示该场次可用的座位总数
    private Integer seatNums;

    // 已售座位数，表示该场次已被预定或售出的座位数
    private Integer sallNums;

    // 影厅信息，包含该场次所属影厅的详细信息
    private SysHall sysHall;

    // 电影信息，包含该场次对应电影的详细信息
    private SysMovie sysMovie;
}
/*
详细注释说明：
注解解释：

@Data: 由Lombok提供的注解，用于自动生成 getter、setter、toString、equals 和 hashCode 方法，减少冗余代码。
@AllArgsConstructor: 由Lombok提供的注解，用于自动生成一个包含所有属性的构造方法。
@NoArgsConstructor: 由Lombok提供的注解，用于自动生成一个无参构造方法。
@ToString: 由Lombok提供的注解，用于自动生成 toString 方法，便于打印对象信息。
字段的详细说明：

sessionId: 场次的唯一标识符，用于区分不同的放映场次。
hallId: 影厅编号，指示该场次的放映影厅，不能为空。@NotNull注解用于确保此字段不能为 null。
languageVersion: 电影的语言版本，例如普通话、英语等，不能为空。@NotBlank注解用于确保此字段不能为空白字符串。
movieId: 放映的电影的编号，不能为空。表示该场次放映的电影。
playTime: 电影开始播放的时间。使用 @JsonFormat 注解来指定JSON序列化的格式，确保输出时间为 HH:mm 格式。
endTime: 电影结束的时间，格式同 playTime。
deadline: 场次信息的截止修改时间，过了这个时间，场次的电影、影厅信息无法修改或删除。
sessionDate: 场次的具体放映日期，不能为空。使用 @JsonFormat 注解来指定日期的格式为 yyyy-MM-dd。
sessionPrice: 场次的票价，不能为空且不能为负数。@Size(min = 0) 注解确保票价不能为负值。
sessionTips: 提示信息，通常用于显示场次的特殊信息，如“抢票进行中”等。
sessionSeats: 该场次的座位信息，描述座位的布局或状态。不能为空。
seatNums: 场次的座位总数。
sallNums: 已售出的座位数量。
sysHall: 该场次所属的影厅信息，类型为 SysHall。
sysMovie: 该场次所放映的电影信息，类型为 SysMovie。
数据验证：

通过 @NotNull、@NotBlank、@Size 等注解对实体字段进行了有效性验证，确保在应用程序中传入的数据符合要求。例如，票价不能为负数，场次日期和电影信息不能为空。
总结：
该 SysSession 类封装了电影放映场次的详细信息，并通过一系列注解确保字段数据的有效性。通过引入 Lombok 注解，减少了大量的样板代码，提高了代码的简洁性和可读性
 */