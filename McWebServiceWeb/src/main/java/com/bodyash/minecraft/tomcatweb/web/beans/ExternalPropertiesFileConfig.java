package com.bodyash.minecraft.tomcatweb.web.beans;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.io.IClusterable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ExternalPropertiesFileConfig implements IClusterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties prop;
	private IResource logo;

	public ExternalPropertiesFileConfig() {
		//Load Properties file
		Plugin plugin = Bukkit.getPluginManager().getPlugin("MinecraftWebService");
		File pluginDir = plugin.getDataFolder();
		loadFileToProperties(pluginDir);
		loadImgFile(pluginDir);
	}

	private void loadImgFile(File pluginDir) {
		File imgFile = new File(pluginDir, "logo.png");
		BufferedDynamicImageResource imageResource = null;
		if(!imgFile.exists()) {
			try {
				exportResource(imgFile, "logo.png");
				imageResource = getImageResource(imgFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			imageResource = getImageResource(imgFile);
		}
		logo = imageResource;
	}

	private BufferedDynamicImageResource getImageResource(File imgFile) {
		BufferedImage logoImg = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(imgFile)))){
			logoImg = ImageIO.read(bais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
		resource.setImage(logoImg);
		return resource;
	}

	private void loadFileToProperties(File pluginDir) {
		prop = new Properties();
		File propExtFile = new File(pluginDir, "serverConfig.properties");
		//export from unpacked war if not exist
		if (!propExtFile.exists()) {
			try {
				exportResource(propExtFile, "serverConfig.properties");
				loadProp(propExtFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			loadProp(propExtFile);
		}
	}
	//read data to properties Java Object
	private void loadProp(File propExtFile) {
		try (FileInputStream fis = new FileInputStream(propExtFile);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"))) {
			prop.load(isr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Resource Exporter
	private void exportResource(File externalFile, String internalFileName) throws Exception {
		try (InputStream in = this.getClass().getResourceAsStream("/" + internalFileName);
				OutputStream out = new FileOutputStream(externalFile)) {
			IOUtils.copy(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Properties getProperties() {
		return prop;
	}
	
	public IResource getLogoImage() {
		return logo;
	}

}
