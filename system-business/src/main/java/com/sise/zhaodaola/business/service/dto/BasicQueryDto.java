package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

/**
 * User: PangYi
 * Date: 2020-03-13
 * Time: 16:51
 * Description:
 */
@Data
public class BasicQueryDto {

    private String word;

    private String username;

    private int category;

    private int status;

    private String start;

    private String end;
}
