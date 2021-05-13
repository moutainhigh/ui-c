package com.sep.media.vo;

import lombok.Data;

import java.util.List;

@Data
public class MediaXcxVo {
    private String mediaClassifyName;
    private List<MediaVo> mediaVos;
}
