package com.softsync.zerock.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/potal").hasAnyRole("USER")
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .addLogoutHandler(customLogoutHandler())
                .permitAll()
            )
        	.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/login")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                setCookie(response, "userAccess", 7, true); // 모든 사용자에게 적용될 쿠키
                response.sendRedirect("/potal"); // 모든 사용자가 로그인 후 보게 될 공통 홈페이지
            } catch (IOException e) {
                logger.error("Failed to redirect after login", e);
            }
        };
    };

    private void setCookie(HttpServletResponse response, String name, int daysToExpire, boolean isSecure) {
        Cookie cookie = new Cookie(name, "true");
        cookie.setMaxAge(daysToExpire * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(isSecure); // Set to true if using HTTPS
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private LogoutHandler customLogoutHandler() {
        return (request, response, authentication) -> {
            if (authentication != null) {
                authentication.getAuthorities().forEach(authority -> {
                    String cookieName = "";
                    switch (authority.getAuthority()) {
                        case "ROLE_USER":
                            cookieName = "userAccess";
                            break;                      
                    }
                    Cookie cookie = new Cookie(cookieName, null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                });
                try {
                    request.getSession().setAttribute("logoutMessage", "로그아웃 되었습니다.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}