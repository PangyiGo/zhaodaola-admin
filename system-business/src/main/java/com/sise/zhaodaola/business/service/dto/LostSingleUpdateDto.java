package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: PangYi
 * Date: 2020-03-17
 * Time: 15:25
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LostSingleUpdateDto extends LostFoundBasicDto {

    private Integer id;

    private String uuid;

    private int status;
}
