package com.bodyash.minecraft.tomcatweb.web.beans;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class McWebSession extends AuthenticatedWebSession {

	public McWebSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}


}
