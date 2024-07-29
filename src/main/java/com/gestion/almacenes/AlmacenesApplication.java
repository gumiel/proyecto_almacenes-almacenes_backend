package com.gestion.almacenes;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlmacenesApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlmacenesApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenApi() {
    Server server1 = new Server();
    server1.setDescription("Servidor local de desarrollo");
    server1.setUrl("localhost");
    Server server2 = new Server();
    server2.setDescription("Servidor de producciòn");
    server2.setUrl("https://service-storehouse-production.onrender.com/storehouse/v1/");
    List<Server> serverList = new ArrayList<>();
    serverList.add(server1);
    serverList.add(server2);
    return new OpenAPI()
        .servers(serverList)
        .info(
            new Info()
                .title("Documentacion de API-REST de la GESTION DE ALMACENES")
                .version("1.0")
                .description(
                    "Esta es una pequeña documentacion para los endpoints del programa de Gestion de almacenes")
        );
  }

}
