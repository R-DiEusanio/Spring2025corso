package com.example.corso.config;

import com.example.corso.repository.authRepository.UserRepository;
import com.example.corso.entitySecurity.Users;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class DBUserSecurityFilter extends OncePerRequestFilter{

    private final UserRepository userRepository;

    public DBUserSecurityFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Users user = userRepository.findByUsername(username).orElse(null);
            if(user == null) {

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().println("User not found in DB");
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
