package com.ryx.credit.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 文件工具类 <br/>
 * 
 * @author shawnley
 */
public class FileUtil {
    
    /**
     * 默认方法
     */
    private FileUtil() {
    }
    
    /**
     * 将源文件移动成指定目标文件
     * 
     * @param srcFile
     * @param destDir
     * @return boolean
     * @throws IOException IOException
     */
    public static boolean removeFile(File srcFile, File destDir) throws IOException {
        /*
         * String osname = System.getProperty("os.name").toUpperCase(); if
         * (osname.indexOf("LINUX") >= 0) { return FileTool.moveFile(source,
         * dest); } else { return source.renameTo(dest); }
         */
        // return source.renameTo(dest);
        // FileUtils.copyFileToDirectory(source, dest);
        FileUtils.moveFileToDirectory(srcFile, destDir, true);
        return true;
    }
    
    public static boolean moveFilesToDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir.isDirectory()) {
            File[] files = srcDir.listFiles();
            for (File file : files) {
                FileUtils.moveFileToDirectory(file, destDir, true);
            }
        } else {
            FileUtils.moveFileToDirectory(srcDir, destDir, true);
        }
        return true;
    }
    
    /**
     * @return file
     */
    public static File getDataExportRoot() {
        File file = new File(PropertiesFileUtil.getPropertyValue("config/di-config.properties", "pqrt_file_out"));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    
    /**
     * @param b
     * @return str
     */
    public final static String getFileHexString(byte[] b) {
        final int zero = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= zero) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    /**
     * Java文件操作 获取文件扩展名 <br/>
     * 
     * @param filename
     * @return str
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    
    /**
     * 将文件在源目录重命名 <br/>
     * 
     * @param srcFile
     * @param destFileName
     * @return
     * @throws IOException
     */
    public static File renameFile(File srcFile, String destFileName) throws IOException {
        File destFile = new File(srcFile.getParent() + File.separator + destFileName);
        FileUtils.moveFile(srcFile, destFile);
        return destFile;
    }
    
    public static File renameTmpFile(String fileName) throws IOException {
        File file = new File(getDataExportRoot(), fileName);
        return renameTmpFile(file);
    }
    
    /**
     * 将文件在源目录重命名 <br/>
     * 
     * @param srcFile
     * @param destFileName
     * @return
     * @throws IOException
     */
    public static File renameTmpFile(File srcFile) throws IOException {
        if (srcFile == null || !srcFile.isFile() || !srcFile.exists()) {
            return srcFile;
        }
        String destFileName = srcFile.getName().toUpperCase().replace(".TMP", ".XML");
        File destFile = new File(srcFile.getParent() + File.separator + destFileName);
        FileUtils.moveFile(srcFile, destFile);
        return destFile;
    }
    
    public static List<String> renameTmpFile(List<String> fileNameList) throws IOException {
        if (fileNameList == null) {
            return fileNameList;
        }
        List<String> list = new ArrayList<String>();
        for (String srcFile : fileNameList) {
            File destFile = renameTmpFile(srcFile);
            list.add(destFile.getName());
        }
        return list;
    }
    
    /**
     * 获取当前class所在jar包的物理路径 <br/>
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getJarPath() throws UnsupportedEncodingException {
        String jarFilePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
    }
    
    public static File getJarFile() throws UnsupportedEncodingException {
        return new File(getJarPath());
    }
    
    /**
     * 按文件最后修改时间升序排序，获得前n个文件对象
     * 
     * @param rootDir 根目录
     * @param suffix 文件扩展名
     * @param num 读取的数量 -1:代表获取该目录下的全部文件
     * @return File[]
     */
    public static File[] getFileByLastModified(File rootDir, String suffix,
                                               int num) {
        // 获取指定
        File[] files = suffixFileFilter(rootDir, suffix);
        if (files == null || files.length == 0) {
            return null;
        }

        if (files.length < num || num == -1) {
            num = files.length;
        }

        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);

        File[] tmpFiles = new File[num];
        System.arraycopy(files, 0, tmpFiles, 0, num);
        return tmpFiles;
    }

    /**
     * @param rootDir
     * @param suffix
     * @return File[]
     */
    public static File[] suffixFileFilter(File rootDir, String suffix) {
        FilenameFilter fileFilter = new SuffixFileFilter(suffix);
        return rootDir.listFiles(fileFilter);
    }
    
    
    /**
     * 获取XML报文处理完后最终存储相对路径，/年/月/日地址命名规则
     * 
     * @return str
     */
    public static String getLastOutRelative() {
        // 获得当前日期
        Calendar cal = Calendar.getInstance();
        // 日月
        String month = cal.get(Calendar.MONTH) + 1 + "";
        String day = cal.get(Calendar.DATE) + "";
        // 创建文件夹，如果没有此文件夹，自动生成
        String address = cal.get(Calendar.YEAR)
                         + File.separator
                         + ((month.length() == 2)
                                                 ? month
                                                 : ("0" + month))
                         + File.separator
                         + ((day.length() == 2
                                              ? day
                                              : "0" + day))
                         + File.separator; // 两位，如05或25
        return address;
    }
}
