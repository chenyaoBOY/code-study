package org.study.smartframe.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author chenyao
 * @date 2021/1/15 14:08
 * @description
 */
public class PropsUtil {

    public static Properties loadFile(String fileName){
        Properties properties=null;
        InputStream is =null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null){
                throw new FileNotFoundException(fileName+" cannot find");
            }
            properties= new Properties();
            properties.load(is);
        }catch (IOException e){

        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public String getString(Properties properties,String key){
        if(properties==null) return null;
        if(properties.containsKey(key)){
            return properties.getProperty(key);
        }
        return null;
    }
}
