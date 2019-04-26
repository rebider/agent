package com.ryx.credit.common.util;

import com.ryx.credit.common.exception.MessageException;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * 接口服务
 * 
 * @author wangqi
 * 
 */
public class MailUtil {
	
	private static Logger logger = Logger.getLogger(MailUtil.class);
	private static Properties emailProps; //邮件会话对象   
	private static String emailHost; //邮件服务器地址
	private static String emailUsername; //邮件登录用户名
	private static String emailPassword; //邮件登录密码
//	private static String jy_callUrl;
 
	static{
		try {
			Resource emailResource = new ClassPathResource("/email.properties");
			emailProps = PropertiesLoaderUtils.loadProperties(emailResource);
			emailHost = emailProps.getProperty("mail.smtp.host");
			emailUsername = emailProps.getProperty("username");
			emailPassword = emailProps.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送邮件
	 * @param mimeMsg
	 * @return
	 */
	public static ResultVO sendEmailMsg(MimeMessage mimeMsg){
		ResultVO result = new ResultVO();
		try{   
			logger.info("======开始发送邮件！");
			logger.info("======邮件主题："+mimeMsg.getSubject());
			logger.info("======smtp："+emailHost);
            Session mailSession = Session.getInstance(emailProps,null);   
            Transport transport = mailSession.getTransport("smtp");   
            transport.connect(emailHost,emailUsername,emailPassword);  
            Address[] to = mimeMsg.getRecipients(Message.RecipientType.TO);
            logger.info("======收件人列表："+InternetAddress.toString(to));
            transport.sendMessage(mimeMsg,to); 
            //暂时不用抄送
//            Address[] cc = mimeMsg.getRecipients(Message.RecipientType.CC);
//            if(null != cc && cc.length>0){
//            	transport.sendMessage(mimeMsg,cc);   
//            }
            //transport.send(mimeMsg);   
            logger.info("=================发送邮件成功！");
            transport.close();   
            result.setResCode("1");
            result.setResInfo("邮件发送成功！");
        } catch(Exception e) { 
        	logger.error("===================发送邮件失败！", e);
            e.printStackTrace();
            result.setResCode("0");
            result.setResInfo("邮件发送失败！");
        }   
		return result;   
	}
	
	public static String printStackTrace(Exception e){
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer,true));
		return writer.toString();
	}

}
