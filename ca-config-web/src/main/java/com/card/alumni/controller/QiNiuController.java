package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.request.RoleRequest;
import com.card.alumni.utils.RequestUtil;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User: liuyang
 * Date: 2020-05-21
 * Time: 12:12 上午
 */
@Api(value = "七牛云文件相关模块", tags = "七牛云文件相关模块")
@RestController
@RequestMapping("/api/qiniu")
public class QiNiuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiNiuController.class);

    @Value("${qiniu.ACCESS_KEY}")
    private String accessKey;

    @Value("${qiniu.SECRET_KEY}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @PostMapping("/getToken")
    @ApiOperation(value = "获取token", notes = "获取token")
    public UnifiedResult<String> getToken() throws Exception {

        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            if (StringUtils.isEmpty(upToken)){
                throw new CaConfigException("下载七牛云token失败");
            }
            return UnifiedResult.success(upToken);
        } catch (CaConfigException e) {
            LOGGER.error("getToken error. e: ", e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }
}
