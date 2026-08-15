package com.farma.estoque.config;

<<<<<<< HEAD
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
=======
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
<<<<<<< HEAD
                        .title("API Estoque Farmacêutico")
                        .description("API RESTful para gestão de estoque e medicamentos com Spring Security e JWT.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento API Estoque")
                                .email("dev@farmaestoque.com.br")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .name("bearer-jwt")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
=======
                        .title("API Gestão de Estoque")
                        .version("1.0")
                        .description("API RESTful para estudo")
                        .contact(new Contact()
                                .name("Marcelo Felix do Vale")
                                .email("marcelofelix1225@gmail.com")));
    }
}
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
