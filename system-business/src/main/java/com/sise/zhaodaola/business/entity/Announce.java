package com.sise.zhaodaola.business.entity;

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
@TableName("system_announce")
public class Announce extends Model<Announce> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String uuid;

    private String title;

    private String content;

    private String username;

    private long browse;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
