package com.sise.zhaodaola.business.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: PangYi
 * @Date 2020/3/68:03 下午
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@TableName("system_found")
public class Found extends Model<Found> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String uuid;

    private Integer userId;

    private String title;

    private Integer type;

    private String remark;

    private String place;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime lostTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String images;

    private long browse;

    private Integer owner;

    private int contact;

    private Integer claim;

    private String telephone;

    private String wechat;

    private String dorm;

    private int status;
}
