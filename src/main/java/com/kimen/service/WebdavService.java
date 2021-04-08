package com.kimen.service;

import com.kimen.bo.WebdavFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WebdavService {

    List<WebdavFile> getPhotoList();
    void getImage(String filePath, String etag, HttpServletResponse response);
}
