package com.kimen.util;

import com.kimen.core.WebdavClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Kimen
 * @Date 2021/4/8 - 2:15 下午
 */
public class WebdavUtil {

    public static Boolean imgMatch(String fileName) {
        List<String> imgTypeList = WebdavClient.webdavConfig.getImgType();
        long count = imgTypeList.stream().filter(imgType -> fileName.toUpperCase().endsWith("." + imgType.toUpperCase())).count();
        return count != 0;
    }

    public static String getPhotoName(String filePath, String etag) {
        List<String> fileNameArray = new ArrayList<>(Arrays.asList(filePath.split("\\.")));
        fileNameArray.add(fileNameArray.size() - 1, etag);
        return String.join(".", fileNameArray);
    }
}
