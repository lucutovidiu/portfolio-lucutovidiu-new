package com.lucutovidiu.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    //    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:on
        http.csrf()
                .disable()
                .httpBasic()
                .and()

                .authorizeRequests()
                .antMatchers("/swagger**",
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
}
