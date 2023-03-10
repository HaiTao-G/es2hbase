package com.haitao.es2eshbase.config;

import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiESSTartConfigurer {

    @Primary
    @Bean(initMethod = "start")
    @ConfigurationProperties("spring.elasticsearch.bboss.default")
    public BBossESStarter bbossESStarterDefault(){
        return new BBossESStarter();
    }

    @Bean(initMethod = "start")
    @ConfigurationProperties("spring.elasticsearch.bboss.logs")
    public BBossESStarter bbossESStarterLogs(){
        return new BBossESStarter();
    }

}
