package com.company.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.urlshortener"))
                //.paths(PathSelectors.any())
                .paths(PathSelectors.regex("/v1/url/.*"))
                .build()
                .enable(true)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("SHORTEN URL REST API",
                "Shorten the url with http and https protocol",
                "Version 1",
                "Freemium",
                new Contact("Morteza Naeimabadi", "www.example.com", "info@company.com"),
                "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }


}
