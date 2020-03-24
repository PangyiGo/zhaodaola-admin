package com.sise.zhaodaola.business.service.impl;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PangYi
 * @Date 2020/3/251:18 上午
 */
@Data
public class UserPassDto implements Serializable {

    private String oldPass;

    private String newPass;
}
