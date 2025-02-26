package webdev.e_commerce.product_catalog_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static webdev.e_commerce.product_catalog_service.utils.api.APIURI.adminURI;
import static webdev.e_commerce.product_catalog_service.utils.api.APIURI.userURI;
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class OAuthSecurityConfig {
    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(userURI + "/**").permitAll()
                        .requestMatchers(adminURI + "/**").hasRole("ADMIN")
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/product-service/swagger-ui/**").permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        return http.build();
    }
}

