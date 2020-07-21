package com.iot.platform.Config;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.application.name:BaseService}")
    private String appName;

    @Value("${system.swagger.domain}")
    private String deployDomain;

    @Value("${system.swagger.project-name:Ecosystem}")
    private String projectName;

    @Value("${system.swagger.service-name:Microservice}")
    private String serviceName;

    @Value("${system.swagger.login-path:#{\"/sso/swagger-ui.html#!/user-controller/signInUsingPOST\"}}")
    private String loginPath;

    @Value("${system.swagger.postman-path:#{\"https://documenter.getpostman.com/view/4241099/SWDzfMCb?version=latest\"}}")
    private String postmanPath;

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("Spring Boot REST API - " + this.projectName)
                .description("Spring Boot REST API for \"<b>" + this.projectName + " - " + this.serviceName
                        + "</b>\"\n\n" + "Please signin <a href=\"" + this.loginPath
                        + "\">Here</a> with your account before testing all\n\n"
                        + "You also check the example requests via Postman <a href='" + this.postmanPath + "'>here</a>")
                .version("1.0.0").license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Louis Nguyen", "https://it4u.top", "tho.n@intelizest.com")).build();
    }

    private List<ResponseMessage> globalResponses() {
        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder().code(200).message("Successfully retrieved data").build(),
                new ResponseMessageBuilder().code(401).message("You are not authorized to view the resource").build(),
                new ResponseMessageBuilder().code(403)
                        .message("Accessing the resource you were trying to reach is forbidden").build(),
                new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found")
                        .build(),
                new ResponseMessageBuilder().code(500).message("Internal Error").build());

        return globalResponses;
    }

    @Bean
    public Docket allApis() {
        final List<ResponseMessage> responseMessages = globalResponses();
        return new Docket(DocumentationType.SWAGGER_2).host(this.deployDomain).useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages).apiInfo(metaData()).select().build()
                .securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Lists.newArrayList(securityContext()));
    }

    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}