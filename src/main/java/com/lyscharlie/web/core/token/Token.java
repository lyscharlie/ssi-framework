package com.lyscharlie.web.core.token;

/**
 * 令牌对象
 * 
 * @author LiYishi
 */
public class Token {

	/**
	 * cookie的key
	 */
	private String cookieKey;
	/**
	 * cookie的value（base64以后，解码后即为sessionKey）
	 */
	private String cookieValue;
	/**
	 * session的key（页面input框的name）
	 */
	private String sessionKey;
	/**
	 * session加密后的值（页面input框的value）
	 */
	private String sessionValue;
	/**
	 * 未加密的值
	 */
	private String tokenValue;

	public String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSessionValue() {
		return sessionValue;
	}

	public void setSessionValue(String sessionValue) {
		this.sessionValue = sessionValue;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	/**
	 * input框html代码
	 * 
	 * @return
	 */
	public String getTokenHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<input type=\"hidden\" name=\"").append(sessionKey).append("\" value=\"").append(sessionValue).append("\">");
		return sb.toString();
	}

}
