/*package com.example.kanban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
//@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())//basePackage("com.example.kanban"))
                .paths(PathSelectors.any())
                .build();
                //.securitySchemes(Collections.singletonList(apiKey()))
                //.securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiKey apiKey() {
        return new ApiKey(/*"Bearer Token"*//*"JWT", "Authorization", "header"); */
 /*   }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];//{authorizationScope};
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT" , authorizationScopes));
        // Collections.singletonList(new SecurityReference("Bearer Token", authorizationScopes));
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "tutorial jwt",
                "Descripcion",
                "2.0",
                "Terminos y condiciones",
                new Contact("David Morales", "wwww.wwww.wwww", "edavid.morales@udea.edu.co"),
                "Licencia",
                "Api licencia",
                Collections.EMPTY_LIST
        );
    } */
////////////////////////////////////////////////
  /*  @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.kanban"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }*/

   /* private ApiKey apiKey() {
        return new ApiKey("Bearer Token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Bearer Token", authorizationScopes));
    }*/


//}
