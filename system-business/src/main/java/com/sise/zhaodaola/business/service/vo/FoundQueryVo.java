package com.sise.zhaodaola.business.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: PangYi
 * Date: 2020-03-18
 * Time: 14:03
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FoundQueryVo extends LostFoundQueryVo {

    private int contact;

    // 认领类型
    private String contactType;

    private Integer owner;

    // 认领者
    private String foundUser;
}
