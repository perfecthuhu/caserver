package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-01-01 2:50 PM
 */
@Data
public class RecommendModel implements Serializable {

    private Integer recommendId;

    private SimpleUserModel user;
}
