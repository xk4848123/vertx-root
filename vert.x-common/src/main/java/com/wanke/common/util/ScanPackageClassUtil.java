package com.wanke.common.util;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author tianyu
 * 
 */
public class ScanPackageClassUtil {

    public static List<String> getClassNameFromPackage(String packageName) throws IOException {
    	packageName = packageName.replace(".", "/");
        Enumeration<URL> iterator = Thread.currentThread().getContextClassLoader().getResources(packageName);
        List<String>list = new ArrayList<String>();
        URL url = null;
        while(iterator.hasMoreElements()) {
            url = iterator.nextElement();
            if ("file".equals(url.getProtocol())) {
                File file = new File(url.getPath());
                ecursionFile(file,list,packageName);
            }else {
                ecursionJarFile(url,list,packageName);
            }

        }
        return list;

    }
    
    static void ecursionFile(File file,List<String> list,String packageName) {
        File[] fls = null;
            fls= file.listFiles();
            for(File fl :fls) {
            	if (fl.isFile()) {
            		 String path = fl.getPath();
            		 if (path.indexOf(packageName) != path.lastIndexOf(packageName)) {
						System.exit(-1);
					}
                    packageName = packageName.replace("/",File.separator);
                    String classPathName = path.substring(path.indexOf(packageName));
                    String classNameWithSuffix = classPathName.replace(File.separator,".");
                    String className = classNameWithSuffix.substring(0, classNameWithSuffix.lastIndexOf("."));
                    list.add(className);
				}else if (fl.isDirectory()){
					ecursionFile(fl,list,packageName);
				}
        }
    }


    static void ecursionJarFile(URL url,List<String> list,String packageName) throws IOException {
        System.out.println(url.getProtocol());
        System.out.println(url.getPath());
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarName = jarEntry.getName();
            if (jarEntry.isDirectory() || !jarName.endsWith(".class") || !jarName.startsWith(packageName)) {
                continue;
            }
            String className = jarName.replace(".class", "").replaceAll("/", ".");
            list.add(className);
        }
    }
}
