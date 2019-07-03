package com.chan.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-02 22:29
 **/
public class ClassScanner {

    public static List<Class<?>> scanClasses(String packegeName) throws IOException, ClassNotFoundException {

        List<Class<?>> classList = new ArrayList<>();
        String path = packegeName.replace(".","/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()){

            URL resource = resources.nextElement();

            if (resource.getProtocol().contains("jar")){
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(path,jarFilePath));
            }else if (resource.getProtocol().contains("file")){

                File dir = new File(resource.getFile());
                for (File file:dir.listFiles()){
                    if (file.isDirectory()){
                        classList.addAll(scanClasses(packegeName + "." + file.getName()));
                    }else {
                        String className =packegeName +"." +file.getName().replace(".class", "");
                        classList.add(Class.forName(className));
                    }
                }

            }

        }



        return classList;

    }

    private static List<Class<?>> getClassesFromJar(String path, String jarFilePath) throws IOException, ClassNotFoundException {

        List<Class<?>> classList = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        while (jarEntrys.hasMoreElements()){
            JarEntry jarEntry = jarEntrys.nextElement();
            String name = jarEntry.getName();//com/mooc/zbs/test/Test.class
            if (name.startsWith(path)&&name.endsWith(".class")){
                String classFullName = name.replace("/", ".").substring(0, name.length() - 6);
                classList.add(Class.forName(classFullName));
            }
        }

        return classList;

    }

}
