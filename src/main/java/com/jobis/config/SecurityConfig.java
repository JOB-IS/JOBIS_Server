package com.jobis.config;

import com.jobis.config.security.JwtAccessDeniedHandler;
import com.jobis.config.security.JwtAuthenticationEntryPoint;
import com.jobis.config.security.JwtFilter;
import com.jobis.config.security.TokenProvider;
import com.jobis.domain.code.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    // save plain text in password
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  public void configure(WebSecurity web) {
    // security 제외
    web.ignoring()
        .antMatchers("/v3/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html",
            "/webjars/**", "/swagger/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .cors().disable()
        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
          .authenticationEntryPoint(jwtAuthenticationEntryPoint)
          .accessDeniedHandler(jwtAccessDeniedHandler)
        .and()
        .authorizeRequests()
          .antMatchers("/api/v1/auth/additional-info").hasRole(AuthType.ROLE_PRE_USER.getValueWithoutPrefix())
          .antMatchers("/api/v1/auth/**").permitAll()

          // TODO DELETE START
          .antMatchers("/api/v1/hello/real-user").hasRole(AuthType.ROLE_USER.getValueWithoutPrefix())
          .antMatchers("/api/v1/hello/only-pre-user").hasRole(AuthType.ROLE_PRE_USER.getValueWithoutPrefix())
          // TODO DELETE END

          .antMatchers("/api/**").hasRole(AuthType.ROLE_USER.getValueWithoutPrefix())
          .anyRequest().denyAll();
  }

}