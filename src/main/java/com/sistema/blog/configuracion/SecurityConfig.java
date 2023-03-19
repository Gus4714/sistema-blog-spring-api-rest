package com.sistema.blog.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.sistema.blog.seguridad.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/{

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
/*	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		 .csrf().disable()
	   .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**").permitAll()
	 //  .authorizeRequests().antMatchers(HttpMethod.POST,"/api/**").hasRole("USER") //ESTE FUNCIONA
	//	.authorizeRequests().antMatchers(HttpMethod.POST,"/api/**").authenticated() //ESTE TAMBIEN FUNCIONA
		  .anyRequest().authenticated()
		 .and()
		 .httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}*/
	
	
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 
		http.authenticationProvider(getDaoAuthProvider());
		 
		return http
    		 .csrf().disable()
    	   .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**").permitAll()
    		// .authorizeRequests().antMatchers("/api/**").permitAll()
    		//.and()
    	   .anyRequest().authenticated()
    	//	.authorizeHttpRequests().anyRequest().hasRole("ADMIN")
    	  // anyRequest().hasRole("ADMIN")
    		 .and()
    		 .httpBasic()
    		 .and()
    		
    		 //.authorizeHttpRequests().anyRequest().authenticated()
    		// .and()        	 
    		 .build();
     
    }
	
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

/*	@Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        		.userDetailsService(userDetailsService)
        		.passwordEncoder(passwordEncoder())
        		.and()
        		.build();
	}*/
		
	/*	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
         authenticationManagerBuilder
               .userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder());
         return authenticationManagerBuilder.build();*/
	
/*	@Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
         AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
         authenticationManagerBuilder
               .userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder());
         return authenticationManagerBuilder.build();
     }*/
	
	/* @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
	            throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }*/
	
	//PARA CREAR USUARIO EN MEMORIA DE EJECUCION ESTE FUNCIONA AL 100%
	
/*	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails joaquin = User.builder().username("jbaranda")
				.password(passwordEncoder().encode("123")).roles("USER").build();
		
	UserDetails admin = User.builder().username("admin")
				.password(passwordEncoder().encode("123")).roles("ADMIN").build();
		return new InMemoryUserDetailsManager(admin,joaquin);
	}*/
	


}
