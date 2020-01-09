package com.card.alumni.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "Jwt用户信息", description = "Jwt用户信息")
public class JwtUser implements UserDetails {

    @JsonIgnore
    @ApiModelProperty(value = "用户ID")
    private final Integer id;

    @ApiModelProperty(value = "登录账号")
    private final String username;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private final String password;

    @ApiModelProperty(value = "头像")
    private final String avatar;

    @ApiModelProperty(value = "邮箱")
    private final String email;

    @ApiModelProperty(value = "用户名")
    private final String name;

    @JsonIgnore
    @ApiModelProperty(value = "权限列表")
    private final Collection<GrantedAuthority> authorities;

    @ApiModelProperty(value = "是否可用")
    private final boolean enabled;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonIgnore
    @ApiModelProperty(value = "密码最后更改时间")
    private final Date lastPasswordResetDate;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
