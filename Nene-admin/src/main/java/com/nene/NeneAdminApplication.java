package com.nene;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName NeneAdminApplication
 * @Description nene管理服务启动类
 * @Author Protip
 * @Date 2023/2/6 15:20
 * @Version 1.0
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@MapperScan(basePackages = {"com.nene.mapper"})
public class NeneAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeneAdminApplication.class, args);
    }
}
