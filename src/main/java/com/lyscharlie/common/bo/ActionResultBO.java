package com.lyscharlie.common.bo;

import java.io.Serializable;

/**
 * action层向页面返回的信息
 * 
 * @author LiYishi
 */
public class ActionResultBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1464589837377058988L;

	/**
	 * 操作结果标志
	 */
	private boolean success;

	/**
	 * 返回信息
	 */
	private String msg;

	/**
	 * service层返回对象
	 */
	private Object dataObject;

	/**
	 * 消息code
	 */
	private String msgCode;

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ActionResultBO(boolean success, String msg){
		super();
		this.success = success;
		this.msg = msg;
	}

	public ActionResultBO(){
		super();
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public ActionResultBO toSuccess(Object dataObject) {
		this.success = true;
		this.dataObject = dataObject;
		return this;
	}

	public ActionResultBO toFail(String msg) {
		this.success = false;
		this.msg = msg;
		return this;
	}

	public ActionResultBO toFail(String msgCode, String msg) {
		this.success = false;
		this.msgCode = msgCode;
		this.msg = msg;
		return this;
	}
}
