package com.bodyash.minecraft.tomcatweb.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Default Spring Boot Wicket security getting started configuration. Its only
 * active if there is not other {@link WebSecurityConfigurerAdapter} present.
 * 
 * Holds hard coded users which should only be used to get started
 * 
 * @author Marc Giffing
 *
 */
@Configuration
public class WicketWebSecurityAdapterConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    @Qualifier("CustomAccountDetailService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationProvider authenticationProvider;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  	CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        http.csrf().disable();
        
      http.authorizeRequests()
        .antMatchers("/home/**").permitAll()
        .antMatchers("/adminpanel/**").access("hasRole('ADMIN')")
        .antMatchers("/cabinet").access("hasRole('USER')or hasRole('ADMIN')")
        .and().exceptionHandling().accessDeniedPage("/403");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
	}



}
