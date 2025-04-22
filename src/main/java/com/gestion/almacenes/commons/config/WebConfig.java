package com.gestion.almacenes.commons.config;

import com.gestion.almacenes.commons.interceptor.ValidationHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ValidationHeaderInterceptor validationHeaderInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Permite todos los orígenes
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Especifica los métodos permitidos
                .allowedHeaders("*") // Permite todas las cabeceras
                .allowCredentials(false); // Cambia a `true` si se requieren credenciales, pero no se puede usar con `*`
    }

    @Autowired
    public WebConfig(ValidationHeaderInterceptor validationHeaderInterceptor) {
        this.validationHeaderInterceptor = validationHeaderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validationHeaderInterceptor);
    }
}