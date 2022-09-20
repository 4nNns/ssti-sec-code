package com.ha0l.sstiseccode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ha0
 * @date 2022-0919
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
