package com.card.alumni.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liumingyu
 * @date 2019-19-19 11:05 PM
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String title;
    private String description;
    private String author;
    private String version;
    private String basePackage;

}
