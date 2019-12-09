package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-09 9:44 PM
 */
@Data
public class RoleQueryRequest extends BaseQueryRequest implements Serializable {

    /**
     * 关键字
     */
    private String keyword;

}
