package junit;


import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MBGexecute {

    /** 数据库驱动 */
    private static final String GENERATOR_DRIVER_CLASS="generator.driverClass";
    /** 数据库连接 */
    private static final String GENERATOR_CONNECTION_URL="generator.connectionURL";
    /** 数据库用户 */
    private static final String GENERATOR_USER_ID="generator.username";
    /** 数据库密码 */
    private static final String GENERATOR_PASSWORD="generator.password";
    /** 分页插件类 */
    private static final String GENERATOR_PAGEPLUGIN="generator.pagePlugin";
    /** 生成配置文件名称 */
    private static final String CONFIG_NAME= "generatorConfig.xml";
    /** 生成配置文件路径 */
    private static final String CONFIG_URL= MBGexecute.class.getResource("/").getPath()+"/";
    /** 数据库配置文件名称 */
    private static final String PROPERTIES_NAME="jdbc.properties";
    /** 生成完毕! */
    private static final String SUCCESS_VALUE = "生成完毕!";
    /** 生成失败! */
    private static final String FAILURE_VALUE = "生成失败!";
    /**
     * 生成MyBatis
     *
     * @throws Exception
     */
    @Test
    public void generate() throws Exception {
        //初始化系统属性
        this.propertyInit();
        String value=null;
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File(CONFIG_URL+CONFIG_NAME);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            value=SUCCESS_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            value=FAILURE_VALUE;
        }
        System.out.println(value);


    }

    /**
     * 初始化系统属性
     *
     * @throws Exception
     */
    private void propertyInit()throws Exception{
        System.setProperty(GENERATOR_DRIVER_CLASS, NgearUtil.getPropertiesByName(PROPERTIES_NAME, GENERATOR_DRIVER_CLASS));
        System.setProperty(GENERATOR_CONNECTION_URL, NgearUtil.getPropertiesByName(PROPERTIES_NAME, GENERATOR_CONNECTION_URL));
        System.setProperty(GENERATOR_USER_ID, NgearUtil.getPropertiesByName(PROPERTIES_NAME, GENERATOR_USER_ID));
        System.setProperty(GENERATOR_PASSWORD, NgearUtil.getPropertiesByName(PROPERTIES_NAME, GENERATOR_PASSWORD));
        System.setProperty(GENERATOR_PAGEPLUGIN, NgearUtil.getPropertiesByName(PROPERTIES_NAME, GENERATOR_PAGEPLUGIN));
    }
}
