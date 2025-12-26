package com.example.EcommerceApplication.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecommerceAPI() {
        SecurityScheme jwtScheme=new SecurityScheme();
        jwtScheme.type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components().addSecuritySchemes("JWT",jwtScheme))
                .info(new Info()
                        .title("E-Commerce Backend APIs")
                        .description("All APIs for Products, Cart, Orders, Wishlist")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Aswin R")
                                .email("aswin@gmail.com")
                        )
                );
    }
}
