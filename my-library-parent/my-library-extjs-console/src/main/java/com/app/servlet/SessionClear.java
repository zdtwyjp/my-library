package com.app.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.app.util.ParameterUtil;

public class SessionClear implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if(ParameterUtil.getSessionMap().containsKey(session.getId())){
			ParameterUtil.removeSession(session.getId());
		}
	}

}
