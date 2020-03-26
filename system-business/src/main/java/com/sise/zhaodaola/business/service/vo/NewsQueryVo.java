package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User: PangYi
 * Date: 2020-03-19
 * Time: 15:32
 * Description:
 */
@Data
public class NewsQueryVo implements Serializable {

    private Integer id;

    private String uuid;

    private String title;

    private String content;

    private String author;

    private String dept;

    private String placement;

    private Long browse;

    private String image;

    private String type;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
