package com.ecarxclub.app.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class NotNull {

    public static boolean isNotNull(Integer i) {
        return null != i && 0 != i;
    }

    public static boolean isNotNull(Double d) {
        return null != d && 0 != d;
    }

    public static boolean isNotNull(Object object) {
        return null != object && !"".equals(object);
    }

    public static boolean isNotNull(List<?> t) {
        return null != t && t.size() > 0;
    }

    public static boolean isNotNull(Map<?, ?> t) {
        return null != t && t.size() > 0;
    }

    /**
     * 判断多个字符串是否有空值
     *
     * @param strings
     * @return
     * @author julyzeng
     * @date 2014-12-3 上午11:25:39
     */
    public static boolean isNotNull(String... strings) {
        if (strings == null) {
            return false;
        }
        for (int i = 0; i < strings.length; i++) {
            if (null == strings[i] || "".equals(strings[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(Object[] objects) {
        return null != objects && objects.length > 0;
    }

    public static boolean isNotNull(JSONArray jsonArray) {
        return null != jsonArray && jsonArray.length() > 0;
    }

    public static boolean isNotNull(JSONObject jsonObject) {
        return null != jsonObject && !"".equals(jsonObject);
    }

    public static boolean isNotNullAndNaN(Object object) {
        return isNotNull(object) && !object.toString().equals("NaN");
    }

}
