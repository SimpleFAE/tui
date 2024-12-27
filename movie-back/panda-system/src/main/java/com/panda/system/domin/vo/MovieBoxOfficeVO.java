package com.panda.system.domin.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MovieBoxOfficeVO {
    // 电影名称
    private String movieName;

    // 票房金额
    private BigDecimal boxOffice;

    // 如果需要其他字段，请根据实际需求添加
    // private String movieType;
    // private Date releaseTime;
    // private Integer ticketCount;
}