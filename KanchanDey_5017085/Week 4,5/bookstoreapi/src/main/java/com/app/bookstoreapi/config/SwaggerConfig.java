package com.app.bookstoreapi.config;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
    }
    private ApiInfo getInfo(){
        return new ApiInfo(
            "BookStore Api",
            "Using this api, Customers can login, rent books",
            "1.0",
            "Terms of Service",
            "Soham Dutta",
            "LICENSE",
            "API licecnse url"
            );
    };
}
