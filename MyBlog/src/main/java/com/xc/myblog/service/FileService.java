package com.xc.myblog.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String fileUpload(MultipartFile file);

}
