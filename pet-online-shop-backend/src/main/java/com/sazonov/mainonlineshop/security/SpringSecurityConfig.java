package com.sazonov.mainonlineshop.security;


import com.sazonov.mainonlineshop.security.JWT.JwtEntryPoint;
import com.sazonov.mainonlineshop.security.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@DependsOn("passwordEncoder")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    private JwtEntryPoint accessDenyHandler;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth
                .jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()



                .authorizeRequests()

//                .antMatchers("/product/add").access("hasAnyRole('ADMIN')")
//                .antMatchers("/product/add").permitAll()
                .antMatchers("/address/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/product/**").permitAll()
                .antMatchers("/wish-list/**").permitAll()
                .antMatchers("/category/findAllCategories").permitAll()
                .antMatchers("/category/add").permitAll()
                .antMatchers("/category/find").permitAll()
//                .antMatchers("/product/update").access("hasAnyRole('ADMIN','MANAGER')")
                .antMatchers("/profile/**").authenticated()
               // .antMatchers("/cart/**").access("hasAnyRole('CUSTOMER', 'ADMIN', 'MANAGER')")
                .antMatchers("/cart/**").permitAll()

                .antMatchers("/makeOrder").authenticated()
                .antMatchers("/addCard").authenticated()
                .antMatchers("/deleteCard").authenticated()
                .antMatchers("/getAllCards").authenticated()

                .antMatchers("/payment/**").hasAuthority("MANAGER")

                .antMatchers("/order/finish/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
                .antMatchers("/order/**").authenticated()
                .antMatchers("/profiles/**").authenticated()
                .antMatchers("/seller/product/new").access("hasAnyRole('MANAGER')")
                .antMatchers("/seller/**/delete").access("hasAnyRole( 'MANAGER')")
                .antMatchers("/seller/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
                .antMatchers("/seller/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
               // .antMatchers("/api/**").authenticated()
                //.antMatchers("/api/**").permitAll()
               // .antMatchers("/login").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/logout").authenticated()




                .anyRequest().permitAll()

                .and()
                .exceptionHandling().authenticationEntryPoint(accessDenyHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
