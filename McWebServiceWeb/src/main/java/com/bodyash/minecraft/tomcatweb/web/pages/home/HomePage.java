package com.bodyash.minecraft.tomcatweb.web.pages.home;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.bukkit.Bukkit;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.minecraft.tomcatweb.web.pages.BasePage;

@MountPath("/home")
public class HomePage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePage() {
		getTitle().setDefaultModel(Model.of("Home Page"));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Label lbl = new Label("test", new Model<String>() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public String getObject() {
				return Bukkit.getServer().getVersion() + " Online: " + Bukkit.getOnlinePlayers().size() + " of " + Bukkit.getMaxPlayers();
			}
		});
		lbl.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
		add(lbl);
		

	}

}
