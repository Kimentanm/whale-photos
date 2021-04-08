package com.kimen.core;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.kimen.bo.WebdavConfig;
import com.kimen.bo.WebdavFile;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kimen
 * @Date 2021/4/7 - 9:30 下午
 */
public class WebdavClient {

    public static Sardine client;
    public static WebdavConfig webdavConfig;
    private static List<WebdavFile> fileList = new ArrayList<>();
    public static List<WebdavFile> photoList;

    public static void connectWebdav() {
        webdavConfig = fetchWebdavConfig();
        if (client == null) {
            client = SardineFactory.begin(webdavConfig.getUsername(), webdavConfig.getPassword());
        }
    }

    private static WebdavConfig fetchWebdavConfig() {
        Yaml yaml = new Yaml(new Constructor(WebdavConfig.class));
        String configDirPath = "config/";
        String configFileName = "config.yml";
        createConfigFile(configDirPath, configFileName);
        FileInputStream yamlInputStream = null;
        try {
            yamlInputStream = new FileInputStream(configDirPath + configFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return yaml.load(yamlInputStream);
    }

    private static void createConfigFile(String configDirPath, String configFileName) {
        String configFilePath = configDirPath + configFileName;
        File configFile = new File(configFilePath);
        // 若配置文件不存在，则拷贝默认配置文件
        if (!configFile.exists()) {
            File configDir = new File(configDirPath);
            if (!configDir.exists()) {
                configDir.mkdir();
            }
            try (InputStream configInputStream = WebdavClient.class.getClassLoader().getResourceAsStream("config.yml")) {
                String configContent = IOUtils.toString(configInputStream);
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(configFilePath));
                writer.write(configContent);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<WebdavFile> listByFolder(Boolean deep) {
        return listByFolder("/dav/", deep);
    }

    private static List<WebdavFile> listByFolder(String folder, Boolean deep) {
        if (deep) {
            listByFolderDeep(folder);
            return WebdavClient.fileList;
        } else {
            List<DavResource> list = listByFolder(folder);
            return list.stream().map(WebdavClient::convertDavResource).collect(Collectors.toList());
        }
    }

    private static List<DavResource> listByFolder(String folder) {
        List<DavResource> list = new ArrayList<>();
        try {
            list = client.list(webdavConfig.getUrl() + folder, 1, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void listByFolderDeep(String folder) {
        List<DavResource> list = listByFolder(folder);
        if (!CollectionUtils.isEmpty(list)) {
            list.remove(0);
        }
        list.forEach(item -> {
            if (item.isDirectory()) {
                listByFolderDeep(item.getHref().getPath());
            } else {
                fileList.add(convertDavResource(item));
            }
        });
    }

    private static WebdavFile convertDavResource(DavResource res) {
        String path = res.getHref().getPath();
        String[] pathArray = path.split("/");
        WebdavFile webdavFile = new WebdavFile();
        webdavFile.setPath(path);
        webdavFile.setFileName(pathArray[pathArray.length - 1]);
        webdavFile.setEtag(res.getEtag());
        return webdavFile;
    }

    public static InputStream getFile(String filePath) {
        try {
            return client.get(webdavConfig.getUrl() + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
