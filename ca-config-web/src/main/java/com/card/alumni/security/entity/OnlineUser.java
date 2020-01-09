package com.card.alumni.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "在线用户", description = "在线用户")
public class OnlineUser {

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "地点")
    private String address;

    @ApiModelProperty(value = "秘钥")
    private String key;;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

}
