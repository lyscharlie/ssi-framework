package com.lyscharlie.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.lyscharlie.common.utils.EncryptUtils;
import com.lyscharlie.web.core.cookie.CookieUtil;
import com.lyscharlie.web.core.cookie.SessionKeeper;
import com.lyscharlie.web.core.token.Token;
import com.lyscharlie.web.core.token.TokenUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TokenInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3313387846764676266L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		HttpSession session = request.getSession();

		String tokenKey = CookieUtil.getCookieValueByName(request, SessionKeeper.COOKIE.TOKEN_KEY);// 编码后的key

		// 无cookie或者session失效
		if (StringUtils.isBlank(tokenKey) || null == session.getAttribute(EncryptUtils.decodeBase64(tokenKey))) {
			Token token = TokenUtils.createToken(request, response, SessionKeeper.COOKIE.TOKEN_KEY);

			request.setAttribute("token", token.getTokenHtml());
		} else {
			String sessionKey = EncryptUtils.decodeBase64(tokenKey);
			String sessionValue = (String) session.getAttribute(sessionKey);

			Token token = new Token();
			token.setSessionKey(sessionKey);
			token.setSessionValue(sessionValue);

			request.setAttribute("token", token.getTokenHtml());
		}

		return invocation.invoke();
	}

}
