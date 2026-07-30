package com.laxmi.smart_banking_system.config;
import com.laxmi.smart_banking_system.service.CustomUserDetailsService;
import com.laxmi.smart_banking_system.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (request.getRequestURI().equals("/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
            return;
        }
        String email = jwtService.extractEmail(token);

        System.out.println("Logged in User : " + email);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        System.out.println("Valid JWT Token");

        filterChain.doFilter(request, response);

    }
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
}