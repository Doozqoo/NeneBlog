package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description 文件上传控制类
 * @Author Protip
 * @Date 2023/1/31 14:22
 * @Version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final UploadService uploadService;

    @ApiLog(name = "上传头像")
    @PostMapping("/avatar")
    public ResponseResult uploadAvatar(MultipartFile file) {
        return uploadService.uploadAvatar(file);
    }
}
