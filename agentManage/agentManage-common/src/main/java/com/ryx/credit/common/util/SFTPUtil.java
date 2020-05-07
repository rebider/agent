package com.ryx.credit.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.ryx.credit.common.exception.MessageException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	
	private  Logger logger = Logger.getLogger(this.getClass().getName());

	ChannelSftp sftp = null;
	
	boolean flag = false;

	public  SFTPUtil(String host,int port, String username,String password){
		this.connect(host, port, username, password);
	} 


	private void connect(String host, int port, String username,String password) {
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
			this.flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("连接远程服务出错\n",e);
		}
	}
	/**
	 * 判断是否可用
	 * @return
	 */
	public  boolean isAvailable(){
		return flag;
	}

	/**
	 * 判断文件是否存在
	 * @param filePathe
	 * @param fileName
	 * @return
	 */
	public boolean isExistFile(String filePathe,String fileName){
		
		try {
			Vector vector = sftp.ls(filePathe);
			Iterator<LsEntry> it = vector.iterator(); 
			while(it.hasNext()) { 
				String name = it.next().getFilename(); 
				if(name.startsWith(fileName)){
					return true;
				}
			} 
			return false;
		} catch (SftpException e) {
			logger.error("查找远程文件出错\n"+e);
			return false;
		}
		
	}
	
	
	/**
	 * 获取以某个字符串换开头的文件，传“”时列出所有
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public List<String> showFileList(String filePath,String fileName){
		List<String> list = new ArrayList<String>();
		try {
			Vector vector = sftp.ls(filePath);
			Iterator<LsEntry> it = vector.iterator(); 
			if(it == null){
				logger.info("远程服务目录为空");
				return list;
			}
			
			while(it.hasNext()) { 
				String name = it.next().getFilename(); 
				if("".equals(fileName)  || name.startsWith(fileName)){
					list.add(name);
				}
			} 
			
			return list;
		} catch (Exception e) {
			logger.error("验证远程文件失败"+e);
			return list;
		}
		
	}
	
	/**
	 * 判断文件是否存在
	 * @param filePathe
	 * @param fileName
	 * @return
	 */
	public String isExistFile1(String filePathe,String fileName){
		
		String name = "";
		try {
			
			System.out.println(filePathe);
			System.out.println(fileName);
			Vector vector = sftp.ls(filePathe);
			Iterator<LsEntry> it = vector.iterator(); 
			while(it.hasNext()) { 
				name = it.next().getFilename(); 
				System.out.println(name);
				if(name.startsWith(fileName)){
					return name;
				}
			} 
			return name;
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("查找远程文件出错\n"+e);
			return name;
		}
		
		
	}
	/***
	 * 下载文件到本地
	 * @param remotePath
	 * @param remoteName
	 * @param localPath
	 * @param localName
	 * @return
	 */
	public  boolean downloadFileName(String remotePath,String remoteName,String localPath,String localName){
		
		try {
			sftp.cd(remotePath);
			File file=new File(localPath);
			System.out.println(localPath+localName);
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream  fop = new FileOutputStream(file+localName);
			sftp.get(remoteName, fop);
			fop.flush();
			fop.close();
			return true;
		} catch (Exception e) {
			logger.error("下载远程文件出错\n"+e);
			return false;
		}
	}

	/***
	 * 上传文件到远程
	 * @param remotePath
	 * @param remoteName
	 * @param localPath
	 * @param localName
	 * @return
	 */
	public  boolean uploadFileName(String remotePath,String remoteName,String localPath,String localName){
		try {
			logger.info("上传文件:本地:"+localPath+localName+"|远程:"+ remotePath + remoteName);
			createRemoteDir(remotePath);
			sftp.put(localPath+ localName, remotePath+ remoteName, ChannelSftp.OVERWRITE);
			logger.info("sftp上传成功"+remotePath + remoteName);
			return true;
		} catch (Exception e) {
			logger.error("上传远程文件出错\n"+e);
			return false;
		}
	}

	public boolean createRemoteDir(String remotePath)  {
		//判断目的路径是否存在，不存在则创建
		String[] arr = remotePath.split("/");
		int size = arr.length;
		String tempPath = "";
		for(int i = 0; i < size; i++){
			if(i==0){
				tempPath =arr[i] + "/";
			}else{
				tempPath = tempPath + arr[i] + "/";
			}
			for(int j=0;j<3;j++){
				if(!dirExits(tempPath)){
					try {
						sftp.mkdir(tempPath);
					} catch (Exception e) {
						logger.info("远程路径创建失败:" + tempPath + e.getMessage());
					}
				}
				if(dirExits(tempPath)){
					break;
				}
				if(j==2){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断文件夹是否存在
	 * 适用于SFTP
	 * @param dir
	 * @return
	 */
	public boolean dirExits(String dir){
		try {
			Vector<?> vector = sftp.ls(dir);
			if(null == vector)
				return false;
			else
				return true;
		} catch (SftpException e) {
			return false;
		}
	}

	/***
	 * 断开连接
	 * @throws Exception
	 */
    public void disconnect() throws Exception {
    	if(sftp!=null){
			sftp.disconnect();
		}
    }



}
