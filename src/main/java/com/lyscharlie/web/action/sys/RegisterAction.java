package com.lyscharlie.web.action.sys;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyscharlie.biz.core.user.domain.UserDO;
import com.lyscharlie.biz.core.user.service.UserService;
import com.lyscharlie.common.dto.BaseResultDTO;
import com.lyscharlie.common.utils.EncryptUtils;
import com.lyscharlie.web.action.BaseAction;

public class RegisterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8741872155962696798L;

	@Autowired
	private UserService userService;

	private String msg;
	private UserDO user;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserDO getUser() {
		return user;
	}

	public void setUser(UserDO user) {
		this.user = user;
	}

	/**
	 * 注册页
	 * 
	 * @return
	 */
	public String register() {
		return "register";
	}

	public String doRegister() {
		if (!this.validateToken()) {
			request.setAttribute("message", "非法提交");
			return "error";
		}

		if (null == user) {
			return "register";
		}

		if (StringUtils.isBlank(user.getUserName())) {
			this.msg = "请输入用户名";
			return "register";
		}
		if (user.getUserName().length() > 15) {
			this.msg = "请输入用户名长度15个字";
			return "register";
		}
		if (!user.getUserName().matches("([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+")) {
			this.msg = "用户名只允许英文字母、数字、汉字";
			return "register";
		}
		if (StringUtils.isBlank(user.getPassword())) {
			this.msg = "请输入密码";
			return "register";
		}
		if (user.getPassword().length() < 6 || user.getPassword().length() > 10) {
			this.msg = "密码长度6-10位";
			return "register";
		}
		if (StringUtils.isBlank(user.getEmail())) {
			this.msg = "请输入邮箱";
			return "register";
		}
		if (!user.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
			this.msg = "邮箱不合法";
			return "register";
		}

		user.setPassword(EncryptUtils.encodeMD5(user.getPassword()));

		BaseResultDTO r = this.userService.registerUser(user);

		if (r.isSuccess()) {
			return "success";
		} else {
			user.setPassword(null);
			this.msg = r.getErrorDetail();
			return "register";
		}

	}

}
