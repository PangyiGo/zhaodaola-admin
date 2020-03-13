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

    private String word;

    private int status;

    private String dept;

    private String start;

    private String end;

}
