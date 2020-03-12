package com.sise.zhaodaola.business.service.dto;

import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 16:11
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListDto extends User {

    private Set<Role> roles = new HashSet<>(0);
}
