package net.javaguides.springboot.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CV Detect Service", version = "1.0", description = "CV detect medical application"))
public class SwaggerConfig {

}
