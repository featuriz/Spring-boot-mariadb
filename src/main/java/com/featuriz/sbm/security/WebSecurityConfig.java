package com.featuriz.sbm.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// {noop} => No operation for password encoder (no password encoding needed)
//		auth.inMemoryAuthentication().withUser("devs").password("{noop}devs").authorities("ADMIN");
//		auth.inMemoryAuthentication().withUser("ns").password("{noop}ns").authorities("EMPLOYEE");
//		auth.inMemoryAuthentication().withUser("vs").password("{noop}vs").authorities("MANAGER");
		auth.jdbcAuthentication()
		.dataSource(dataSource)     //creates database connection
		.usersByUsernameQuery("select user_name,user_pass,user_enabled from user where user_name=?")
		.authoritiesByUsernameQuery("select user_name,user_role from user where user_name=?")
		.passwordEncoder(passwordEncoder);
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
}