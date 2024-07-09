package ar.germin.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final CorsGlobalConfiguration corsGlobalConfiguration;

    @Autowired
    public SecurityConfig(CorsGlobalConfiguration corsGlobalConfiguration) {
        this.corsGlobalConfiguration = corsGlobalConfiguration;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        String jwkSetUri = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_g34U2Zocx/.well-known/jwks.json";

        http
                .cors(corsSpec -> corsSpec.configurationSource(this.corsGlobalConfiguration))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/login").permitAll()
                        .pathMatchers("/api/v1/auth/signup").permitAll()
                        .pathMatchers("/api/v1/auth/confirm-signup").permitAll()
                        .pathMatchers("/webjars/**", "/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt -> jwt.jwkSetUri(jwkSetUri)));
        return http.build();
    }

}
