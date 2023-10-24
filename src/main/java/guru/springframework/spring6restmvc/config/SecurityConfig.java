package guru.springframework.spring6restmvc.config;

import guru.springframework.spring6restmvc.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> {
                    authorize
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/api/**").authenticated();
                })
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(jsonAuthenticationEntryPoint(), new AntPathRequestMatcher("/api/**"))
                .and()
                .formLogin().successHandler(customAuthenticationSuccessHandler())
                .failureHandler(customAuthenticationFailureHandler()).and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new CustumLogoutHandler())
                .and()
                .cors().and()
                .csrf().disable()
                .oauth2Login().successHandler(customAuthenticationSuccessHandler());

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint jsonAuthenticationEntryPoint() {
        return (request, response, authException) -> {
//            if (request.getRequestURI().startsWith("/api/login")) {
//                return;
//            }
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String jsonErrorResponse = "{ \"message\": \"Unauthorized\" }";
            response.getWriter().write(jsonErrorResponse);
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }


    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Allow all origins
        configuration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, etc.)
        configuration.addAllowedHeader("*"); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }


}