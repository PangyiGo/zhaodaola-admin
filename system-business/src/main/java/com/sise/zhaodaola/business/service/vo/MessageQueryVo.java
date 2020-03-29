package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: PangYi
 * @Date 2020/3/2911:13 下午
 */
@Data
public class MessageQueryVo implements Serializable {

    private Integer id;

    private Integer type;

    private Integer fromUser;

    private Integer toUser;

    private String fromUsername;

    private String fromNickName;

    private String fromAvatar;

    private String toUsername;

    private String toNickName;

    private String toAvatar;

    private String content;

    private String portCode;

    private String portTitle;

    private int portId;

    private Integer commentId;

    private int commentType;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
