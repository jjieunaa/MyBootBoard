package kr.jieun.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.jieun.security.service.impl.SecurityUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.userDetailsService(userDetailsService);
		
		security.authorizeRequests()
			.antMatchers("/", "/system/**").permitAll()
			.antMatchers("/board").authenticated()
			.antMatchers("/reply").authenticated()
			.antMatchers("/admin").hasRole("ADMIN");
		
		security.csrf().disable();
		security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/listBoard", true);
		security.exceptionHandling().accessDeniedPage("/system/accessDenied");
		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
	}

}
