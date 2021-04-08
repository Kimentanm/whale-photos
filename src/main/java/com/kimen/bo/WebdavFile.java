package com.kimen.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Kimen
 * @Date 2021/4/7 - 9:25 下午
 */
@Getter
@Setter
public class WebdavFile {

    private String path;
    private String fileName;
    private String etag;
    private PhotoInfo photoInfo;
}
