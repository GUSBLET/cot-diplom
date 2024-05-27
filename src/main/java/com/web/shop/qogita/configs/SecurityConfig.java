package com.web.shop.qogita.configs;


import com.web.shop.qogita.account.Account;
import com.web.shop.qogita.role.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/img/**", "/js/**", "/css/**", "/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .build();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            request.getSession().setAttribute("username", userDetails.getUsername());
            request.getSession().setAttribute("id", ((Account) userDetails).getId());
            request.getSession().setAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());
            response.sendRedirect("/");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .formLogin(formLogin -> formLogin
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/account/login-page")
                        .loginProcessingUrl("/authenticate")
                        .successHandler(authenticationSuccessHandler())
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout.logoutUrl("/account/logout")
                        .logoutSuccessUrl("/account/technical/login-page"))
                .exceptionHandling(e -> e.accessDeniedPage("/"))
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                    session.maximumSessions(3)
                            .maxSessionsPreventsLogin(false);
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/",
                            "/item",
                            "/account/recover-password-page",
                            "/account/login-page",
                            "/account/send-request-recover-password",
                            "/account/recover-password",
                            "/account/request-recover-password-page"
                    ).permitAll();
                    authorize.requestMatchers(
                            "/support/send-question",
                            "/account/setting"
                    ).authenticated();
                    authorize.requestMatchers(
                            "/support/questions-panel",
                            "/support/question",
                            "/support/send-answer",
                            "/order/admin/**",
                            "/account/admin/**",
                            "/admin/order/orders-panel",
                            "/admin/order/order-processing",
                            "/admin/order/process-order"
                    ).hasAnyAuthority(Authority.ADMIN.name());
                    authorize.requestMatchers(
                            "/order/**"
                    ).hasAnyAuthority(Authority.CUSTOMER.name());
                })
                .csrf(AbstractHttpConfigurer::disable).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

