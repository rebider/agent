package com.ryx.credit.profit.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.ryx.credit.common.util.AppConfig;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * liunx远程调用工具
 *
 * @author zhaodw
 * @create 2018/8/27
 * @since 1.0.0
 */
public class RemoteShellTool {
    private static final  String IP_ADDR =  AppConfig.getProperty("ssh_ipAddr");
    private  static final String CHARSET = Charset.defaultCharset().toString();
    private static final  String USER_NAME =  AppConfig.getProperty("ssh_userName");
    private static final  String PASSWORD =  AppConfig.getProperty("ssh_password");

    private RemoteShellTool() {
    }

    public static Connection getConnection() throws IOException {
        Connection conn = null;
        try {
            conn = new Connection(IP_ADDR);
            conn.connect(); // 连接
            conn.authenticateWithPassword(USER_NAME, PASSWORD); // 认证
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String exec(String cmds) {
        InputStream in = null;
        String result = "";
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Session session = conn.openSession(); // 打开一个会话
                session.execCommand(cmds);
                in = session.getStdout();
                result = processStdout(in, CHARSET);
                session.close();
                conn.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    public static String processStdout(InputStream in, String charset) {

        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String result = RemoteShellTool.exec("cd /home/tomcat/;mkdir clientMk");
        System.out.println(result);

    }
}
