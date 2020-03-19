package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User: PangYi
 * Date: 2020-03-19
 * Time: 15:16
 * Description:
 */
@Data
public class NewsQueryDto implements Serializable {

    private String word;

    private int status;

    private int type;

    private String dept;

    private String start;

    private String end;
}
