package net.javaguides.springboot.shared.config;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.shared.exception.JwtAuthenticationEntryPoint;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {

        try {
            String token = jwtUtil.extractToken(request);

            if (token != null && jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            jwtAuthenticationEntryPoint.commence(
                    request,
                    response,
                    new InsufficientAuthenticationException("JWT expired", e)
            );
        } catch (JwtException e) {
            jwtAuthenticationEntryPoint.commence(
                    request,
                    response,
                    new InsufficientAuthenticationException("Invalid JWT", e)
            );
        }
    }
}

