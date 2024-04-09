package com.delitech.revealing.auth.config;

import com.delitech.revealing.auth.jwt.JwtService;
import com.delitech.revealing.auth.service.CustomDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomDetailService customDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        final String token = getTokenFromRequest(request);
        final String username;
        final String type;
        try {
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            username = jwtService.getUsernameFromToken(token);
            type = jwtService.getClaim(token, claims -> claims.get("type", String.class));

            if (username != null && type != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customDetailService.loadUserByUsernameAndType(username, type);

                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            log.info(e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader)){
            return authHeader.replace("Bearer ","");
        } else return null;
    }
}
