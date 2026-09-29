package com.chatapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Check which request is coming
        System.out.println(
                "REQUEST: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI());

        // 2. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        System.out.println("AUTH HEADER: " + authHeader);

        // 3. If no Bearer token, continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO BEARER TOKEN");

            filterChain.doFilter(request, response);
            return;
        }

        // 4. Remove "Bearer " from header
        String token = authHeader.substring(7);

        System.out.println("TOKEN RECEIVED: " + token);

        // 5. Validate JWT
        boolean tokenValid = jwtService.isTokenValid(token);

        System.out.println("TOKEN VALID: " + tokenValid);

        if (tokenValid) {

            // 6. Extract user ID from JWT
            Long userId = jwtService.extractUserId(token);

            System.out.println("USER ID FROM JWT: " + userId);

            // 7. Create authenticated user
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    Collections.emptyList());

            // 8. Add request details
            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request));

            // 9. Store authentication in SecurityContext
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println("AUTHENTICATION SET SUCCESSFULLY");
        }

        // 10. Continue request
        filterChain.doFilter(request, response);
    }
}