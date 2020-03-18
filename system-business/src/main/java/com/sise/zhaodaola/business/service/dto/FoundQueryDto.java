package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: PangYi
 * Date: 2020-03-18
 * Time: 13:34
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FoundQueryDto extends LostFoundQueryDto {

    private int contact;

}
