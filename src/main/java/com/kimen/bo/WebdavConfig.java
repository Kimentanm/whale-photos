package com.kimen.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author Kimen
 * @Date 2021/4/7 - 9:24 下午
 */
@Getter
@Setter
public class WebdavConfig {

    private String url;
    private String username;
    private String password;
    private List<String> imgType;
}
