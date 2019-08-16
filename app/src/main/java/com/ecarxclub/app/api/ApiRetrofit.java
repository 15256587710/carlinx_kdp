package com.ecarxclub.app.api;

import android.util.Log;

import com.ecarxclub.app.base.gson.BaseConverterFactory;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiRetrofit {
    public String BASE_SERVER_URL= UrlOkHttpUtils.YXB_URL;    //https://www.tianqiapi.com/api
    private static ApiRetrofit apiRetrofit;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ApiServer apiServer;
    private String TAG="ApiRetrofit2";
    private String mToken;

    public ApiRetrofit(){
        client=new OkHttpClient.Builder()
//                .addInterceptor(new AppendHeaderParamIntercepter())
                .addInterceptor(new LogInterceptor())
//                .addInterceptor(new CommonInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        ToastUtils.i("lujinnnnnnnnnn__",""+UrlOkHttpUtils.YXB_SET_MYURL);
        if(UrlOkHttpUtils.YXB_SET_MYURL.length()>0){
            BASE_SERVER_URL=UrlOkHttpUtils.YXB_SET_MYURL;
        }
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        apiServer=retrofit.create(ApiServer.class);
    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    public ApiServer getApiService() {
        return apiServer;
    }
    private class CommonInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取原先的请求
            Request originalRequest = chain.request();
            //重新构建url
            HttpUrl.Builder builder = originalRequest.url().newBuilder();
//            如果是post请求的话就把参数重新拼接一下，get请求的话就可以直接加入公共参数了
            if(originalRequest.method().equals("POST")){
                FormBody body = (FormBody) originalRequest.body();
                for(int i = 0; i < body.size();i++){
                    Log.i("RequestFatory",body.name(i) + "---" + body.value(i));
                    builder.addQueryParameter(body.name(i),body.value(i));
                }
            }
            ToastUtils.e("","token ++ "+SharedPreferencesUtils.getParam(YxbApplication.getContext(),UrlOkHttpUtils.YXB_USER_TOKEN,"").toString());
            //这里是我的2个公共参数
            builder.addQueryParameter("token", SharedPreferencesUtils.getParam(YxbApplication.getContext(),UrlOkHttpUtils.YXB_USER_TOKEN,"").toString())
//                    .addQueryParameter("appid","appids00000000")
            ;

//            新的url
            HttpUrl httpUrl = builder.build();
            Log.e(TAG, "----------Request Start----------------");
            Log.e(TAG, "| Response:" + httpUrl);
            Request request = originalRequest.newBuilder()
                    .method(originalRequest.method(),originalRequest.body())
                    .url(httpUrl).build();

//            Request original = chain.request();
//            Request request = original.newBuilder()
//                    .header("token", SharedPreferencesUtils.getParam(YxbApplication.getContext(),UrlOkHttpUtils.YXB_USER_TOKEN,"").toString())
//                    .method(original.method(), original.body())
//                    .build();
//            Log.e(TAG, "----------Request Start----------------");
//            Log.e(TAG, "| Response:" + request.toString());

            return chain.proceed(request);
        }
    }


    //拦截器
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.e(TAG, "----------Request Start----------------");
            Log.e(TAG, "| " + request.toString() + request.headers().toString());
            Log.e(TAG, "| Response:" + content);
            Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };


    /**
     * 统一追加Header
     * Created by zkt on 2018-2-3.
     */

    public class AppendHeaderParamIntercepter implements Interceptor {

        // 1.获取以前的Builder
        // 2.为以前的Builder添加参数
        // 3.生成新的Builder

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Headers.Builder builder = request
                    .headers()
                    .newBuilder();
            //统一追加Header参数
            Headers newBuilder = builder
                    .add("Authorization", SharedPreferencesUtils.getParam(YxbApplication.getContext(),UrlOkHttpUtils.YXB_USER_TOKEN,"").toString())
                    .add("Channel", CommonUtils.getMetaData(YxbApplication.getContext(),"UMENG_CHANNEL"))
                    .build();
            Request newRequest = request.newBuilder()
                    .headers(newBuilder)
                    .build();
            HttpUrl.Builder mBuild = newRequest.url().newBuilder();
//            HttpUrl httpUrl = mBuild.build();

            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
//            StringBuilder logSB = new StringBuilder();
//            logSB.append("----------Request Start----------------"+"\n");
//            logSB.append(newRequest.toString()+"\n|");
//            logSB.append(method.equalsIgnoreCase("POST")?"post参数{"+ postBodyString +"}\n|":"\n");
//            logSB.append("httpCode=" + httpStatus + ";Response:" + content+"\n|");
//            logSB.append("----------End:" + duration + "毫秒----------header |"+newRequest.headers().toString());
//            ToastUtils.showLog(logSB.toString());

            Log.e(TAG, "----------Request Start----------------");
            Log.e(TAG, "| " + request.toString()+"\n| Response"+ chain.proceed(newRequest).body().string());
//            Log.e(TAG, "| Response:" + chain.proceed(newRequest).body().string());
            Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
            return chain.proceed(newRequest);
        }
    }


    public class LogInterceptor implements Interceptor {
        public String TAG = "LogInterceptor";
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            Request.Builder newRequestBuild;
            String method = oldRequest.method();
            String postBodyString="";//post参数
            mToken=SharedPreferencesUtils.getParam(YxbApplication.getContext(),UrlOkHttpUtils.YXB_USER_TOKEN,"").toString()+"";
            ToastUtils.e("method  ","____________________________________________-"+method);
            if("POST".equals(method)){
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("deviceOs", "cwp111");
//                formBodyBuilder.add("appVersion","huacwp222");
                newRequestBuild = oldRequest.newBuilder();

                if(!UrlOkHttpUtils.isUploadImg){//上传图片的时候  不显示post参数的显示
                    RequestBody formBody = formBodyBuilder.build();
                    postBodyString = bodyToString(oldRequest.body());
                    postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                    newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
                }
                //application/x-www-form-urlencoded;charset=UTF-8
            }else {
                // 添加新的参数
                HttpUrl.Builder commonParamsUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host());
//                        .addQueryParameter("deviceOs", "cwp")
//                        .addQueryParameter("appVersion", "huacwp");
                newRequestBuild = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(commonParamsUrlBuilder.build());
            }
            Request newRequest = newRequestBuild
//                    .addHeader("Accept", "application/json")
//                    .addHeader("Accept-Language", "zh")
//                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Authorization", mToken)
                    .addHeader("Channel", CommonUtils.getMetaData(YxbApplication.getContext(),"UMENG_CHANNEL"))
                    .addHeader("PhonePlatform", "Android")
                    .build();

            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(newRequest);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            int httpStatus = response.code();
            StringBuilder logSB = new StringBuilder();
            logSB.append("-------start:"+method+"\n");
            logSB.append(newRequest.toString()+"\n|");
            logSB.append(method.equalsIgnoreCase("POST")?"post参数{"+ postBodyString +"}\n|":"\n");
            logSB.append("httpCode=" + httpStatus + ";Response:" + content+"\n|");
            logSB.append("----------End:" + duration + "毫秒----------header |"+newRequest.headers().toString());
            ToastUtils.showLogI("cwp",logSB.toString());
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
        private String bodyToString(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null)
                    copy.writeTo(buffer);
                else
                    return "";
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }



}
