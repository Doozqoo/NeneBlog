package com.nene.config;

import com.nene.config.properties.MinioConfigProperties;
import com.nene.service.MinioService;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MinioAutoConfiguration
 * @Description minio自动配置类
 * @Author Protip
 * @Date 2023/1/31 10:35
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {
        MinioConfigProperties.class
})
@ConditionalOnClass(value = {MinioService.class})
public class MinioAutoConfiguration {

    @Bean
    public MinioClient buildMinioClient(MinioConfigProperties minioConfigProperties) {
        return MinioClient.builder()
                .credentials(minioConfigProperties.getAccessKey(), minioConfigProperties.getSecretKey())
                .endpoint(minioConfigProperties.getEndpoint())
                .build();
    }
}
