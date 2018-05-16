package com.ryx.credit.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 配置文件
 *
 * @author wangqi
 * @version 1.0
 * @date 2015-8-5 下午14:18:17
 * @since 1.0
 */
public class AppConfig extends Thread {
    private static final Log logger = LogFactory.getLog(AppConfig.class.getName());

    private static Properties appConfig = null;
    private static AppConfig instance = new AppConfig();
//	private static DBCon dbcon = null;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        appConfig = new Properties();
        loadConfig();
		start();  //TODO 上线时需取消注释，启动线程
    }

    public void loadConfig() {
        logger.debug("Reading config file.");
        synchronized (appConfig) {
            InputStream conf = null;
            try {
                conf = getClass().getResourceAsStream("/config.properties");
                appConfig.load(conf);
                PropertyConfigurator.configure(appConfig);
            } catch (Exception ex2) {
                logger.fatal("Connot load configuration file. [server.properties], see the following errors...");
                logger.fatal(ex2);
            } finally {
                try {
                    conf.close();
                } catch (Exception ex1) {
                    logger.fatal(ex1);
                }
            }
        }
        logger.debug("Reading config file done.");
    }

    public void run() {
        while (!appConfig.getProperty("ServerStop").equals("0")) {
            try {
                sleep(300000);//隔5分钟重读配置文件
            } catch (Exception ex1) {
                logger.fatal(ex1);
            }
            loadConfig();
        }
        logger.debug("AppConfig thread is exiting...");
    }


    public static String getProperty(String propName) {
        synchronized (appConfig) {
            if (appConfig == null)
                return null;

            return appConfig.getProperty(propName);
        }
    }

    public static String getProperty(String propName, String defaultValue) {
        String value = getProperty(propName);
        if(value == null) {
            value = defaultValue;
        }
        
        return value;
    }

    public static boolean getBoolean(String property) {
        try {
            return Boolean.valueOf(getProperty(property)).booleanValue();
        } catch (Exception e) {
            logger.fatal(e);
            return false;
        }
    }

    public static float getFloat(String property) {
        try {
            return Float.valueOf(getProperty(property)).floatValue();
        } catch (Exception e) {
            logger.fatal(e);
            return 0f;
        }
    }

    public static int getInt(String property) {
        try {
            return Integer.valueOf(getProperty(property)).intValue();
        } catch (Exception e) {
            logger.fatal(e);
            return 0;
        }
    }

    /**
     * 发送邮件
     * @return
     */
    public static  ResultVO sendEmail(final String receiveemailaddr,final String msg,final String title) {
        try {
            ThreadPool.putThreadPool(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }

                    try {
                        String email_platform = AppConfig.getProperty("email_platform");
                        Resource emailResource = new ClassPathResource("/email.properties");
                        Properties emailProps;
                        emailProps = PropertiesLoaderUtils.loadProperties(emailResource);
                        String auth = emailProps.getProperty("mail.smtp.auth");
                        String emailUsername = emailProps.getProperty("username");
                        String emailPassword = emailProps.getProperty("password");
                        // 判断是否需要身份认证
                        MyAuthenticator authenticator = null;
                        if(StringUtils.isNotBlank(auth)){
                            authenticator = new MyAuthenticator(emailUsername, emailPassword);
                        }
                        Session mailSession = Session.getDefaultInstance(emailProps,authenticator);
                        MimeMessage mimeMsg = new MimeMessage(mailSession);

                        mimeMsg.setSubject(title+email_platform);

                        mimeMsg.setFrom(emailProps.getProperty("from"));//设置发送邮箱

                        Multipart mp = new MimeMultipart();
                        BodyPart bp = new MimeBodyPart();
                        bp.setContent(msg,"text/html;charset=UTF-8");
                        mp.addBodyPart(bp);
                        mimeMsg.setContent(mp);
                        mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveemailaddr));
                        MailUtil.sendEmailMsg(mimeMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            logger.error("contentsPool Asynchronous call failed", e);
        }
        return null;


    }

    /**
     * 发送邮件(加附件)
     * @param receiveemailaddr 接受邮件地址
     * @param msg	消息
     * @param title 标题
     * @param newFile	文件
     * @param fileName	文件名称
     * @return
     */
    public static  ResultVO sendEmailAndAtt(final String receiveemailaddr,final String msg,final String title,
    		final File newFile,final String fileName) {
        try {
            ThreadPool.putThreadPool(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    try {
                        String email_platform = AppConfig.getProperty("email_platform");
                        Resource emailResource = new ClassPathResource("/email.properties");
                        Properties emailProps;
                        emailProps = PropertiesLoaderUtils.loadProperties(emailResource);
                        String auth = emailProps.getProperty("mail.smtp.auth");
                        String emailUsername = emailProps.getProperty("username");
                        String emailPassword = emailProps.getProperty("password");
                        String from = emailProps.getProperty("from");
                        // 判断是否需要身份认证
                        MyAuthenticator authenticator = null;
                        if(StringUtils.isNotBlank(auth)){
                            authenticator = new MyAuthenticator(emailUsername, emailPassword);
                        }
                        Session mailSession = Session.getDefaultInstance(emailProps,authenticator);
                        MimeMessage mimeMsg = new MimeMessage(mailSession);

                        mimeMsg.setSubject(title+email_platform);

                        mimeMsg.setFrom(from);//设置发送邮箱

                        Multipart mp = new MimeMultipart();
                        BodyPart bp = new MimeBodyPart();
                        bp.setContent(msg,"text/html;charset=UTF-8");
                        mp.addBodyPart(bp);
                        mimeMsg.setContent(mp);
                        mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveemailaddr));
                        //*****************************上传附件*******************************
                        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMsg,true);  
                        mimeMessageHelper.setTo(receiveemailaddr);  
                        mimeMessageHelper.setFrom(from);  
                        mimeMessageHelper.setSubject(title);  
                        mimeMessageHelper.setText(msg,true);   
                        mimeMessageHelper.addAttachment(fileName, newFile);  
                      //*****************************---------*******************************
                        MailUtil.sendEmailMsg(mimeMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            logger.error("contentsPool Asynchronous call failed", e);
        }
        return null;
    }

    
    public static void sendEmails(String msg,String title){
        String emails = AppConfig.getProperty("emails");
        for(String email:emails.split("\\,")){
            AppConfig.sendEmail(email, msg, title);
        }
    }

    public static void sendEmailsAtt(String msg,String title,File newFile,String fileName){
        String emails = PropUtils.getProp("dailyEmail");
        for(String email:emails.split("\\,")){
            AppConfig.sendEmailAndAtt(email, msg, title, newFile,fileName);
        }
    }
}
