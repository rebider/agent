package com.ryx.credit.common.util;

/**
 *
 * Created by RYX on 2018/9/11.
 */
public class EnvironmentUtil {

    public static String environment = AppConfig.getProperty("environment");

    public static Boolean isProduction(){
        return environment.equals("production");
    }
}
