package com.ca.service;

import com.ca.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    //上传图片操作
    ImageVO upload(MultipartFile uploadFile);
}
