package dev.codeio.HelloWorld;

import dev.codeio.HelloWorld.Utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Arrays;


import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            System.out.println("Auth Header raw bytes: " + Arrays.toString(authHeader.getBytes()));
        }

        System.out.println("🔹 Request URL: " + request.getRequestURI());
        System.out.println("🔹 Auth Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("🔹 Token received: " + token);

            if (jwtUtil.validateJwtToken(token)) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("✅ Token valid for: " + email);

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                var auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("❌ Invalid token!");
            }
        } else {
            System.out.println("⚠️ No Authorization header or not Bearer format");
        }

        filterChain.doFilter(request, response);
    }
}
