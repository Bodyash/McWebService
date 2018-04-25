package com.bodyash.minecraft.tomcatweb.plugin;

import java.io.File;
import java.util.logging.Logger;
import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin {

	private final static Logger LOGGER = Bukkit.getLogger();
	private static Tomcat tomcat = null;

	@Override
	public void onEnable() {

		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}

		tomcat = new Tomcat();
		tomcat.setBaseDir(getDataFolder().getAbsolutePath());
		tomcat.getHost().setAppBase(getDataFolder().getAbsolutePath());
		tomcat.getEngine().setParentClassLoader(getClassLoader());
		tomcat.getServer().setParentClassLoader(getClassLoader());
		tomcat.getService().setParentClassLoader(getClassLoader());
		tomcat.getHost().setParentClassLoader(getClassLoader());
		tomcat.setHostname("localhost");
		tomcat.setPort(8080);
		tomcat.getConnector();
		tomcat.enableNaming();
		//org.apache.catalina.startup.ClassLoaderFactory.createClassLoader(File[], packed, parent)
		Context ctx = null;
		try {
			ctx = tomcat.addWebapp("", new File(getDataFolder(), "Empik.war").getAbsolutePath());
			ctx.setIgnoreAnnotations(false);
			ctx.setParentClassLoader(getClassLoader());
		} catch (ServletException | SecurityException e1) {
			e1.printStackTrace();
		}
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			LOGGER.severe("Tomcat could not be started.");
			e.printStackTrace();
		}
		LOGGER.info("Tomcat started on " + tomcat.getHost());
		
	}
}
