package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-31
 * Time: 11:36
 * Description:
 */
@Data
public class RoleMenuDto {

    private Integer roleId;

    private List<Integer> menuIds = new ArrayList<>(0);
}


