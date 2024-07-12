package com.poc.cric.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Cricinfo API").version("1.0.0")
                .description("API for Managing Cricket Players")
                .contact(new Contact().name("Jitender").email("jiten.s@nttdata.com").url("https://your-website.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
