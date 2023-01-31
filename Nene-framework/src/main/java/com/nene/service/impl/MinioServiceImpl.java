package com.nene.service.impl;

import com.nene.config.properties.MinioConfigProperties;
import com.nene.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName MinioServiceImpl
 * @Description 文件存储服务实现类
 * @Author Protip
 * @Date 2023/1/31 10:38
 * @Version 1.0
 */
@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    @Autowired
    private MinioConfigProperties minioConfigProperties;

    private final static String SEPARATOR = "/";

    /**
     * 构建文件存储路径
     *
     * @param dirPath
     * @param filename yyyy/mm/dd/file.jpg
     * @return
     */
    public String builderFilePath(String dirPath, String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (StringUtils.hasText(dirPath)) {
            stringBuilder.append(dirPath).append(SEPARATOR);
        }
        String todayStr = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        stringBuilder.append(todayStr).append(SEPARATOR);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    // 设置文件存储路径
                    .object(filePath)
                    // 设置文件类型
                    .contentType("image/jpg")
                    // 设置存储桶
                    .bucket(minioConfigProperties.getBucket())
                    // 设置数据流
                    .stream(inputStream, inputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
            return minioConfigProperties.getEndpoint() + SEPARATOR + minioConfigProperties.getBucket() + SEPARATOR + filePath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minioConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            return minioConfigProperties.getEndpoint() + SEPARATOR + minioConfigProperties.getBucket() + SEPARATOR + filePath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minioConfigProperties.getEndpoint() + SEPARATOR, "");
        int index = key.indexOf(SEPARATOR);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] downLoadFile(String pathUrl) {
        String key = pathUrl.replace(minioConfigProperties.getEndpoint() + SEPARATOR, "");
        int index = key.indexOf(SEPARATOR);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int len = 0;
        while (true) {
            try {
                assert inputStream != null;
                if ((len = inputStream.read(buff, 0, 100)) <= 0) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0, len);
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
