package com.ecarxclub.app.base.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class BaseConverterFactory extends Converter.Factory {
    public static BaseConverterFactory create(){
        return  create(new Gson());
    }
    private final Gson gson;


    private BaseConverterFactory(Gson gson) {
        this.gson = gson;
    }
    @SuppressWarnings("ConstantConditions")
    public static BaseConverterFactory create(Gson gson){
        if(gson==null) throw new NullPointerException("gson==null");
        return new BaseConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new BaseResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new BaseRequestBodyConverter<>(gson, adapter);
    }

}
