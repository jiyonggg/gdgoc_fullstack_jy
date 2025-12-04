package com.jiyonggg.spring_project1.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity // Spring Security 기능을 사용하기 위한 어노테이션
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        // withHttpOnlyTrue: 자바스크립트로 데이터 넘길 때 CSRF 방지 적용 X
                )
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요할 때만 세션 생성
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/loginPage", "/logout", "/noticeCheckPage", "/register", "/menu/all").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/resources/**", "/WEB-INF/**").permitAll()

                        // 운영자만 이용할 수 있음
                        .requestMatchers("/noticerAdd", "noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/menu/add").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/loginPage") // 로그인 페이지
                        .loginProcessingUrl("/login") // 로그인 처리 주소
                        .failureUrl("/loginPage?error=true") // 로그인 실패 시
                        .usernameParameter("username") // 아이디 input 태그의 name
                        .passwordParameter("password") // 아이디 input 태그의 name
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL 설정
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );

        return http.build();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {

            // 로그인 성공했을 때(세션, 권한 기능)
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                HttpSession session = request.getSession();
                boolean isManager = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals("ADMIN") ||
                                grantedAuthority.getAuthority().equals("MANAGER"));

                if (isManager) {
                    // 관리자라는 정보를 세션에 저장
                    session.setAttribute("MANAGER", true);
                }

                // 아이디(username)를 세션에 저장
                session.setAttribute("username", authentication.getName());

                // 인증되었음을 세션에 저장
                session.setAttribute("isAuthenticated", true);

                // 루트 경로로 리다이렉트
                response.sendRedirect(request.getContextPath()+"/");

                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // localhost:8080 서버에서는 데이터를 주고받을 수 있게 함
        // 일반적으로는, 프론트와 백 서버 둘 다 적어줘야 함
        // 지금은, JSP 기반의 스프링 부트 프로젝트이므로 8080 포트만으로 OK
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "https://localhost:8080"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 도메인에 적용
        return source;
    }
}
