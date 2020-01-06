package com.card.alumni.security.utils;

import com.card.alumni.constant.CaConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "ca")
public class CaPermissionValidator {

    public Boolean check(String... permissions) {
        // 如果是匿名访问的，就放行
        String anonymous = CaConstants.ANONYMOUS;
        if (Arrays.asList(permissions).contains(anonymous)) {
            return true;
        }
        // 获取当前用户的所有权限
        List<String> caPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return caPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(caPermissions::contains);
    }
}
