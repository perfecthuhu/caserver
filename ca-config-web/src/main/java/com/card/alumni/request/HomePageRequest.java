package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
public class HomePageRequest implements Serializable {

    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;
}
