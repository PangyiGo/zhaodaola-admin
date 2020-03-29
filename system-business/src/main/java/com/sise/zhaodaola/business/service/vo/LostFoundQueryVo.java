package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/172:05 上午
 */
@Data
public class LostFoundQueryVo implements Serializable {

    private Integer id;

    private String uuid;

    private Integer userId;

    // 用户名
    private String username;

    // 昵称
    private String nickName;

    // 头像
    private String avatar;

    private String title;

    // 分类名称
    private String type;

    private String remark;

    private String place;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lostTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 失物图片名称列表
    private List<String> imagesName = new ArrayList<>(0);

    private long browse;

    private long comment;

    private String name;

    private String telephone;

    private String wechat;

    private String dorm;

    // 状态
    private String status;

    private int statusId;
}
