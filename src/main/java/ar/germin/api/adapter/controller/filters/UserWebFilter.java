package ar.germin.api.adapter.controller.filters;

import ar.germin.api.application.port.out.GetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class UserWebFilter implements WebFilter {
    private final GetUserRepository getUserRepository;

    @Autowired
    public UserWebFilter(@Qualifier("jdbc") GetUserRepository getUserRepository) {
        this.getUserRepository = getUserRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerWebExchange ex = exchange.mutate().request(builder -> builder.header("id-user", "32")).build();

        return chain.filter(ex);
        /*return exchange.getPrincipal()
                .flatMap(principal -> {
                    if (principal instanceof JwtAuthenticationToken) {
                        Jwt jwt = ((JwtAuthenticationToken) principal).getToken();
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(new JwtAuthenticationToken(jwt));
                        SecurityContextHolder.setContext(context);
                    }
                    return chain.filter(exchange);
                });*/
    }
}
