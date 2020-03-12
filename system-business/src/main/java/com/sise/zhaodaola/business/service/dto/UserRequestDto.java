package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 15:18
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequestDto extends UserDto {

    private Integer rolesId;
}
