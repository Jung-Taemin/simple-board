package me.jungtaemin.simpleboard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_KEY = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearer = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name(BEARER_KEY);

        return new OpenAPI()
                .info(new Info()
                        .title("Simple Board API")
                        .description("JWT 인증 + 게시판 CRUD + Redis 통계")
                        .version("v1.0.0")
                        .license(new License().name("MIT")))
                .schemaRequirement(BEARER_KEY, bearer)
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_KEY));
    }
}
