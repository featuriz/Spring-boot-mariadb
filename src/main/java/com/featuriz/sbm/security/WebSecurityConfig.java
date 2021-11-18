package com.featuriz.sbm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// {noop} => No operation for password encoder (no password encoding needed)
		auth.inMemoryAuthentication().withUser("devs").password("{noop}devs").authorities("ADMIN");
		auth.inMemoryAuthentication().withUser("ns").password("{noop}ns").authorities("EMPLOYEE");
		auth.inMemoryAuthentication().withUser("vs").password("{noop}vs").authorities("MANAGER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// declares which Page(URL) will have What access type
		http.authorizeRequests()
				.antMatchers("/sb/home").permitAll()
				.antMatchers("/sb/welcome").authenticated()
				.antMatchers("/sb/admin").hasAuthority("ADMIN")
				.antMatchers("/sb/emp").hasAuthority("EMPLOYEE")
				.antMatchers("/sb/mgr").hasAuthority("MANAGER")
				.antMatchers("/sb/common").hasAnyAuthority("EMPLOYEE", "MANAGER")

				// Any other URLs which are not configured in above antMatchers
				// generally declared aunthenticated() in real time
				.anyRequest().authenticated()

				// Login Form Details
				.and().formLogin().defaultSuccessUrl("/sb/welcome", true)

				// Logout Form Details
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				// Exception Details
				.and().exceptionHandling().accessDeniedPage("/sb/accessDenied");
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/sb/", "/sb/home").permitAll().anyRequest().authenticated().and()
//				.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
//	}
//
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("user")
//				.password(passwordEncoder().encode("password")).roles("USER").build();
//
//		return new InMemoryUserDetailsManager(user);
//	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}