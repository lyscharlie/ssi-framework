package com.lyscharlie.web.action.sys;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyscharlie.biz.core.user.domain.UserDO;
import com.lyscharlie.biz.core.user.service.UserService;
import com.lyscharlie.common.dto.ResultDTO;
import com.lyscharlie.common.utils.EncryptUtils;
import com.lyscharlie.web.action.BaseAction;
import com.lyscharlie.web.core.cookie.SessionKeeper;

public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048193062110401683L;

	@Autowired
	private UserService userService;

	private String userName;
	private String password;
	private String msg;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	public String login() {
		return "login";
	}

	/**
	 * 登录操作
	 * 
	 * @return
	 */
	public String doLogin() {
		if (!this.validateToken()) {
			request.setAttribute("message", "非法提交");
			return "error";
		}

		if (StringUtils.isBlank(userName)) {
			this.msg = "请输入用户名";
			return "login";
		}

		if (StringUtils.isBlank(password)) {
			this.msg = "请输入密码";
			return "login";
		}

		ResultDTO<UserDO> r = this.userService.queryUserByName(userName);
		if (r.isSuccess()) {
			if (null == r.getModule()) {
				this.msg = "用户名或密码错误";
				return "login";
			} else {
				UserDO user = r.getModule();
				if (!StringUtils.equals(user.getPassword(), EncryptUtils.encodeMD5(password))) {
					this.msg = "用户名或密码错误";
					return "login";
				}

				this.addCookie(SessionKeeper.COOKIE.USER_ID, user.getUserId().toString(), null);
				this.addCookie(SessionKeeper.COOKIE.USER_NAME, EncryptUtils.encodeBase64(user.getUserName()), null);
				this.addSession(SessionKeeper.SESSION.USER_INFO, user);

				return "success";
			}

		} else {
			this.msg = "系统正忙，请稍后再试";
			return "login";
		}

	}

}
