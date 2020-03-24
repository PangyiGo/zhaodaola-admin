package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: PangYi
 * @Date 2020/3/2410:08 下午
 */
@Data
public class CommentQueryVo implements Serializable {

    private Integer id;

    private String postCode;

    private String title;

    private String content;

    private int type;

    private Integer userId;

    private String fromUsername;  // 评论者用户名

    private Integer replayId;

    private String toUsername; // 被评论者用户名

    private Integer pid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
