package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: PangYi
 * @Date 2020/3/1410:15 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryQueryDto extends PageQueryCriteria {

    private String word;

    private int status;
}
