package com.dpa.news;

import com.dpa.news.services.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
               .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")  // Ruta de login
                .permitAll()
            )
            .logout(logout -> logout.logoutUrl("/logout").permitAll()); // Configurar URL de logout

        return http.build();
    }
    
    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
            .csrf().disable();

        return http.build();
    }*/
}
