package com.scushare.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * �ʼ�������
 * http://blog.csdn.net/u012661010/article/details/71946312
 */
public class MailUtil {
	public static boolean sendMail(String subject, String mailContent,String receiveAddress) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");//ָ���ʼ��ķ��ͷ�������ַ
        props.put("mail.smtp.auth", "true");//�������Ƿ�Ҫ��֤�û��������Ϣ

        Session session = Session.getInstance(props);//�õ�Session
        session.setDebug(true);//��������debugģʽ�������ڿ���̨���SMTPЭ��Ӧ��Ĺ���


        //����һ��MimeMessage��ʽ���ʼ�
        MimeMessage message = new MimeMessage(session);

        try {
	        //���÷�����
	        Address fromAddress = new InternetAddress("scushare@163.com");//�ʼ���ַ
	        message.setFrom(fromAddress);//���÷��͵��ʼ���ַ
	        //���ý�����
	        Address toAddress = new InternetAddress(receiveAddress);//Ҫ�����ʼ�������
	        message.setRecipient(RecipientType.TO, toAddress);//���ý����ߵĵ�ַ
	
	        //�����ʼ�������
	        message.setSubject(subject);
	        //�����ʼ�������
	        message.setContent(mailContent, "text/html; charset=utf-8");
	        //message.setText(mailContent);
	        //�����ʼ�
	        message.saveChanges();
	
	
	        //�õ������ʼ��ķ�����(�����õ���SMTP������)
	        Transport transport = session.getTransport("smtp");
	
	        //�����ߵ��˺����ӵ�SMTP��������  @163.com���Բ�д
	        transport.connect("smtp.163.com","scushare@163.com","VonnyedBedTor2IX"); 
	        //������Ϣ
	        transport.sendMessage(message, message.getAllRecipients());
	        //�رշ�����ͨ��
	        transport.close();
        }catch(Exception exception) {
        	exception.printStackTrace();
        	return false;
        }
        return true;
	}
}
