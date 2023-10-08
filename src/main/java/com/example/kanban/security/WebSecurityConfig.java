package com.example.kanban.security;

import com.example.kanban.security.jwt.JwtFilter;
import com.example.kanban.services.UsuarioServices;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{

    @Autowired
    UsuarioServices usuarioServices;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }

    // @Override
    public void configure(HttpSecurity http)throws  Exception{
        http
                //.httpBasic(Customizer.withDefaults())
              //  .csrf().disable()
                 .authorizeRequests()
                .requestMatchers("/publico/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
                //.and().cors()
                //.and()
              //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);



    }

   /* protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(usuarioServices);

        auth
                .inMemoryAuthentication()
                .withUser("david").password("{2580}" + "secreto").roles("USER")
                .and()
                .withUser("karen").password("{noop}" + "secreto").roles("ADMIN");
    }*/

    @Override
    public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler() {
        return super.webSecurityExpressionHandler();
    }

    @Override
    public Filter springSecurityFilterChain() throws Exception {
        return super.springSecurityFilterChain();
    }

    @Override
    public WebInvocationPrivilegeEvaluator privilegeEvaluator() {
        return super.privilegeEvaluator();
    }

    @Override
    public void setFilterChainProxySecurityConfigurer(ObjectPostProcessor<Object> objectPostProcessor, ConfigurableListableBeanFactory beanFactory) throws Exception {
        super.setFilterChainProxySecurityConfigurer(objectPostProcessor, beanFactory);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        super.setImportMetadata(importMetadata);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        super.setBeanClassLoader(classLoader);
    }
}
