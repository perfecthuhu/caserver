package com.card.alumni.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回token
 */
@Getter
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "认证信息", description = "认证信息")
public class AuthInfo implements Serializable {

    @ApiModelProperty(value = "token")
    private final String token;

    @ApiModelProperty(value = "用户信息")
    private final JwtUser user;
}
