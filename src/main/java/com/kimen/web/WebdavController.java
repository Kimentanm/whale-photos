package com.kimen.web;

import com.kimen.bo.WebdavFile;
import com.kimen.core.Result;
import com.kimen.core.ResultGenerator;
import com.kimen.service.WebdavService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kimen
 * @Date 2021/4/8 - 2:57 下午
 */
@RestController
@RequestMapping("/webdav")
public class WebdavController {

    @Resource
    private WebdavService webdavService;

    @GetMapping("/photo/list")
    public Result getPhotoList() {
        List<WebdavFile> photoList = webdavService.getPhotoList();
        return ResultGenerator.genSuccessResult(photoList);
    }

    @GetMapping("/img")
    public void getImage(@RequestParam String filePath, @RequestParam String etag, HttpServletResponse response) {
        webdavService.getImage(filePath, etag, response);
    }
}
