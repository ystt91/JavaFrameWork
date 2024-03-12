package org.zerock.b01.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi restApi(){
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/replies/**")
//                .group("REST API")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi commonApi() {
//
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/**/*")
//                .pathsToExclude("/api/**/*")
//                .group("COMMON API")
//                .build();
//    }

    @Bean
    public GroupedOpenApi restApi() {

        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("org.zerock.b01.controller")
                .build();
    }

//    @Bean
//    public GroupedOpenApi uploadApi() {
//
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/upload/**")
//                .group("UPLOAD API")
//                .build();
//
//    }



}