package com.nene.service.impl;

import com.nene.domain.ResponseResult;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.MinioService;
import com.nene.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName UploadServiceImpl
 * @Description 文件上传服务接口实现
 * @Author Protip
 * @Date 2023/1/31 14:27
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final MinioService minioService;

    @Override
    public ResponseResult uploadAvatar(MultipartFile file) {

        if (file == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMPTY_DATA);
        }

        try {
            // 2. 拼装上传所需参数
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            // --获取文件后缀名
            String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // --生成文件名
            String filename = UUID.randomUUID().toString().replace("-", "");

            // 2.1 上传数据并返回访问地址 url
            String url = minioService.uploadImgFile("", filename + postfix, file.getInputStream());
            return ResponseResult.okResult(url);
        } catch (IOException e) {
            throw new CustomServiceException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
