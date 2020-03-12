package com.sise.zhaodaola.tool.config;

import com.sise.zhaodaola.tool.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 8:37
 * Description:
 */
@Service("auth")
public class AuthPermission {

    /**
     * 校验当前用户权限
     * @param permissions 权限列表
     * @return /
     */
    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
