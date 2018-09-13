package com.lyscharlie.web.action.sys;

import org.apache.commons.lang3.StringUtils;

import com.lyscharlie.common.utils.EncryptUtils;
import com.lyscharlie.web.action.BaseAction;

public class IndexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -83727264504507653L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute() {
		String cookie2 = this.getCookie("cookie2");
		if (StringUtils.isNotBlank(cookie2)) {
			this.name = EncryptUtils.decodeBase64(cookie2);
		}

		request.setAttribute("b", "hello new world");

		return "index";
	}

}
