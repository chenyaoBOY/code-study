package org.study.smartframe.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author chenyao
 * @date 2021/1/18 16:00
 * @description
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * @param classFullName
     * @param isInitialized 是否初始化类 指是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String classFullName, boolean isInitialized) {
        try {
            return Class.forName(classFullName, isInitialized, getClassLoader());
        } catch (Exception e) {
            logger.error("load class is error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过classLoader.getResources获取相对路经下的资源  不需要绝对路径
     * 获取到Url以后 就可以通过url.getPath 找到绝对路径 从而创建文件
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassFromPackage(String packageName) {
        if (StringUtils.isBlank(packageName)) throw new RuntimeException("packageName can not empty!");
        Set<Class<?>> result = new HashSet<>();
        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url == null) continue;
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String packagePath = url.getPath().replace("%20", "");
                    addClass(result, packageName, packagePath);
                }
                if ("jar".equals(protocol)) {
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    if (urlConnection == null) continue;
                    JarFile jarFile = urlConnection.getJarFile();
                    if (jarFile == null) continue;
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String name = jarEntry.getName();
                        if (name.endsWith(".class")) {
                            String className = name.substring(0, name.lastIndexOf(".")).replace("/", ".");
                            addClass(result, className);
                        }
                    }
                }
            }
            return result;
        } catch (IOException e) {
            logger.error("get class from " + packageName + " had occur wrong!", e);
            throw new RuntimeException(e);
        }
    }

    private static void addClass(Set<Class<?>> result, String className) {
        result.add(loadClass(className, false));
    }

    /**
     * 查找 packagePath下的所有class文件
     *
     * @param result
     * @param packageName
     * @param packagePath
     */
    private static void addClass(Set<Class<?>> result, String packageName, String packagePath) {

        File[] files = new File(packagePath).listFiles(file -> file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                addClass(result, className);
            } else {
                String subPagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPagePath = packagePath + "/" + subPagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(result, subPackageName, subPagePath);
            }
        }

    }


    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
