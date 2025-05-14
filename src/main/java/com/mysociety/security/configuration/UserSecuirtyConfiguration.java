package com.mysociety.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysociety.security.JwtAuthenticationEntryPoint;
import com.mysociety.security.JwtAuthenticationFilter;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class UserSecuirtyConfiguration  {
	
	@Autowired
	private JwtAuthenticationEntryPoint unAuthorizeHandler;
	
	@Autowired
	private UserSecurityServiceImpl usersecurityerviceimpl;
	
	@Autowired
	private JwtAuthenticationFilter jwtauthenticationfilter;
	 
	
	@Bean
	public SecurityFilterChain securefilterchain(HttpSecurity http) throws Exception{	
		return http 
					.csrf(csrf->csrf.disable())
					.cors(cors->cors.disable())
					.exceptionHandling(exception->
						exception.authenticationEntryPoint(unAuthorizeHandler))
					.sessionManagement(session->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authorizeHttpRequests(auth->auth
							.requestMatchers("/generate-token","/user/").permitAll()
							.requestMatchers(HttpMethod.OPTIONS).permitAll()
							.anyRequest().authenticated())
					.addFilterBefore(jwtauthenticationfilter, UsernamePasswordAuthenticationFilter.class)
					.authenticationProvider(daoAthenticationProvider())
					.build();
				
					
					
	}
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
//	@Bean
//	public PasswordEncoder passwordencoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	@Bean
	protected AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configure)throws Exception{
		return configure.getAuthenticationManager();
	}
	
	public DaoAuthenticationProvider daoAthenticationProvider() {
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
		authprovider.setUserDetailsService(this.usersecurityerviceimpl);
		authprovider.setPasswordEncoder(passwordencoder());
		return authprovider;
	}
	
}
