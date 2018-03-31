package com.scushare.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scushare.entity.UserLogInfo;
import com.scushare.mapper.UserServiceMapper;
import com.scushare.service.UserLoginStatu;
import com.scushare.service.UserLogService;
import com.scushare.utils.PwdUtil;

@Service
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	private UserServiceMapper userMapper;

	/**
	 * 函数用于与数据库匹配用户名密码
	 * @param uname 用户名
	 * @param pwd 用户密码明文
	 * @return 返回登陆结果
	 */
	public UserLoginStatu login(String uname, String pwd) {
		
		UserLogInfo user = userMapper.getUserLogInfoByName(uname);
		if(user == null || !PwdUtil.confirmPwd(pwd, user.getUser_password())) {
			return UserLoginStatu.PWD_OR_UNAME_ERROR;
		} 
		String userLimit = user.getUser_limit();
		if(userLimit == null || userLimit.equals("")) {
			return UserLoginStatu.USER_INACTIVE;
		}
		return UserLoginStatu.LOGIN_SUCCESS;
	}
	
	public UserLoginStatu login(HttpServletRequest request) {
		String uname=(String)request.getParameter("user_name");
		String pwd=(String)request.getParameter("user_password");
		return login(uname,pwd);
	}

	/**
	 * 函数用于向request和response中添加登陆状态
	 * @param uesr_name 登录的用户名
	 * @param request 传入的request
	 * @param response 传入的response
	 */
	public void addLoginStatu(String user_name,HttpServletRequest request, HttpServletResponse response) {
		//获取uid
		int user_id = userMapper.getUserIdByName(user_name);
		//获取头像路径
		String user_chathead = userMapper.getUserChatheadById(user_id);
		if(user_chathead == null || user_chathead.equals("")) {
			user_chathead = "/userChathead/defaultChathead.jpg";
		}else {
			user_chathead = "/userChathead/" + user_chathead.substring(user_chathead.lastIndexOf("/") + 1);
		}
		//设置cookie
		Cookie cookie = new Cookie("last_user", user_name + "," + String.valueOf(user_id) + "," + user_chathead);
		//过期时间3天
		cookie.setMaxAge(3*24*60*60);
		response.addCookie(cookie);

		//设置session
		HttpSession session = request.getSession();
		session.setAttribute("user_name", user_name);
		session.setAttribute("user_id", user_id);
		session.setAttribute("user_chathead", user_chathead);
	}
	
	/**
	 * 函数用于更新登陆状态，只更新session，不更新cookie
	 * @param request 传入的request
	 */
	public void updateLoginStatu(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("user_name") != null) {
			return;
		}
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return;
		}
		//获取cookie中的用户名、id和头像路径
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("last_user")) {
				String[] cookieValue = cookie.getValue().split(",");
				session.setAttribute("user_name", cookieValue[0]);
				if(cookieValue.length > 1) {
					session.setAttribute("user_id", Integer.parseInt(cookieValue[1]));
				}
				if(cookieValue.length > 2) {
					session.setAttribute("user_chathead", cookieValue[2]);
				}
			}
		}
	}
	
	/**
	 * 用于注销登陆的函数，修改session和cookie
	 * @param request 传入的request
	 * @param response 传入的response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		//修改session
		HttpSession session = request.getSession();
		session.removeAttribute("user_name");
		session.removeAttribute("user_id");
		session.removeAttribute("user_chathead");
		
		//修改cookie
		Cookie cookie = new Cookie("last_user", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

}
