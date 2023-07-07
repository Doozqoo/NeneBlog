package com.nene.service;

import java.io.InputStream;

/**
 * @ClassName MinioService
 * @Description 文件存储服务
 * @Author Protip
 * @Date 2023/1/31 10:36
 * @Version 1.0
 */
public interface MinioService {

    /**
     * 上传图片文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    public String uploadFile(String prefix, String filename, String contentType, String bucket, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param pathUrl 文件全路径
     */
    public void delete(String pathUrl);

    /**
     * 下载文件
     *
     * @param pathUrl 文件全路径
     * @return
     */
    public byte[] downLoadFile(String pathUrl);
}
