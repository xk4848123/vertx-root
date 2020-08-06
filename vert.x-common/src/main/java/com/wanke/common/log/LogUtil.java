package com.wanke.common.log;


/**
 * @Author: chendi
 * @Description: 公共打印日志类
 * @Date: 2020/7/22 15:40
 * @Version: 1.0
 */
public class LogUtil {

    public static void error(String errorContent){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        System.err.println(stackTraceElements[2].getClassName() + "----line:" + stackTraceElements[2].getLineNumber() + " error is " + errorContent);
    }

    public static void info(String infoContent){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        System.out.println(stackTraceElements[2].getClassName() + "----line:" + stackTraceElements[2].getLineNumber() + " info is " + infoContent);
    }

    public static void infoDirect(String infoContent){
        System.out.println(infoContent);
    }

    public static void errorDirect(String errorContent){
        System.err.println(errorContent);
    }


}
