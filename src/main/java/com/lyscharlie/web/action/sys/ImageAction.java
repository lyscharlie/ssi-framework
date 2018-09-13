package com.lyscharlie.web.action.sys;

import com.lyscharlie.web.action.BaseAction;

public class ImageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6755573045862719056L;

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void upload() {

	}

	public String show() {
		return "show";
	}

}
