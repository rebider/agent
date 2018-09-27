package com.ryx.credit.util;

import com.ryx.credit.common.util.AppConfig;

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
