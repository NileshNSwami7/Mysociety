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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysociety.security.JwtAuthenticationEntryPoint;
import com.mysociety.security.JwtAuthenticationFilter;
import com.mysociety.security.JwtUtils;
import com.mysociety.security.OAuthAthenticationSuccessHandler;
import com.mysociety.security.PathSecuritProperties;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class UserSecuirtyConfiguration  {
	
	@Autowired
	private JwtUtils jwtutils;
	
	@Autowired
	private JwtAuthenticationEntryPoint unAuthorizeHandler;
	
	@Autowired
	private UserSecurityServiceImpl usersecurityerviceimpl;
	
	@Autowired
	private JwtAuthenticationFilter jwtauthenticationfilter;
	
	@Autowired
	private PathSecuritProperties pathsecurityproperties;
	 
	@Autowired
	private OAuthAthenticationSuccessHandler successhandler;
	
	@Bean
	public SecurityFilterChain securefilterchain(HttpSecurity http) throws Exception{	
		return http 
					.csrf(csrf->csrf.disable())
					.cors(cors->cors.disable())
					.exceptionHandling(exception->
						exception.authenticationEntryPoint(unAuthorizeHandler))
					.sessionManagement(session->
						session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
					.authorizeHttpRequests(auth->auth
//							.requestMatchers("/generate-token","/refresh-access-token","/mysociety/api/user/**").permitAll()
							.requestMatchers(pathsecurityproperties.getPublicpaths().toArray(new String[0])).permitAll()
							.requestMatchers(HttpMethod.OPTIONS).permitAll()
							.anyRequest().authenticated())
					.oauth2Login(oauth2 -> oauth2
//					        .defaultSuccessUrl("http://localhost:4200/signup", true)
					        .successHandler(successhandler)
					        )
					.addFilterBefore(jwtauthenticationfilter, UsernamePasswordAuthenticationFilter.class)
					.authenticationProvider(daoAthenticationProvider())
					.build();
				
					
					
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
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
		authprovider.setPasswordEncoder(passwordEncoder());
		return authprovider;
	}
	
	@Bean
	public JwtAuthenticationFilter jwtfilter() {
		return new JwtAuthenticationFilter(jwtutils, usersecurityerviceimpl);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	  return new WebMvcConfigurer() {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**")
	              .allowedOrigins("http://localhost:4200")
	              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	              .allowedHeaders("*")
	              .allowCredentials(true);
	    }
	  };
	}
}
