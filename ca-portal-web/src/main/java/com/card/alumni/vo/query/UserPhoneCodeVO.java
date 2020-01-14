package com.card.alumni.vo.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/21
 * @date 1:19 PM
 */
@Getter
@Setter
@ApiModel(value = "用户", description = "用户")
public class UserPhoneCodeVO implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;
}
