package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-16
 * Time: 9:04
 * Description:
 */
@Data
public class LostFoundBasicDto implements Serializable {

    private String title;

    private Integer type;

    private String remark;

    private String place;

    private String lostTime;

    private List<String> images;

    private String telephone;

    private String wechat;

    private String dorm;

    // 寻物属性
    private String name;

    // 认领属性
    private int contact;

    private Integer claim;
}
