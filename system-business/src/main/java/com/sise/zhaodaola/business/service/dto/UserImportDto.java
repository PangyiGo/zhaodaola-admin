package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PangYi
 * @Date 2020/3/81:51 下午
 */
@Data
public class UserImportDto implements Serializable {

    private String username;

    private String realName;

    private String idCard;

    private String telephone;

    private String email;

    private Integer gender;

    private String dept;

}
