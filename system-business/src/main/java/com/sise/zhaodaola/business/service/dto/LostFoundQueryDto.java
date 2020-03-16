package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PangYi
 * @Date 2020/3/172:02 上午
 */
@Data
public class LostFoundQueryDto implements Serializable {

    private String word;

    private String username;

    private int type;

    private String strart;

    private String end;

    private int status;
}
