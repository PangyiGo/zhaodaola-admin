package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PangYi
 * @Date 2020/3/81:51 下午
 */
@Data
public class UserDto implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String nickName;

    private String idCard;

    private String telephone;

    private String email;

    private Integer gender;

    private String avatar;

    private String dept;

    private Integer status;
}
