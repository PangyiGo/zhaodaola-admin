package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: PangYi
 * Date: 2020-03-18
 * Time: 17:12
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FoundSingleDto extends LostFoundBasicDto {

    private Integer id;

    private String uuid;

    private int status;
}
