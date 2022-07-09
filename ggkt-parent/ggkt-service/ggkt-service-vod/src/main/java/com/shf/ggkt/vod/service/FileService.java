package com.shf.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    String upload(MultipartFile multipartFile);
}
