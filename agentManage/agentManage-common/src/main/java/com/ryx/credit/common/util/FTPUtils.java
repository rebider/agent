package com.ryx.credit.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FTPUtils implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final static Log logger = LogFactory.getLog(FTPUtils.class);
    
    // FTP 客户端代理
    private static FTPClient ftpClient = null;
    
    
    
    // 配置信息
    private FTPConfig config;
    
    static class FTPUtilsHolder {
        private static final FTPUtils FTP_UTILS = new FTPUtils();
    }
    
    public static FTPUtils getInstance() {
        return FTPUtilsHolder.FTP_UTILS;
    }
    
    public static FTPUtils getInstance(FTPConfig config) {
        FTPUtils ftp = FTPUtilsHolder.FTP_UTILS;
        ftp.config = config;
        ftp.initFtpClient();
        return ftp;
    }
    
    public void initFtp(FTPConfig config) {
        this.config = config;
        connectServer();
    }
    
    public boolean uploadFile(InputStream input, String path, String fileName) {
        boolean flag = true;
        try {
            connectServer();
            
            ftpClient.enterLocalPassiveMode();
            
            ftpClient.setFileTransferMode(10);
            
            ftpClient.changeWorkingDirectory(path);
            
            flag = ftpClient.storeFile(fileName, input);
            if (flag)
                System.out.println("上传文件成功！");
            else {
                System.out.println("上传文件失败！");
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    public boolean deleteFile(String filename) throws IOException {
        connectServer();
        return ftpClient.deleteFile(filename);
    }
    
    public boolean delete(String pathname) throws IOException {
        try {
            connectServer();
            File file = new File(pathname);
            if (file.isDirectory())
                return ftpClient.removeDirectory(pathname);
            else {
                return deleteFile(pathname);
            }
        } finally {
            closeConnect();
        }
    }
    
    public boolean deleteFiles(String pathname) throws IOException {
        try {
            connectServer();
            File file = new File(pathname);
            File[] file2;
            if (file.isDirectory()) {
                file2 = file.listFiles();
                for (File file3 : file2) {
                    deleteFile(pathname + file3.getName());
                }
            }
            return true;
        } finally {
            closeConnect();
        }
    }
    
    protected String getEndDate(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }
    
    public void deleteEmptyDirectory(String pathname) throws IOException {
        connectServer();
        ftpClient.removeDirectory(pathname);
    }
    
    public void closeConnect() {
        try {
            if (ftpClient != null && ftpClient.isRemoteVerificationEnabled()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void connectServer() {
        if (ftpClient == null || !ftpClient.isRemoteVerificationEnabled()) {
            initFtpClient();
        }
    }
    
    /**
     * 初始化FTP客户端
     * 
     * @return
     */
    private void initFtpClient() {
        try {            // 创建FTP客户端对象
            // 设置FTP字符集
            //ftpClient.setControlEncoding(config.getEncode());
            // //设置FTP端口
            //ftpClient.setDefaultPort(config.getPort());
            // 设置FTP 客户端配置
            //ftpClient.configure(getFtpConfig());
        	ftpClient = new FTPClient();
            // 设置IP
            ftpClient.connect(config.getIp(), config.getPort());
            // 客户端登录
            boolean flag = ftpClient.login(config.getUserName(), config.getPassword());
            System.out.println("ftp客户端登录"+flag);
            // 如果登录成功
            if (flag) {
                // 读取FTP响应
                int reply = ftpClient.getReplyCode();
                // 设置超时时间
                ftpClient.setDataTimeout(config.getTimeout());
                // 设置文件类型
                ftpClient.setFileType(config.getFileType());
                // 确定是否连接成功,如果没有连接就销毁
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    throw new RuntimeException("FTP server refused connection.");
                }
            } else {
                // 登录失败抛出登录失败异常
                throw new RuntimeException("FTP login error.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Initialize the FTP client failure.", e);
        }
    }
    
    /**
     * 非统一工具类:银行对账专用---不可修改
     * @param remoteDir 下载地址
     * @param downloadPath 本地下载地址
     */
    public List<String> downloadFiles(String remoteDir, String downloadPath,String datePath){
        connectServer();
        File filedown = new File(downloadPath);
        if (!filedown.exists()) {
            filedown.mkdirs();
        }
        
        try {
            ftpClient.changeWorkingDirectory(remoteDir);
        	ftpClient.enterLocalPassiveMode();   
        	ftpClient.configure(new FTPClientConfig("com.zznode.tnms.ra.c11n.nj.resource.ftp.UnixFTPEntryParser")); 
    		//得到的文件清单转成集成并升序排序
        	FTPFile[] dateFtpFiles = ftpClient.listFiles(remoteDir);
			if(dateFtpFiles==null || dateFtpFiles.length==0){
				return null;
			}
			List<FTPFile>dateDirList = Arrays.asList(dateFtpFiles);
			Comparator<FTPFile> dateNameComparator = new Comparator<FTPFile>() {
				public int compare(FTPFile o1, FTPFile o2) {
					return o1.getName().compareTo(o2.getName());
				}
			};
			File propFile;
			Collections.sort(dateDirList,dateNameComparator);
			 try {
				//获取最后下载日期
				File datatime = new File(datePath);
			    propFile = new File(datatime, "datatime.properties");
				String startTime = PropertiesFileUtil.getPropertyValue(propFile,"bank.data.time");
				logger.info("最後下載日期=========================="+startTime);
				Date date = new Date();
		        String endDate = getEndDate(date);
	            if (startTime.equals(endDate)) {
	            	logger.info("startDate == endDate 退出");
	                return null;
	            }
	            List<String> listName = new ArrayList<String>();
			 	for (int i = 0; i < dateDirList.size(); i++) {
			 		if(startTime.compareTo(dateDirList.get(i).getName().substring(8,16))==0){
			 			if(dateDirList.get(i).isFile()){
			 				logger.info("下载数据文件名========================"+dateFtpFiles[i].getName());
			 				logger.info("下在到==============================="+downloadPath);
			 				logger.info("FTP下载路径=========================="+remoteDir);
			 				logger.info("文件名称============================="+dateFtpFiles[i].getName());
			 				downloadFile(dateFtpFiles[i], downloadPath, remoteDir); 
			 				listName.add(dateFtpFiles[i].getName());
			 			}
			 		}
				}
			 	//更新最后修改时间
			 	if(listName.size()>0){
			 		PropertiesFileUtil.writePropertie(propFile, "bank.data.time", endDate);
			 		return listName;
			 	}else{
			 		return null;
			 	}
	        } catch (Exception e) {
	            throw new RuntimeException("Download files from the FTP server error.", e);
	        } finally {
	            closeConnect();
	        }
		} catch (Exception e) {
			 throw new RuntimeException("Download files from the FTP server error.", e);
		}
    }

    /** 
     * 
     * 下载FTP文件 
     * 当你需要下载FTP文件的时候，调用此方法 
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
     * @param ftpFile 
     * @param relativeLocalPath 
     * @param relativeRemotePath 
     */ 
    private void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) {
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) { 
                OutputStream outputStream = null; 
                try { 
                    File locaFile= new File(relativeLocalPath+ ftpFile.getName()); 
                    //判断文件是否存在，存在则返回 
                    if(locaFile.exists()){ 
                        return; 
                    }else{ 
                        outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName()); 
                        ftpClient.retrieveFile(ftpFile.getName(), outputStream); 
                        outputStream.flush(); 
                        outputStream.close(); 
                    } 
                } catch (Exception e) { 
                   logger.error(e);
                } finally { 
                    try { 
                        if (outputStream != null){ 
                            outputStream.close(); 
                        }
                    } catch (IOException e) { 
                    logger.error("输出文件流异常"); 
                    } 
                } 
            } 
        } else { 
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName(); 
            String newRemote = new String(relativeRemotePath+ ftpFile.getName().toString()); 
            File fl = new File(newlocalRelatePath); 
            if (!fl.exists()) { 
                fl.mkdirs(); 
            } 
            try { 
                newlocalRelatePath = newlocalRelatePath + '/'; 
                newRemote = newRemote + "/"; 
                String currentWorkDir = ftpFile.getName().toString(); 
                boolean changedir = ftpClient.changeWorkingDirectory(currentWorkDir); 
                if (changedir) { 
                    FTPFile[] files = ftpClient.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                        downloadFile(files[i], newlocalRelatePath, newRemote); 
                    } 
                } 
                if (changedir){
                	ftpClient.changeToParentDirectory(); 
                } 
            } catch (Exception e) { 
               logger.error(e);
            } 
        } 
	}
    
    public static void main(String[] args) throws Exception {
//    	File propFile2 = new File("D:\\RYX\\xsystem\\src\\main\\resources");
//    	File propFile = new File(propFile2, "datatime.properties");
//    	String startDate = PropertiesFileUtil.getPropertyValue(propFile, "bank.data.time");
//    	PropertiesFileUtil.writePropertie(propFile, "bank.data.time", new SimpleDateFormat("yyyyMMdd").format(new Date()));
    	String wenbendate = "20170417";//文件名
    	String startDate = "20170417";//当前时间
    	int compareTo = startDate.compareTo(wenbendate);
    	System.out.println(compareTo);
//    	PropertyUtil.setConfig("data.properties");
//    	String propery = PropertyUtil.getPropery("datePath");
//    	System.out.println(propery);
	}
}