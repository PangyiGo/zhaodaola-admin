package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User: PangYi
 * Date: 2020-03-24
 * Time: 10:27
 * Description:
 */
@Data
public class BannerQueryVo {

    private Integer id;

    private String uuid;

    private String title;

    private String link;

    private String newsTitle;

    private Integer newsId;

    private Integer type;

    private String image;

    private Integer sort;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
