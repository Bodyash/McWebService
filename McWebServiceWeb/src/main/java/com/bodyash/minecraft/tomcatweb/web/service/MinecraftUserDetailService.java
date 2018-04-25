package com.bodyash.minecraft.tomcatweb.web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MinecraftUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//throw new UsernameNotFoundException("Please, Dont Forget to rewrite MinecraftUserDetailService");
		return null;
	}

}
