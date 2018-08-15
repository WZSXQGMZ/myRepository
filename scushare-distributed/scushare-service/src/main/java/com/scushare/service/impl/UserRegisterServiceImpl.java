package com.scushare.service.impl;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.service.UserRegisterResult;
import com.scushare.service.UserRegisterService;
import com.scushare.task.MailSendTask;
import com.scushare.utils.DateUtil;
import com.scushare.utils.MailUtil;
import com.scushare.utils.PwdUtil;
import com.scushare.utils.SerializationUtil;
import com.scushare.utils.SymCrypUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.scushare.mapper.UserServiceMapper;

import scu.pojo.User;

@Service("UserRegisterService")
public class UserRegisterServiceImpl implements UserRegisterService{

	@Autowired
	private UserServiceMapper userMapper;
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 此函数用于将form提交的注册信息进行处理后保存到数据库
	 * @param request 携带注册信息的request
	 * @return 返回注册结果
	 */
	public UserRegisterResult register(HttpServletRequest request) {
		User user = new User();
		if(addRegisterInfo(user, request) == false){
			return UserRegisterResult.USER_INFO_ERROR;
		}
		//加密密码
		String encryptedPassword = PwdUtil.encryptPwd(user.getUserPassword());
		user.setUserPassword(encryptedPassword);
		//插入注册信息到数据库，出错返回DB_INSERT_ERROR
		int insertResult = insertRegisterInfo(user);
		if(insertResult != 1) {
			return UserRegisterResult.DB_INSERT_ERROR;
		}
		try {
			Jedis jedis = jedisPool.getResource();
			jedis.lpush(MailSendTask.MAIL_SEND_QUEUE, user.getUserMail() + "," + user.getUserName());
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return UserRegisterResult.MAIL_SEND_ERROR;
		}
		
		//正常返回REGISTER_SUCCESS
		return UserRegisterResult.REGISTER_SUCCESS;
	}
	
	/**
	 * 函数用于验证邮箱确认连接中的数据
	 * @param request 携带数据参数的request
	 */
	public UserRegisterResult mailCheck(HttpServletRequest request) {
		String arg = request.getParameter("arg");
		if(arg == null) {
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		String plaintext = null;
		//解密数据
		try {
			plaintext = SymCrypUtil.decrypt(arg, SymCrypUtil.getKey());
		} catch (Exception e) {
			e.printStackTrace();
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		try {
			String[] result = plaintext.split(",");
			//如果链接格式不正确
			if(result.length != 3) {
				return UserRegisterResult.MAIL_CHECK_ERROR;
			}
			//判断是否过期
			int time = DateUtil.minutesBetweenDate(result[2], DateUtil.getDate());
			if(time > 30) {
				return UserRegisterResult.MAIL_OVERDUE_ERROR;
			}
			//判断数据是否正确
			String user_checked = userMapper.getUserByNameAndMail(result[0], result[1]);
			if(user_checked == null) {
				return UserRegisterResult.MAIL_CHECK_ERROR;
			}
			//更新用户权限，暂不判断
			userMapper.updateUserLimitByName(user_checked, "normal");
		}catch(Exception exception) {
			exception.printStackTrace();
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		return UserRegisterResult.MAIL_CHECK_SUCCESS;
	}
	
	/**
	 * 此函数用于将form提交的注册信息保存到User类中
	 * @param user 要保存到的User类
	 * @param request 携带注册信息的request
	 * @return 添加是否成功
	 */
	public boolean addRegisterInfo(User user, HttpServletRequest request) {
		String infoString = null;
		infoString = request.getParameter("user_name");
		if(infoString == null || infoString == "") {
			return false;
		}
		user.setUserName(infoString);
		
		infoString = request.getParameter("user_password");
		if(infoString == null || infoString == "" || infoString.length() > 16) {
			return false;
		}
		user.setUserPassword(infoString);

		infoString = request.getParameter("user_mail");
		if(infoString == null) {
			return false;
		}
		user.setUserMail(infoString);		

		infoString = request.getParameter("user_phone_num");
		if(infoString == null) {
			infoString = "";
		}
		user.setUserPhoneNum(infoString);

		infoString = request.getParameter("user_gender");
		if(infoString == null) {
			infoString = "男";
		}
		if(infoString.equals("男")) {
			user.setUserGender(true);
		}else {
			user.setUserGender(false);
		}

		infoString = request.getParameter("user_grade");
		if(infoString == null || infoString.length() != 1 || !Character.isDigit(infoString.charAt(0))) {
			infoString = "1";
		}
		user.setUserGrade(Character.getNumericValue(infoString.charAt(0)));
		
		infoString = request.getParameter("user_college");
		if(infoString == null) {
			infoString = "";
		}
		user.setUserCollege(infoString);
		
		infoString = request.getParameter("user_major");
		if(infoString == null) {
			infoString = "";
		}
		user.setUserMajor(infoString);

		String ip = request.getRemoteAddr();
		if(ip == null) {
			ip = "";
		}
		user.setUserRegIp(ip);
		
		return true;
	}
	
	/**
	 * 函数用于发送验证邮件
	 * @param mailAddress 待验证邮箱
	 * @param user_name 用户名
	 * @return 邮件发送是否成功
	 */
	public boolean sendConfirmMail(String mailAddress, String user_name) {
		String plaintext = user_name + "," + mailAddress + "," + DateUtil.getDate();
		String ciphertext = null;
		try {
			//加密用户名和邮箱
			ciphertext = SymCrypUtil.encrypt(plaintext, SymCrypUtil.getKey());
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
		String url = "http://localhost/scushare/checkMail?arg=" + ciphertext;
		//拼接内容
		String mailContent="<p><span>欢迎使用川大资料网，请在30分钟内访问下面的链接以完成邮箱验证</span></p>"
						+"<p><a href=\"" + url + "\">" + url + "</a></p>";
		//发送邮件
		if(MailUtil.sendMail("邮箱验证", mailContent, mailAddress)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 函数用于补全注册时间、IP，并向数据库插入注册信息
	 * @param user User类
	 * @param request 传入的request
	 * @return 数据库受影响的行
	 */
	private int insertRegisterInfo(User user) {
		//获取日期
		Timestamp date = DateUtil.getCurrentTimestamp();

		//插入数据库
		return userMapper.insertRegisterInfo(user,date);
		
	}
	
	/**
	 * 函数用于判断数据库中用户名是否已存在
	 * @param user_name 用户名
	 * @return 不存在返回true
	 */
	public boolean userNameExistedCheck(String user_name) {
		if(user_name == null) {
			return true;
		}
		String user_name_found = null;
		user_name_found = userMapper.findUserName(user_name);
		if(user_name_found == null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 函数用于判断数据库中邮箱是否已存在
	 * @param user_mail 用户邮箱
	 * @return 不存在返回true
	 */
	public boolean userMailExistedCheck(String user_mail) {
		if(user_mail == null) {
			return true;
		}
		String user_mail_found = null;
		user_mail_found = userMapper.findUserMail(user_mail);
		if(user_mail_found == null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean registerUser(HttpServletRequest request) {
		User user = new User();
		boolean result = addRegisterInfo(user, request);
		if(result == false) {
			return false;
		}
		String serializedString = SerializationUtil.objectSerialiable(user);
		Jedis jedis = jedisPool.getResource();
		jedis.lpush(MailSendTask.MAIL_SEND_QUEUE, serializedString);
		jedis.close();
		
		return true;
	}
}
