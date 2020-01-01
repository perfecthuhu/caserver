package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-29 4:34 PM
 */
@Data
public class UserQueryRequest extends BaseQueryRequest implements Serializable {

    private Integer id;

    private String phone;

    private String keyword;

    private Integer status;

    private Integer yn;

}
