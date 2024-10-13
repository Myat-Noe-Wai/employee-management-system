package net.javaguides.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	// Define SecurityFilterChain instead of WebSecurityConfigurerAdapter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Enable CORS
            .and()
            .csrf().disable() // Disable CSRF for simplicity (modify as per your app's needs)
            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/v1/user/**", "/api/v1/employees/**", "api/roles/**", "api/attendance/**", "api/leave-requests/**", "api/employee-attendance/**").permitAll() // Allow access to employee endpoints
            	.requestMatchers("/**").permitAll()
                .anyRequest().authenticated() // Authenticate all other requests
            );
        
        return http.build();
    }

    // Configure CORS globally
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
//                        .allowedOrigins("http://localhost:3000") // Allow React frontend
                		.allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
//                        .allowCredentials(true);
                        .allowCredentials(false);
            }
        };
    }
}
