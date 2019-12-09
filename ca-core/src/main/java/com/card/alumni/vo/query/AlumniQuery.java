package com.card.alumni.vo.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:07 PM
 */
@Getter
@Setter
public class AlumniQuery extends PageParam implements Serializable {

    private Integer id;

    private Integer partentId;

    private String name;

    private Integer type;

}
