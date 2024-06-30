package com.amar.onlinestore.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.amar.onlinestore.springjwt.security.jwt.AuthEntryPointJwt;
import com.amar.onlinestore.springjwt.security.jwt.AuthTokenFilter;
import com.amar.onlinestore.springjwt.security.services.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .anyRequest().authenticated();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }
  
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.cors().and().csrf().disable()
      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests().antMatchers("/api/auth/**").permitAll()     
     .antMatchers(HttpMethod.POST, "/categories").hasAnyAuthority("ROLE_ADMIN")
       .antMatchers(HttpMethod.PUT, "/categories/{name}").hasAnyAuthority("ROLE_ADMIN")
       .antMatchers(HttpMethod.DELETE, "/categories/{name}").hasAnyAuthority("ROLE_ADMIN")
       .antMatchers(HttpMethod.DELETE, "/{id}/{image}").hasAnyAuthority("ROLE_ADMIN")
       .antMatchers(HttpMethod.DELETE, "/categories/{name}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.POST, "/products").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.DELETE, "/products/{id}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.PUT, "/products").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.PUT, "/products/{id}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.PUT, "/products/{productid}/colors").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.PUT, "/products/{productid}/tags").hasAnyAuthority("ROLE_ADMIN")

      .antMatchers(HttpMethod.PUT, "/products/{productId}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.GET, "/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.PUT, "/carts/{id}").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/categories/{name}/products").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/categories/{name}/products").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/customers/{id}/cart/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/customers/{id}/cart/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.DELETE, "/customers/{id}/cart/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/customers/{id}/cartitems").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/customers/{id}/orders").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/customers/{id}/orders").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.DELETE, "/customers/{id}/orders/{orderid}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.DELETE, "/customers/{id}/orders/{orderid}/cancel").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.PUT, "/customers/{id}/orders/{orderid}/complete").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.GET, "/customers/{id}/payments").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.POST, "/customers/{id}/payments").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.DELETE, "/customers/{id}/orders/{orderid}").hasAnyAuthority("ROLE_USER")



      .antMatchers(HttpMethod.DELETE, "/categories/{name}/products/{productid}").hasAnyAuthority("ROLE_ADMIN")

      .antMatchers(HttpMethod.POST, "/payment").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/payment/{paymentId}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.DELETE, "/payment/{paymentId}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.PUT, "/payment/{paymentId}").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.PUT, "/orders/{orderId}/complete").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.GET, "/orders").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.POST, "/orders").hasAnyAuthority("ROLE_USER")
      .antMatchers(HttpMethod.GET, "/orders/{orderId}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.GET, "/orders/status/{status}").hasAnyAuthority("ROLE_ADMIN")
      .antMatchers(HttpMethod.DELETE, "/orders/{orderId}/cancel").hasAnyAuthority("ROLE_USER")


      
      
      



      
      ;
      //.antMatchers("/api/test/**").permitAll()
    //  .anyRequest().authenticated();
  
  http.authenticationProvider(authenticationProvider());

  http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  
  return http.build();
}
}

