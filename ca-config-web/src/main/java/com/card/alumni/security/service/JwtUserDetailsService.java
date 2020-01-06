package com.card.alumni.security.service;

import com.card.alumni.entity.CaUser;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.exception.BadRequestException;
import com.card.alumni.security.entity.JwtUser;
import com.card.alumni.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final JwtPermissionService permissionService;

    public JwtUserDetailsService(UserService userService, JwtPermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        CaUser user = null;
        try {
            user = userService.findByPhone(username);
        } catch (Exception e) {
            log.error("load user by username error. username = {}", username, e);
        }
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(CaUser user) {
        return new JwtUser(
                user.getId(),
                user.getPhone(),
                user.getPwd(),
                user.getPhotoImg(),
                user.getEmail(),
                user.getPhone(),
                permissionService.loadGrantedAuthorities(user),
                StatusEnum.YES.getCode().equals(user.getYn()),
                user.getCreateTime(),
                user.getPwdLastResetTime()
        );
    }
}
