package com.rays.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @authorHarsh Patidar
 *
 */
@Configuration
@EnableWebSecurity // methed level security.
//The prePostEnabled property enables Spring Security pre/post annotations.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 // configure AuthenticationManager so that it knows from where to  load user for matching credentials Use BCryptPasswordEncoder
		 
	  auth.userDetailsService(jwtUserDetailsService).passwordEncoder(
	  passwordEncoder()); 
	                          }

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("***passwordEncoder ");
		return new BCryptPasswordEncoder();
	}

	
/*
	* kis type ka authentication chahiye database, userDetailsService or etc type
	* ka.
	* AuthenticationManager ko bean provied karta he.ben depandent class ka object
	* provied krta he.
	* EX--> AuthenticationManager--return type liya class ka to eske liye es
	* AuthenticationManager class ka object return karega.
	* 
*/
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("***webSecurity Authentication manager ");
		return super.authenticationManagerBean();
	}

	// ****************DURGESH******ki vedio ke hisab se*******//
	// AuthenticationManagerBuilder-->ye bana or esme service class pass kr di usme
	// pahle se name de diya
	// OR data base se bhi le sakte he or usko es method me pass kar diya ab vahi
	// name and password forntend se aayega tab
	// jis controller pr token genrat kar re us pr-->
	// this.authenticationManager.authenticate(new
	// UsernamePasswordAuthenticationToken(form.getLoginId(),form.getPassword()));
	// ye methode se cross chek karenge sem hoga to fir
	// jwtUserDetaiesServise ki loadUserByUserName--> method me from.getloginId();
	// pass kar denge
	// UserDetails ka object token genrate ke time pr pass kar denge.

	/*
	 * durgesh ki vedio me dekha tha- ye kara tha es method me->
	 * auth.UserDetailsService(CustomeUserDetailsService);
	 * 
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {auth.UserDetailsService(CustomeUserDetailsService) ; }
	 * 
	 * 
	 * super.configure(auth)-->jab overried karte tab aata method ke andar
	 */

	@Override
	// kis type ki api ko authorization like kisko dena kisko nahi dena or kisko
	// nahi
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("****webSecurity httpSecurity ");
		// We don't need CSRF for this example

		httpSecurity.csrf().disable()

				// dont authenticate this particular request
				.authorizeRequests()
				.antMatchers("/Auth/**", "/User/profilePic/**","/Jasper/report/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// all other requests need to be authenticated
				.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		
		
		

		// Add a filter to validate the tokens with every request
		                             //coutom filter   //specified filter->main filter of springBoot
	
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		System.out.println("***jwtRequestFilter ke just bad inside WebSecurity");
		//configuration in Spring Security is used to enable Cross-Origin Resource Sharing (CORS) support. 
		httpSecurity.cors();
	}
}
