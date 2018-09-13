package com.lyscharlie.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyscharlie.web.core.cookie.SessionKeeper;
import com.lyscharlie.web.core.token.Token;
import com.lyscharlie.web.core.token.TokenUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4979134481162712127L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 新增cookie内容
	 * 
	 * @param name
	 * @param value
	 * @param expireTime
	 */
	protected void addCookie(String name, String value, Integer expireTime) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(null != expireTime && expireTime > 0 ? expireTime : 3600);
		response.addCookie(cookie);
	}

	/**
	 * 取cookie内容
	 * 
	 * @param name
	 * @return
	 */
	protected String getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (StringUtils.equals(cookie.getName(), name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 移除cookie内容
	 * 
	 * @param name
	 */
	protected void removeCookie(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 新增session内容
	 * 
	 * @param name
	 * @param value
	 */
	protected void addSession(String name, Object value) {
		session.put(name, value);
	}

	/**
	 * 取session内容
	 * 
	 * @param name
	 * @return
	 */
	protected Object getSession(String name) {
		return session.get(name);
	}

	/**
	 * 移除session内容
	 * 
	 * @param name
	 */
	protected void removeSession(String name) {
		session.remove(name);
	}

	/**
	 * JSON输出
	 * 
	 * @param obj
	 */
	public void responseJson(Object obj) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		PrintWriter out = null;
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("JSON输出异常", e);
		}
		out.print(gson.toJson(obj));
		out.flush();
	}

	/**
	 * JSON输出
	 * 
	 * @param obj
	 */
	public void responseJsonWithNull(Object obj) {
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).create();
		PrintWriter out = null;
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("JSON输出异常", e);
		}
		out.print(gson.toJson(obj));
		out.flush();
	}

	/**
	 * JSONP输出
	 * 
	 * @param callback
	 * @param obj
	 */
	public void responseJsonp(String callback, Object obj) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("JSONP输出异常", e);
		}

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(callback)) {
			sb.append("callback");
		} else {
			sb.append(callback.trim());
		}
		sb.append("(").append(gson.toJson(obj)).append(")");

		out.print(sb.toString());
		out.flush();
	}

	/**
	 * JSONP输出
	 * 
	 * @param callback
	 * @param obj
	 */
	public void responseJsonpWithNull(String callback, Object obj) {
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).create();
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("JSONP输出异常", e);
		}

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(callback)) {
			sb.append("callback");
		} else {
			sb.append(callback.trim());
		}
		sb.append("(").append(gson.toJson(obj)).append(")");

		out.print(sb.toString());
		out.flush();
	}

	/**
	 * 字符串流输出
	 * 
	 * @param text
	 */
	public void responseString(String text) {
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("字符串输出异常", e);
		}

		out.print(text);
		out.flush();
	}

	/**
	 * token验证
	 * 
	 * @return
	 */
	public boolean validateToken() {
		try {
			boolean success = TokenUtils.validateToken(request, response, SessionKeeper.COOKIE.TOKEN_KEY);

			if (success) {
				Token token = TokenUtils.reflashToken(request, response, SessionKeeper.COOKIE.TOKEN_KEY);
				request.setAttribute("token", token.getTokenHtml());
			}

			return success;
		} catch (Exception e) {
			logger.error("token校验失败", e);
			return false;
		}
	}

}
