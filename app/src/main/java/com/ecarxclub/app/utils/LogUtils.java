package com.ecarxclub.app.utils;

import android.util.Log;

/**
 * Created by wecent on 2017/10/10
 */

public class LogUtils {

    public static boolean isNeedDebug=true;

    public static int v(String tag, String msg) {
        if (isNeedDebug) {
            return Log.v(tag, msg);
        }
        return 0;
    }

    public static int e(String tag, String msg) {
        if (isNeedDebug) {
            return Log.e(tag, msg);
        }
        return 0;
    }

    public static int i(String tag, String msg) {
        if (isNeedDebug) {
            return Log.i(tag, msg);
        }
        return 0;
    }

    public static int w(String tag, String msg) {
        if (isNeedDebug) {
            return Log.w(tag, msg);
        }
        return 0;
    }

    public static int d(String tag, String msg) {
        if (isNeedDebug) {
            return Log.d(tag, msg);
        }
        return 0;
    }

    /**
     * 日志级别
     */
    private static final int LOG_LEVEL = Log.VERBOSE;

    /**
     * 异常栈位
     */
    private static final int EXCEPTION_STACK_INDEX = 2;

    /**
     * verbose级别的日志
     * 
     * @param msg
     *            打印内容
     * 
     * @see
     */
    public static void verbose(String msg) {
        if (Log.VERBOSE >= LOG_LEVEL) {
            LogUtils.v(getTag(), msg);
        }
    }

    /**
     * debug级别的日志
     * 
     * @param msg
     *            打印内容
     * 
     * @see
     */
    public static void debug(String msg) {
        if (Log.DEBUG >= LOG_LEVEL) {
            LogUtils.d(getTag(), msg);
        }
    }

    /**
     * info级别的日志
     * 
     * @param msg
     *            打印内容
     * 
     * @see
     */
    public static void info(String msg) {
        if (Log.INFO >= LOG_LEVEL) {
            LogUtils.i(getTag(), msg);
        }
    }

    /**
     * warn级别的日志
     * 
     * @param msg
     *            打印内容
     * 
     * @see
     */
    public static void warn(String msg) {
        if (Log.WARN >= LOG_LEVEL) {
            LogUtils.w(getTag(), msg);
        }
    }

    /**
     * error级别的日志
     * 
     * @param msg
     *            打印内容
     * 
     * @see
     */
    public static void error(String msg) {
        if (Log.ERROR >= LOG_LEVEL) {
            LogUtils.e(getTag(), msg);
        }
    }

    /**
     * 获取日志的标识格式：类名_方法名_行（需要权限：android.permission.GET_TASKS
     * 
     * @return tag
     * @see
     */
    private static String getTag() throws StackOverflowError {
        StackTraceElement element = new LogException().getStackTrace()[EXCEPTION_STACK_INDEX];

        String className = element.getClassName();

        int index = className.lastIndexOf(".");
        if (index > 0) {
            className = className.substring(index + 1);
        }

        return className + "_" + element.getMethodName() + "_" + element.getLineNumber();
    }

    /**
     * 取日志标签用的的异常类，只是用于取得日志标识
     * 
     */
    private static class LogException extends Exception {

        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
    }
}
