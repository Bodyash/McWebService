package com.bodyash.minecraft.tomcatweb.web;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.bukkit.Bukkit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.wicketstuff.annotation.scan.AnnotatedMountList;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.pageserializer.fast2.Fast2WicketSerializer;

import com.bodyash.minecraft.tomcatweb.web.beans.McWebSession;
import com.bodyash.minecraft.tomcatweb.web.pages.login.LoginPage;

import de.agilecoders.wicket.webjars.WicketWebjars;


@ComponentScan(basePackages = {"com.bodyash.minecraft.tomcatweb"})
public class WicketWebApplicationInitializer extends AuthenticatedWebApplication {
	
	@Override
	protected void init() {
		//strip tags
		getMarkupSettings().setStripWicketTags(true);
		getMarkupSettings().setDefaultMarkupEncoding(StandardCharsets.UTF_8.name());
		//Spring Context
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		//Beans definitions (annotations)
	    rootContext.register(ApplicationConfig.class);
	    //install Spring Context for spring dependency injection
		getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);
		SpringComponentInjector springComponentInjector = new SpringComponentInjector(this , rootContext, true);
		getComponentInstantiationListeners().add(springComponentInjector);
		//refresh for dependency injecting.
		rootContext.refresh();
		//webjars
		WicketWebjars.install(this);
		//Never Change Fast2WicketSerializer to smthg else, because KRYO, KRYO2 and JavaSerializer is not working for this project!
		Fast2WicketSerializer serFast2 = new Fast2WicketSerializer();
		getFrameworkSettings().setSerializer(serFast2);
		//mounts
		initDefaultPageMounts();
		//init webapp
		super.init();
	}
	
	protected void initDefaultPageMounts()
	{
//	    mountPage("/login", getSignInPageClass());
//	    mountPage("/home", getHomePage());
	    
	    // Mount the other pages via @MountPath annotation on the page classes
	    AnnotatedMountList mounts = new AnnotatedMountScanner().scanPackage("com.bodyash.minecraft.tomcatweb");
	    for (IRequestMapper mapper : mounts) {
	      Bukkit.getLogger().log(Level.WARNING, mapper.toString());
	    }
	    mounts.mount(this);
	}
	

	@Override
	public Class<? extends Page> getHomePage() {
		return com.bodyash.minecraft.tomcatweb.web.pages.home.HomePage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return McWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}
	
	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEPLOYMENT;
	}
	
	

}
