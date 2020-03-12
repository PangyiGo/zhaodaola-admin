package com.sise.zhaodaola.business.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 16:11
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateDto extends UserDto {

    private Set<Integer> roles;
}
