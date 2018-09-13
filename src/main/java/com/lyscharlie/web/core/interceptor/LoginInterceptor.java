package com.lyscharlie.web.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.lyscharlie.web.core.cookie.CookieUtil;
import com.lyscharlie.web.core.cookie.SessionKeeper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 登录拦截器
 * 
 * @author LiYishi
 */
public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6430457150180139151L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		ValueStack vs = ctx.getValueStack();
		Map<String, Object> session = ctx.getSession();

		String userId = CookieUtil.getCookieValueByName(request, SessionKeeper.COOKIE.USER_ID);
		String userName = CookieUtil.getCookieValueByName(request, SessionKeeper.COOKIE.USER_NAME);

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(userName) || null == session.get(SessionKeeper.SESSION.USER_INFO)) {
			CookieUtil.deleteCookieByName(request, response, SessionKeeper.COOKIE.USER_ID);
			CookieUtil.deleteCookieByName(request, response, SessionKeeper.COOKIE.USER_NAME);
			session.remove(SessionKeeper.SESSION.USER_INFO);

			// 将重定向的url压入ValueStack的数据map，用于重定向ongl取值
			StringBuffer redirectUrl = new StringBuffer(request.getServletPath());
			if (StringUtils.isNotBlank(request.getQueryString())) {
				redirectUrl.append("?").append(request.getQueryString());
			}
			vs.set("redirectUrl", redirectUrl.toString());
			return "login";
		}

		return invocation.invoke();
	}

}
