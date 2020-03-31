package com.sise.zhaodaola.business.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/316:57 下午
 */
@Data
public class RoleMenuDto {

    private Integer roleId;

    private List<Integer> menuIds = new ArrayList<>(0);
}
