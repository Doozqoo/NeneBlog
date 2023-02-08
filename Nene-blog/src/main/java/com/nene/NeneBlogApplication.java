package com.nene;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Ayachi Nene
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@MapperScan(basePackages = {"com.nene.mapper"})
public class NeneBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeneBlogApplication.class, args);
    }
}
