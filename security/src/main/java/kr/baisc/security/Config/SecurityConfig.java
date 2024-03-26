package kr.baisc.security.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.baisc.security.Config.oauth.PricopalOauth2UserSerivce;
import kr.baisc.security.auth.PricipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PricopalOauth2UserSerivce pricopalOauth2UserSerivce;
    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> {
            web.ignoring().requestMatchers(new String[]{"/favicon.ico","/resources/**","/error"});
        });
    }
    @Bean
    AuthenticationFailureHandler customAuthFailurHandler(){
        return new CustomAuthFailureHandler();
    }
    @Bean
    public SecurityFilterChain fillerChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer ::disable);
        security.authorizeHttpRequests(authz->authz.requestMatchers("/user/**")
                .authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().permitAll()
        ).formLogin(
                form-> {
                    form.loginPage("/loginForm")
                            .loginProcessingUrl("/login")
                            .failureHandler(customAuthFailurHandler())
                            .successHandler(
                                    new AuthenticationSuccessHandler() {
                                        @Override
                                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                            response.sendRedirect("/");
                                        }
                                    }
                            );
                }
        ).oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer.loginPage("/loginForm").
                    userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(pricopalOauth2UserSerivce);
                    });
        });
        return security.build();
    }

}
