package com.jobis.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Arrays;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
public class SwaggerConfig {

  static {
    SpringDocUtils.getConfig()
        .addAnnotationsToIgnore(AuthenticationPrincipal.class);
  }

  @Bean
  public OpenAPI openApi() {
    Info info = new Info()
        .title("Jobis api docs")
        .version("0.1.0")
        .description("Jobis api docs");

    SecurityScheme securityScheme = new SecurityScheme()
        .type(Type.HTTP).scheme("bearer").bearerFormat("JWT")
        .in(In.HEADER).name("Authorization");
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .security(Arrays.asList(securityRequirement))
        .info(info);
  }

}
// https://blog.jiniworld.me/113#a04-2
