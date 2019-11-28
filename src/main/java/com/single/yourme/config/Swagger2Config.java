package com.single.yourme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2配置类
 *
 * @author 1999single
 * @date 2019-11-25 13:06
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.single.yourme.*"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("悠米yourme")
                //描述
                .description("Restful-API-Doc")
                //这里配置的是服务网站，我写的是我的博客园站点~欢迎关注~
                .termsOfServiceUrl("https://www.baidu.com")
                // 三个参数依次是姓名，个人网站，邮箱
                .contact(new Contact("single", "none", "595082586@qq.com"))
                //版本
                .version("1.0")
                .build();
    }
}
