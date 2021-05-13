package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 帐号添加请求对象
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value = "帐号添加请求对象", description = "帐号添加请求对象")
public class AccountAddDto {

    /**
     * 创建人
     */
    private String createUid;

    /**
     * 用户id
     */
    private Integer userId;

}