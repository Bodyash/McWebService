package com.bodyash.minecraft.tomcatweb.web.pages.home;

import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.minecraft.tomcatweb.web.pages.BasePage;

public class HomePage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HomePage() {
		getTitle().setDefaultModel(Model.of("Home Page"));
	}




}
