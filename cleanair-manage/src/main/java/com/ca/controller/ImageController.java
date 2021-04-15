package com.ca.controller;

import com.ca.service.ImageService;
import com.ca.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author yayuzhi
 */
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;


    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/image/upload")
    public ImageVO upload(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        return imageService.upload(file);
    }
}
