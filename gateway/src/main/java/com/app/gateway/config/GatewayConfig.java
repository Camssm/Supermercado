package com.app.gateway.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("productos", r -> r
                        .path("/api/productos/**")
                        .filters(f -> f.filter(
                                authenticationFilter.apply(
                                        new AuthenticationFilter.Config()
                                )
                        ))
                        .uri("lb://MS-PRODUCTOS"))

                .route("stock", r -> r
                        .path("/api/stock/**")
                        .filters(f -> f.filter(
                                authenticationFilter.apply(
                                        new AuthenticationFilter.Config()
                                )
                        ))
                        .uri("lb://MS-STOCK"))

                .route("facturacion", r -> r
                        .path("/api/facturas/**")
                        .filters(f -> f.filter(
                                authenticationFilter.apply(
                                        new AuthenticationFilter.Config()
                                )
                        ))
                        .uri("lb://MS-FACTURACION"))

                .route("usuarios", r -> r
                        .path("/api/usuario/**")
                        .filters(f -> f.filter(
                                authenticationFilter.apply(
                                        new AuthenticationFilter.Config()
                                )
                        ))
                        .uri("lb://MS-USUARIO"))

                .route("seguridad", r -> r
                        .path("/api/auth/**")
                        .uri("lb://SECURITY-SERVICE"))

                .build();
    }
}