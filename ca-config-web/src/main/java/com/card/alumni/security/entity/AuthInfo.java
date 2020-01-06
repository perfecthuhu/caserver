package com.card.alumni.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回token
 */
@Getter
@AllArgsConstructor
public class AuthInfo implements Serializable {

    private final String token;

    private final JwtUser user;
}
