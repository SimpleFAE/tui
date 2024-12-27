package com.panda.system.domin;

import lombok.Data;

import java.sql.Date;

@Data
public class Review {
    private Long reviewId;
    private Long movieId;   // 关联电影
    private Long userId;    // 关联用户
    private Integer  rating; // 评分 (使用 BigDecimal 处理精度)
    private String comment; // 评论内容
    private Date reviewDate; // 评论日期 (使用 LocalDateTime 处理日期)
}
