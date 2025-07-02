package com.example.corso.filter;


import com.example.corso.repository.authRepository.UserRepository;
import com.example.corso.entitySecurity.Users;
import com.example.corso.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

    @Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtUtil jwtUtil;
        private final UserRepository userRepository;
        private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


        public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
            this.jwtUtil = jwtUtil;
            this.userRepository = userRepository;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                try {
                    username = jwtUtil.extractUsername(jwt);
                } catch (Exception e) {
                    logger.error("Invalid JWT token", e);
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                Optional<Users> usersOptional = userRepository.findByUsername(username);


                if (usersOptional.isPresent()) {
                    Users users = usersOptional.get();

                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(users.getUsername())
                            .password(users.getPassword())
                            .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + users.getRole())))
                            .build();

                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }

            }
            filterChain.doFilter(request, response);

        }

    }




