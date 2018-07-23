package com.awe.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * description Swagger
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/18.</p>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.awe.web.controller";

    public static final String VERSION = "1.0.0";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("awe api")
                .description("This is to show api description")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact(new Contact("", "", "wangyang_daya@126.com"))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
