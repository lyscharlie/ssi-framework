package com.lyscharlie.web.core.token;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.lyscharlie.common.utils.EncryptUtils;
import com.lyscharlie.web.core.cookie.CookieUtil;

/**
 * token工具类
 * 
 * @author LiYishi
 */
public class TokenUtils {

	private static final String[] NUMBER_ARRAY = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	/**
	 * 生成
	 * 
	 * @param request
	 * @param response
	 * @param cookieKey
	 * @return 新建的内容
	 * @throws Exception
	 */
	public static Token createToken(HttpServletRequest request, HttpServletResponse response, String cookieKey) throws Exception {
		HttpSession session = request.getSession();

		String key = "tk" + new Date().getTime();
		String cookieValue = EncryptUtils.encodeBase64(key);

		CookieUtil.addCookie(response, cookieKey, cookieValue, 3600, null);

		// 组装value
		StringBuffer sb = new StringBuffer();
		sb.append(EncryptUtils.encodeBase64(key)).append(new Date().getTime());
		for (int i = 0; i < 6; i++) {
			sb.append(NUMBER_ARRAY[RandomUtils.nextInt(10)]);
		}

		String sessionValue = EncryptUtils.encodeSHA(sb.toString());

		session.setAttribute(key, sessionValue);

		Token token = new Token();
		token.setCookieKey(cookieKey);
		token.setCookieValue(cookieValue);
		token.setSessionKey(key);
		token.setSessionValue(sessionValue);
		token.setTokenValue(sb.toString());

		return token;
	}

	/**
	 * 刷新token
	 * 
	 * @param request
	 * @param response
	 * @param cookieKey
	 * @return 刷新后的内容
	 * @throws Exception
	 */
	public static Token reflashToken(HttpServletRequest request, HttpServletResponse response, String cookieKey) throws Exception {
		HttpSession session = request.getSession();

		String tokenKey = CookieUtil.getCookieValueByName(request, cookieKey);

		session.removeAttribute(EncryptUtils.decodeBase64(tokenKey));
		CookieUtil.deleteCookieByName(request, response, cookieKey);

		return createToken(request, response, cookieKey);
	}

	/**
	 * token校验
	 * 
	 * @param request
	 * @param response
	 * @param cookieKey
	 * @return
	 * @throws Exception
	 */
	public static boolean validateToken(HttpServletRequest request, HttpServletResponse response, String cookieKey) throws Exception {
		String tokenKey = CookieUtil.getCookieValueByName(request, cookieKey);

		if (StringUtils.isBlank(tokenKey)) {
			return false;
		}

		String key = EncryptUtils.decodeBase64(tokenKey);

		String tokenValue = request.getParameter(key);
		if (StringUtils.isBlank(tokenValue)) {
			return false;
		}

		HttpSession session = request.getSession();

		String value = (String) session.getAttribute(key);

		return StringUtils.equals(tokenValue, value);
	}

}
