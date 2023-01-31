package com.nene.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName MinIOConfigProperties
 * @Description 属性配置
 * @Author Protip
 * @Date 2023/1/31 10:31
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfigProperties {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
}
