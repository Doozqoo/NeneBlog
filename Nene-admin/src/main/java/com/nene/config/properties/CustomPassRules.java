package com.nene.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName CustomPassRules
 * @Description 定制放行规则
 * @Author Protip
 * @Date 2023/1/10 11:41
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "custom.request")
public class CustomPassRules {

    private List<String> excludedUrls;

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}
