package com.bodyash.minecraft.tomcatweb.web.pages;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.bodyash.minecraft.tomcatweb.web.beans.ExternalPropertiesFileConfig;
import com.bodyash.minecraft.tomcatweb.web.pages.login.LoginPage;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public abstract class BasePage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MarkupContainer defaultModal;
	private Label title;
	protected List<String> localeNames;
	protected DropDownChoice<String> localeDDC;
	protected Image logoImage;
	private Link<String> loginLink;
	private Link<String> cabinetLink;
	private Link<String> logoutLink;

	@SpringBean(name="epfc")
	protected ExternalPropertiesFileConfig externalPropertiesFileConfig;


	public Label getTitle() {
		return title;
	}


	public BasePage(PageParameters params){
		super(params);
		initPage();
	}
	
	public BasePage(){
		initPage();
	}
	
	private void initPage(){
		defaultModal = new EmptyPanel("defaultModal");
		defaultModal.setOutputMarkupId(true);
		add(defaultModal);
		logoImage = new Image("logoImage", getExternalPropertiesFileConfig().getLogoImage());
		add(logoImage);
		title = new Label("title", "Minecraft Subscribe Service");
		add(title);
		add(new Label("serverName",
				Model.of(" " + externalPropertiesFileConfig.getProperties().getProperty("serverName", "serverName key not Set"))));
		createLocalesDCC();
		createHeaderLinks();
	}
	private void createHeaderLinks() {
		loginLink = new Link<String>("loginLink", new ResourceModel("loginLink")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};

		logoutLink = new Link<String>("logoutLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};

		cabinetLink = new Link<String>("cabinetLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};
		loginLink.add(new Label("label", new ResourceModel("loginLink")));
		logoutLink.add(new Label("label", new ResourceModel("logoutLink"))).setVisible(false);
		cabinetLink.add(new Label("label", new ResourceModel("cabinetLink"))).setVisible(false);
		

		add(loginLink, logoutLink, cabinetLink);

	}

	@SuppressWarnings("rawtypes")
	private void createLocalesDCC() {
		List<Locale> locales = Arrays.asList(Locale.ENGLISH, new Locale("ru"));
		Form ddcForm = new Form("ddcForm");
		final DropDownChoice<Locale> localeDDCSelection = new DropDownChoice<Locale>("changeLocale",
				getDDCModel(), locales, getLocalesChoiseRenderer());
		localeDDCSelection.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(getPage());
			}
			
			
			
		});
		localeDDCSelection.setModelObject(Session.get().getLocale());
		ddcForm.add(localeDDCSelection);
		add(ddcForm);
	}
	
	private IModel<Locale> getDDCModel(){
		return new IModel<Locale>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Locale getObject() {
				return getSession().getLocale();
			}
			
			@Override
			public void setObject(Locale object) {
				if (object != null) {
					getSession().setLocale(object);
				}
			}
		};
	}

	private IChoiceRenderer<Locale> getLocalesChoiseRenderer() {
		return new IChoiceRenderer<Locale>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Locale object) {
				String displayValue = object.getDisplayLanguage(object);
				return displayValue.substring(0, 1).toUpperCase() + displayValue.substring(1);
			}

			@Override
			public String getIdValue(Locale object, int index) {
				return object.getDisplayLanguage();
			}

			@Override
			public Locale getObject(String id, IModel<? extends List<? extends Locale>> choices) {
				List<? extends Locale> _choices = choices.getObject();
				for (int index = 0; index < _choices.size(); index++) {
					// Get next choice
					final Locale choice = _choices.get(index);
					if (getIdValue(choice, index).equals(id)) {
						return choice;
					}
				}
				return null;
			}
		};
	}
	
	public void replaceDefaultModal(ModalWindow newModal){
		defaultModal.replaceWith(newModal);
		defaultModal = newModal;
		defaultModal.setOutputMarkupId(true);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getWicketAjaxReference()));
		
		String bootstrapPrefixPath = "bootstrap/current";
        response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference(bootstrapPrefixPath + "/js/bootstrap.js")));
		response.render(CssHeaderItem.forReference(new WebjarsJavaScriptResourceReference(bootstrapPrefixPath + "/css/bootstrap.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "../css/style.css")));
	}


	public ExternalPropertiesFileConfig getExternalPropertiesFileConfig() {
		return externalPropertiesFileConfig;
	}


	public void setExternalPropertiesFileConfig(ExternalPropertiesFileConfig externalPropertiesFileConfig) {
		this.externalPropertiesFileConfig = externalPropertiesFileConfig;
	}
}
