package com.bodyash.minecraft.tomcatweb.web;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.wicket.protocol.http.WicketFilter;

@WebFilter(filterName = "WicketWebApp",
urlPatterns = {"/*"},
initParams = {
    @WebInitParam(name = "applicationClassName", value = "com.bodyash.minecraft.tomcatweb.web.WicketWebApplicationInitializer")})
public class MyFilter extends WicketFilter{
	
}