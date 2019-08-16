package com.ecarxclub.app.base.gson;

import android.text.TextUtils;
import android.util.Log;

import com.ecarxclub.app.base.BaseException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者： ch
 * 时间： 2019/2/15 0015-上午 11:01
 * 描述：
 * 来源：
 */

public class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    BaseResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
//        Log.i("cwp","json  "+jsonString);
        try {
            JSONObject object = new JSONObject(jsonString);
            boolean success=object.getBoolean("success");
            String msg = object.getString("msg");
//            Log.e("cwp","success  "+success);
            if (success==false) {//code返回200  成功  success返回为false
//                Log.e("cwp","数据解析  "+success);
                if (TextUtils.isEmpty(msg)) {
                    msg = object.getString("error");
                }
                //异常处理
//                throw new BaseException(msg, 1003);
                throw new BaseException(msg);
            }

            return adapter.fromJson(jsonString);
//            String data=object.getString("");
//            if(data!=null&&data.length()>0){
//                return adapter.fromJson(object.getString("data"));
//            }else {
//            return (T) msg;
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            //数据解析异常
            throw new BaseException(BaseException.PARSE_ERROR_MSG, BaseException.PARSE_ERROR);
        } finally {
            value.close();
        }
    }
}
