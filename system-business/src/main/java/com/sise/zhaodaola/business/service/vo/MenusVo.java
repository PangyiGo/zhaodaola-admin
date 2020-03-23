package com.sise.zhaodaola.business.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/238:22 下午
 */
@Data
public class MenusVo implements Serializable {
    private Integer id;

    private String title;

    private String name;

    private String icon;

    private Integer sort;

    private Integer pid;

    private String permission;

    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<MenusVo> children = new ArrayList<>(0);

}
