package br.com.restaurants.user.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Restaurants - Users API",
                version = "v1",
                description = "API to manager system restaurant"
        ),
        servers = {
                @Server(url = "/api/v1", description = "Local server - Version 1")
        }
)
public class OpenApiConfig {
}
