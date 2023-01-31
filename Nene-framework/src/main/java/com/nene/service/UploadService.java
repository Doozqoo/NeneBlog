package com.nene.service;

import com.nene.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadService
 * @Description 文件上传服务接口
 * @Author Protip
 * @Date 2023/1/31 14:27
 * @Version 1.0
 */
public interface UploadService {
    /**
     * 上传头像
     *
     * @param file   头像数据
     * @param userId
     * @return
     */
    ResponseResult uploadAvatar(MultipartFile file);
}
