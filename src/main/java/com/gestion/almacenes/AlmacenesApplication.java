package com.gestion.almacenes;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AlmacenesApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AlmacenesApplication.class, args);
    System.out.println("--------------------------------------");
    System.out.println("----------- Desplegado en: -----------");
    System.out.println("--------------------------------------");
    System.out.println("http://localhost:8081/storehouse/v1/doc/swagger-ui/index.html");
    System.out.println("https://almacen-api-test..gumiel.uk/storehouse/v1/doc/swagger-ui/index.html");
    System.out.println("https://almacen.gumiel.uk/storehouse/v1/doc/swagger-ui/index.html");

  }

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Documentación de API-REST de la GESTION DE ALMACENES")
                .version("1.0")
                .description(
                    "Esta es una pequeña documentación para los endpoints del programa de Gestión de almacenes")
        ).servers(List.of(
                    new Server().url("http://localhost:8081/storehouse/v1").description("Servidor de local"),
                    new Server().url("https://almacen.gumiel.uk/storehouse/v1").description("Servidor Producción"),
                    new Server().url("https://almacen-staging.gumiel.uk/storehouse/v1").description("Servidor de Staging")

            ));
  }
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(AlmacenesApplication.class);
  }
}
