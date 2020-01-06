package com.card.alumni.security.service;

import com.card.alumni.entity.CaMenu;
import com.card.alumni.entity.CaRole;
import com.card.alumni.entity.CaUser;
import com.card.alumni.service.MenuService;
import com.card.alumni.service.RoleService;
import com.card.alumni.service.UserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = "role")
public class JwtPermissionService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 加载用户权限
     *
     * @param user 用户信息
     * @return Collection
     */
    @Cacheable(key = "'loadPermissionByUser:' + #p0.phone")
    public Collection<GrantedAuthority> loadGrantedAuthorities(CaUser user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getPhone() + "---------------------");

        try {
            List<Integer> roleIdList = userService.listRoleIdsByUserId(user.getId());
            List<CaRole> roleList = roleService.listByIdList(roleIdList);
            List<Integer> menuIdList = roleService.listMenuIdsByRoleIdList(roleIdList);
            List<CaMenu> menuList = menuService.listRankByIdList(menuIdList);

            Set<String> permissions = roleList.stream().filter(role -> StringUtils.isNotBlank(role.getPermission())).map(CaRole::getPermission).collect(Collectors.toSet());
            permissions.addAll(
                    menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                            .map(CaMenu::getPermission).collect(Collectors.toSet())
            );

            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("load permission by user error.", e);
        }

        return Lists.newArrayList();
    }
}
