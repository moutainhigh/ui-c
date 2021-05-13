package com.sep.user.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAddressOutput {


    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private String areaCode;

    private String areaCodeStr;

    private String name;

    private Integer tag;

    private String mobile;

    private String remark;

    private Integer isDeleted;

    private LocalDateTime createTime;

}
