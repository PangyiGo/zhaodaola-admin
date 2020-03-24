package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PangYi
 * @Date 2020/3/249:51 下午
 */
@Data
public class CommentQueryDto implements Serializable {

    private String word;

    private String postCode;

    private String usernmae;

    private int type;

    private String start;

    private String end;

}
