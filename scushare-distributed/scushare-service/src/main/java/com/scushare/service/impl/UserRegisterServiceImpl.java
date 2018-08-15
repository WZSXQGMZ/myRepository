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
	 * �˺������ڽ�form�ύ��ע����Ϣ���д���󱣴浽���ݿ�
	 * @param request Я��ע����Ϣ��request
	 * @return ����ע����
	 */
	public UserRegisterResult register(HttpServletRequest request) {
		User user = new User();
		if(addRegisterInfo(user, request) == false){
			return UserRegisterResult.USER_INFO_ERROR;
		}
		//��������
		String encryptedPassword = PwdUtil.encryptPwd(user.getUserPassword());
		user.setUserPassword(encryptedPassword);
		//����ע����Ϣ�����ݿ⣬������DB_INSERT_ERROR
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
		
		//��������REGISTER_SUCCESS
		return UserRegisterResult.REGISTER_SUCCESS;
	}
	
	/**
	 * ����������֤����ȷ�������е�����
	 * @param request Я�����ݲ�����request
	 */
	public UserRegisterResult mailCheck(HttpServletRequest request) {
		String arg = request.getParameter("arg");
		if(arg == null) {
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		String plaintext = null;
		//��������
		try {
			plaintext = SymCrypUtil.decrypt(arg, SymCrypUtil.getKey());
		} catch (Exception e) {
			e.printStackTrace();
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		try {
			String[] result = plaintext.split(",");
			//������Ӹ�ʽ����ȷ
			if(result.length != 3) {
				return UserRegisterResult.MAIL_CHECK_ERROR;
			}
			//�ж��Ƿ����
			int time = DateUtil.minutesBetweenDate(result[2], DateUtil.getDate());
			if(time > 30) {
				return UserRegisterResult.MAIL_OVERDUE_ERROR;
			}
			//�ж������Ƿ���ȷ
			String user_checked = userMapper.getUserByNameAndMail(result[0], result[1]);
			if(user_checked == null) {
				return UserRegisterResult.MAIL_CHECK_ERROR;
			}
			//�����û�Ȩ�ޣ��ݲ��ж�
			userMapper.updateUserLimitByName(user_checked, "normal");
		}catch(Exception exception) {
			exception.printStackTrace();
			return UserRegisterResult.MAIL_CHECK_ERROR;
		}
		return UserRegisterResult.MAIL_CHECK_SUCCESS;
	}
	
	/**
	 * �˺������ڽ�form�ύ��ע����Ϣ���浽User����
	 * @param user Ҫ���浽��User��
	 * @param request Я��ע����Ϣ��request
	 * @return ����Ƿ�ɹ�
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
			infoString = "��";
		}
		if(infoString.equals("��")) {
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
	 * �������ڷ�����֤�ʼ�
	 * @param mailAddress ����֤����
	 * @param user_name �û���
	 * @return �ʼ������Ƿ�ɹ�
	 */
	public boolean sendConfirmMail(String mailAddress, String user_name) {
		String plaintext = user_name + "," + mailAddress + "," + DateUtil.getDate();
		String ciphertext = null;
		try {
			//�����û���������
			ciphertext = SymCrypUtil.encrypt(plaintext, SymCrypUtil.getKey());
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
		String url = "http://localhost/scushare/checkMail?arg=" + ciphertext;
		//ƴ������
		String mailContent="<p><span>��ӭʹ�ô���������������30�����ڷ�����������������������֤</span></p>"
						+"<p><a href=\"" + url + "\">" + url + "</a></p>";
		//�����ʼ�
		if(MailUtil.sendMail("������֤", mailContent, mailAddress)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * �������ڲ�ȫע��ʱ�䡢IP���������ݿ����ע����Ϣ
	 * @param user User��
	 * @param request �����request
	 * @return ���ݿ���Ӱ�����
	 */
	private int insertRegisterInfo(User user) {
		//��ȡ����
		Timestamp date = DateUtil.getCurrentTimestamp();

		//�������ݿ�
		return userMapper.insertRegisterInfo(user,date);
		
	}
	
	/**
	 * ���������ж����ݿ����û����Ƿ��Ѵ���
	 * @param user_name �û���
	 * @return �����ڷ���true
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
	 * ���������ж����ݿ��������Ƿ��Ѵ���
	 * @param user_mail �û�����
	 * @return �����ڷ���true
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
