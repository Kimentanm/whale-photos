package com.kimen.core;

import com.alibaba.fastjson.JSON;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.kimen.bo.PhotoInfo;
import com.kimen.bo.WebdavFile;
import com.kimen.util.WebdavUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kimen
 * @Date 2021/4/7 - 9:29 下午
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebdavClient.connectWebdav();
        List<WebdavFile> photoList = getPhotoList();
        for (WebdavFile photoItem : photoList) {
            String filePath = photoItem.getPath();
            String userPath = System.getProperty("user.dir");
            String originPhotoPath = userPath + filePath;
            // 去除etag中的引号
            String etag = photoItem.getEtag().replace("\"", "");
            filePath = WebdavUtil.getPhotoName(originPhotoPath, etag);
            PhotoInfo photoInfo;
            if (!new File(filePath).exists()) {
                InputStream inputStream = WebdavClient.getFile(photoItem.getPath());
                String lastPath = filePath.substring(0, filePath.lastIndexOf("/"));
                File dirFile = new File(lastPath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                // 输入流只能被读一次，所以此处需要拷贝
                ByteArrayOutputStream bos = cloneInputStream(inputStream);
                byte[] bytes = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                photoInfo = parsePhotoInfo(bis, filePath);
                ByteArrayInputStream thumbnailInputStream = new ByteArrayInputStream(bytes);
                FileOutputStream outputStream = new FileOutputStream(filePath);
                Thumbnails.of(ImageIO.read(thumbnailInputStream))
                        .scale(0.5)
                        .outputQuality(0.3)
                        .outputFormat("jpeg")
                        .toOutputStream(outputStream);
                System.out.println(filePath);
            } else {
                Path path = Paths.get(filePath + ".json");
                byte[] data = Files.readAllBytes(path);
                String result = new String(data, StandardCharsets.UTF_8);
                photoInfo = JSON.parseObject(result, PhotoInfo.class);
            }
            photoItem.setPath(photoItem.getPath().substring(4));
            photoItem.setEtag(etag);
            photoItem.setPhotoInfo(photoInfo);
        }
        WebdavClient.photoList = photoList;
    }

    private List<WebdavFile> getPhotoList() {
        List<WebdavFile> list = WebdavClient.listByFolder(true);
        return list.stream().filter(item -> WebdavUtil.imgMatch(item.getFileName())).collect(Collectors.toList());
    }

    /**
     * 拷贝输入流
     */
    private ByteArrayOutputStream cloneInputStream(InputStream input) {
        ByteArrayOutputStream baos = null;
        if (input != null) {
            try {
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = input.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos;
    }

    /**
     * 解析图片信息——拍摄日期等
     */
    private PhotoInfo parsePhotoInfo(InputStream inputStream, String filePath) {
        PhotoInfo photoInfo = new PhotoInfo();
        String photoInfoPath = filePath + ".json";
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(inputStream);
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            Date originalTime = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            photoInfo.setOriginalTime(originalTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(photoInfoPath), StandardCharsets.UTF_8);
            writer.write(JSON.toJSONString(photoInfo));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoInfo;
    }
}
