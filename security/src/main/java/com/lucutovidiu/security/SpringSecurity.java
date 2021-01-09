package com.lucutovidiu.security;

import com.lucutovidiu.domain.configs.EnvVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    //    private CustomUserDetailsService customUserDetailsService;
    private final EnvVariables envVariables;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:on
        http.csrf()
                .disable()
                .httpBasic()
                .and()

                .authorizeRequests()
                .antMatchers("/api/swagger**",
                        "/swagger**",
                        "/",
                        "/About",
                        "/Contact",
                        "/Login",
                        "/api/email/contact",
                        "/Portfolios")
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/api/**", "/Portfolios")
                .authenticated()
                .and()

                .formLogin()
                .loginPage("/Login")
                .permitAll()

                .and()
                .cors();
//                .and();
        //@formatter:off
//            .logout()
//                .logoutUrl("/logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                        .allowedOrigins(envVariables.getUrlForCrossOrigin())
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
