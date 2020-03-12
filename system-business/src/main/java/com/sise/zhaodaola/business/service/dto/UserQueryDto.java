package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 10:25
 * Description:
 */
@Data
public class UserQueryDto {

    private String username;

    private String realName;

    private String email;

    private int status;

    private String start;

    private String end;

}
