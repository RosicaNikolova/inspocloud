//package com.cloudinspo.apigateway.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//
//// Class responsible for filtering requests and handling JWT.
//@Component
//@RequiredArgsConstructor
//public class SubClaimFilter implements WebFilter, Ordered {
//
//    // Injected Decoder to decode JWTs.
//    private final ReactiveJwtDecoder jwtDecoder;
//
//    // Overridden filter method which applies this filter to all requests to /api/photos.
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        var request = exchange.getRequest();
//
//        // If the request path doesn't start with /api/photos we simply continue the chain without changing anything.
//        if (!request.getURI().getPath().startsWith("/api/photos")) {
//            return chain.filter(exchange);
//        }
//
//        // If the request path does start with /api/photos we extract the token, get "sub" claim and reset the request.
//        return jwtDecoder.decode(extractToken(request))
//                .map(jwt -> jwt.getClaimAsString("sub"))
//                .flatMap(subClaim -> chain.filter(withSubClaimInExchange(exchange, subClaim)));
//    }
//
//    // Create a new exchange with an added 'X-User-Id' header value taken from the subClaim.
//    private ServerWebExchange withSubClaimInExchange(ServerWebExchange exchange, String subClaim) {
//        var request = exchange.getRequest();
//        ServerHttpRequest newRequest = request.mutate()
//                .header("X-User-Id", subClaim)
//                .build();
//        return exchange.mutate()
//                .request(newRequest)
//                .build();
//    }
//
//    // Extract the token from the Authorization header.
//    private String extractToken(ServerHttpRequest request) {
//        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        // Check if the token is a Bearer token and if so, remove the 'Bearer ' prefix.
//        if (token != null && token.startsWith("Bearer ")) {
//            return token.substring(7);
//        }
//        // If no token could be extracted, return empty string.
//        return "";
//    }
//
//    // Set the order in which this filter is applied. Lowest precedence means it will be run last among all other filters.
//    @Override
//    public int getOrder() {
//        return Ordered.LOWEST_PRECEDENCE;
//    }
//
//}
