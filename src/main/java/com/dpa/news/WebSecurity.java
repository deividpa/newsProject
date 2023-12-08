package com.dpa.news;

import com.dpa.news.services.UsernameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author David Perez
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurity {
    @Autowired
    private UsernameService usernameService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usernameService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(new AntPathRequestMatcher("/css/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/js/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/img/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
               .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")  // Login Route
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                //.defaultSuccessUrl("/home")
                .successHandler(successHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logoutSuccessUrl("/login")
                .logoutSuccessHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    if (session != null) {
                        session.setAttribute("logoutMessage", "You have successfully logged out.");
                    }
                        response.sendRedirect("/login");
                    })
                .permitAll());
        
        return http.build();
    }
    
    private AuthenticationSuccessHandler successHandler() {
            SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
            handler.setUseReferer(false);
            handler.setDefaultTargetUrl("/home");
        
            return handler;
    }
}
