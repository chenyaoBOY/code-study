package org.study.smartframe.util;

import org.study.smartframe.ConfigConstant;

import java.util.Properties;

/**
 * @author chenyao
 * @date 2021/1/18 15:45
 * @description
 */
public class ConfigUtil {

    private static final Properties PROPERTIES = PropsUtil.loadFile(ConfigConstant.CONFIG_FILE);




    public static String getAppBasePackage(){
        return PROPERTIES.getProperty(ConfigConstant.APP_BASE_PACKAGE,"org.study.smartframe");
    }
    public static String getAppAssertPath(){
        return PROPERTIES.getProperty(ConfigConstant.APP_ASSERT_PATH,"/WEB-INF/view/");
    }
    public static String getAppJspPath(){
        return PROPERTIES.getProperty(ConfigConstant.APP_JSP_PATH,"/asset/");
    }

}
