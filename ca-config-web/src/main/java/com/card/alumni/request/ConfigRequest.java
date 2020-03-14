package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-03-14 2:04 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "配置请求", description = "配置请求")
public class ConfigRequest implements Serializable {

    @ApiModelProperty(value = "配置ID")
    private Integer id;

    @ApiModelProperty(value = "配置key")
    private String key;

    @ApiModelProperty(value = "配置value")
    private String value;

    @ApiModelProperty(value = "配置信息")
    private String info;
}
