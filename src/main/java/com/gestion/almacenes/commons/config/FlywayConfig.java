package com.gestion.almacenes.commons.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public CommandLineRunner flywayInitializer() {
    return args -> {
      // Inicializar Flyway manualmente
      Flyway flyway = Flyway.configure()
          .dataSource(dataSource)
          .baselineOnMigrate(true) // Añadir esta línea para habilitar baselineOnMigrate
          .load();
      flyway.migrate();
    };
  }
}