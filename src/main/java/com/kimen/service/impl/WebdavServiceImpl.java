package com.kimen.service.impl;

import com.kimen.bo.WebdavFile;
import com.kimen.core.WebdavClient;
import com.kimen.service.WebdavService;
import com.kimen.util.WebdavUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;

/**
 * @Author Kimen
 * @Date 2021/4/8 - 3:01 下午
 */
@Service
public class WebdavServiceImpl implements WebdavService {
    @Override
    public List<WebdavFile> getPhotoList() {
        return WebdavClient.photoList;
    }

    @Override
    public void getImage(String filePath, String etag, HttpServletResponse response) {
        try {
            String userPath = System.getProperty("user.dir");
            String originPhotoPath = userPath + "/dav" + filePath;
            String photoName = WebdavUtil.getPhotoName(originPhotoPath, etag);
            FileInputStream photoInputStream = new FileInputStream(photoName);
            byte[] bytes = new byte[photoInputStream.available()];
            photoInputStream.read(bytes);
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
