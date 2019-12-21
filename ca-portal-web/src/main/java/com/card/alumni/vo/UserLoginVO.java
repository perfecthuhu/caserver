package com.card.alumni.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/21
 * @date 10:19 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO implements Serializable {

    private Integer userStatus;

    private String token;
}
