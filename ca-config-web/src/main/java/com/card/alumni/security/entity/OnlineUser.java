package com.card.alumni.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

    private String userName;

    private String name;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;

}
