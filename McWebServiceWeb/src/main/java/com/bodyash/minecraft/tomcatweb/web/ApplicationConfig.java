package com.bodyash.minecraft.tomcatweb.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bodyash.minecraft.tomcatweb.web.beans.ExternalPropertiesFileConfig;

@Configuration
@ComponentScan("com.bodyash.minecraft.tomcatweb.beans")
public class ApplicationConfig{
	
	private ExternalPropertiesFileConfig epfc;
	
	@Bean(name="epfc")
	public ExternalPropertiesFileConfig externalPropertiesFilesConfig() {
		if(epfc == null) {
			epfc = new ExternalPropertiesFileConfig();
		}
		return epfc;
	}

}
