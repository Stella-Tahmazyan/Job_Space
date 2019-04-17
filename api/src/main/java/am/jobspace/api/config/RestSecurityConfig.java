package am.jobspace.api.config;

import am.jobspace.api.security.JwtAuthenticationEntryPoint;
//import am.jobspace.api.security.JwtAuthenticationTokenFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
public class RestSecurityConfig {}
//    extends WebSecurityConfigurerAdapter {
//
//  @Autowired
//  private UserDetailsService userDetailsService;
//
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//
//  @Autowired
//  private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable()
//      // don't create session
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//      .and()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//      .and()
//      .authorizeRequests()
//      .antMatchers("/users").hasAnyAuthority("USER", "ADMIN")
//      .antMatchers("/user/update").hasAnyAuthority("USER", "ADMIN")
//      .antMatchers("/todos").hasAnyAuthority("USER", "ADMIN")
//      .antMatchers("/auth").permitAll()
//      .antMatchers("/user/add").permitAll()
//      .anyRequest().permitAll();
//
//    // Custom JWT based security filter
//    http
//      .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//
//    // disable page caching
//    http.headers().cacheControl();
//  }
//
//
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .userDetailsService(userDetailsService)
//      .passwordEncoder(passwordEncoder);
//  }
//
//  @Bean
//  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//    return new JwtAuthenticationTokenFilter();
//  }


//}