package com.wangliang.lepao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 自定义 Swagger 接口文档的配置
 *
 * @author wangliang
 */
@Configuration
@EnableSwagger2WebMvc
@Profile({"dev", "test"}) // 让某些bean只在特定环境下生效
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wangliang.lepao.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置接口文档信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("乐泡-配对精灵")
                .description("乐泡-配对精灵接口文档")
                .termsOfServiceUrl("https://github.com/kobewl")
                .contact(new Contact("wangliang", "https://github.com/kobewl", "3130187893@qq.com"))
                .version("1.0")
                .build();
    }
}
