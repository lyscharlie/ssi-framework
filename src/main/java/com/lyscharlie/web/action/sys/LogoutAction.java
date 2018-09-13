package com.lyscharlie.web.action.sys;

import com.lyscharlie.web.action.BaseAction;
import com.lyscharlie.web.core.cookie.SessionKeeper;

public class LogoutAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7496022995595376859L;

	/**
	 * 系统登出
	 * 
	 * @return
	 */
	public String logout() {
		this.removeCookie(SessionKeeper.COOKIE.USER_ID);
		this.removeCookie(SessionKeeper.COOKIE.USER_NAME);
		this.removeSession(SessionKeeper.SESSION.USER_INFO);
		return "success";
	}

}
