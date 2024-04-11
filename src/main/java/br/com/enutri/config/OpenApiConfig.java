package br.com.enutri.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title="Swagger eNutri",
                version="1.0.0",
                description = "API de sistema para acompanhamento de planos alimentares."
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080"
                )
        }
)


public class OpenApiConfig {
}
