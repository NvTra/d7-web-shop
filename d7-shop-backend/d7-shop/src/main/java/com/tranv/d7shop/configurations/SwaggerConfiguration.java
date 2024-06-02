package com.tranv.d7shop.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce api in Java Spring boot",
                version = "1.0.0",
                description = "Ứng dụng D7-Shop"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server")
        }
)
@SecurityScheme(
        name = "bearer-key",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfiguration {
//    @Bean
//    public OpenAPI defineOpenApi() {
//        Server server = new Server();
//        server.setUrl("http://localhost:8080");
//        server.setDescription("Development");
//
//        Contact myContact = new Contact();
//        myContact.setName("Nguyen Van Tra");
//        myContact.setEmail("nguyenvantra37.na@gmail.com");
//
//        Info information = new Info()
//                .title("D7 Web Shop Management System API")
//                .version("1.0")
//                .description("This API exposes endpoints to manage d7-web-shop.")
//                .contact(myContact);
//        return new OpenAPI().info(information).servers(List.of(server));
//    }
}
