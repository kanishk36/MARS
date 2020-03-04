package com.mars.network;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class ToStringConverterFactory extends Converter.Factory {
   // private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    static ToStringConverterFactory create(){
        return new ToStringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException{
                    return value.string();
                }
            };
        }
        return null;
    }
}
