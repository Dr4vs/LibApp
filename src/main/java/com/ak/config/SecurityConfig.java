package com.ak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ak.service.UserService;

//TODO: sprawdzić czy napewno okreslone jest całe security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final int ENCODE_STRENGTH = 10; //im wyższa wartość tym trudniej odszyfrować
	
	@Autowired
	private UserService userService;
	
	//włączenie, okreslenie szyfrowania 
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		//inicjalizujemy obiekt zwiazany z algorytmem szyforwania hasla
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(ENCODE_STRENGTH);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	//okreslenie autoryzacji odnosnie metod kontrolera =>trzeba być zalogowanym zeby zobaczyć wypożyczone ksiazki itd
	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .authorizeRequests()        // konfiguracja tego, ktore requesty maja byc autoryzowane a ktore nie
	                    .antMatchers("/login").permitAll()
	                    .antMatchers("/register").permitAll()
	                    .antMatchers("/resources/**").permitAll()
	                    .antMatchers("/users/**", "/create-user").hasRole("ADMIN")
	                    .antMatchers("/admin/**").hasRole("ADMIN")
	                    .antMatchers("/api/**").permitAll()
	                    .antMatchers("/**").authenticated()
	                .and()
	                .formLogin()                // konfigaracja formularza logowania
	                    .usernameParameter("email")
	                    .passwordParameter("password")
	                    .loginPage("/login")
	                    .loginProcessingUrl("/login")        // adres na ktory wysyalmy post z formularza logowania
	                .and()
	                .logout()
	                    .logoutUrl("/logout")
	                    .logoutSuccessUrl("/login?logout")  //?logout to info (parametr) ze sie wylogowano
	                .and()
	                .csrf().disable(); 
		
	}
	

}
