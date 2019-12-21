package com.card.alumni.vo.query;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/21
 * @date 1:19 PM
 */
@Getter
@Setter
public class UserPhoneCodeVO implements Serializable {
    private String phone;
    private String code;
}
