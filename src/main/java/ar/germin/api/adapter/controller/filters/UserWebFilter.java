package ar.germin.api.adapter.controller.filters;

import ar.germin.api.application.port.out.GetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

        Mono<ServerWebExchange> newExchange = exchange
                .getPrincipal()
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(principal -> {
                    Jwt jwt = ((JwtAuthenticationToken) principal).getToken();
                    return jwt.getClaims().get("sub").toString();
                })
                .map(this.getUserRepository::getByHash)
                .map(user -> exchange
                        .mutate()
                        .request(builder ->
                                builder
                                        .header("id-user", user.getId().toString())
                                        .build())
                        .build());

        return newExchange
                .switchIfEmpty(Mono.just(exchange))
                .flatMap(chain::filter);
    }
}
