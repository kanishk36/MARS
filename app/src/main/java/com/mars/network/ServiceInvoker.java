package com.mars.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mars.BuildConfig;
import com.mars.utils.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public enum ServiceInvoker {

    Instance;

    private static final String TAG = ServiceInvoker.class.getSimpleName();
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final boolean isDegubBuild = BuildConfig.DEBUG;

    private static HttpLoggingInterceptor addLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private RequestBody prepareRequest(Object request) {
        if (request == null) return null;
//        String encReq = "";
        String json = gson.toJson(request);
        Log.d(TAG, "req before encrypt " + json);

        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    public <U> Observable<U> invoke(final String url, final Class<U> returnClsType, String methodType) {
        return invoke(url, null, returnClsType, methodType);
    }

    public <T, U> Observable<U> invoke(final String url, final T request, final Class<U> returnClsType, String methodType) {

        RxCallAdapterFactory callFact = (RxCallAdapterFactory) RxCallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(AppConstants.ServiceURLs.Companion.getBASE_URL())
                .addConverterFactory(ToStringConverterFactory.create())
                .addCallAdapterFactory(callFact)
                .build();

        APICaller caller = retrofit.create(APICaller.class);
        Observable<Response<String>> responseObsrv = null;

        if(methodType.equals(AppConstants.HttpMethods.Companion.getHTTP_GET())) {
            responseObsrv = request == null ? caller.get(url) : caller.get(url, prepareRequest(request));

        }
//        else if(methodType.equals(AppConstants.HttpMethods.Companion.getHTTP_POST())) {
//            responseObsrv = request == null ? caller.post(url) : caller.post(url, prepareRequest(request));
//        }

        return responseObsrv.map(new Function<Response<String>, U>() {
            @Override
            public U apply(Response<String> response) {
                String strResp = response.body();
                return ServiceInvoker.this.decryptResponse(strResp, returnClsType);
            }
        });

    }

    public <T> Observable<T> getPlace(final String url, final Class<T> returnClsType) {
        RxCallAdapterFactory callFact = (RxCallAdapterFactory) RxCallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(AppConstants.ServiceURLs.Companion.getGOOGLE_API())
                .addConverterFactory(ToStringConverterFactory.create())
                .addCallAdapterFactory(callFact)
                .build();

        APICaller caller = retrofit.create(APICaller.class);
        Observable<Response<String>> responseObsrv = caller.get(url);

        return responseObsrv.map(new Function<Response<String>, T>() {
            @Override
            public T apply(Response<String> response) {
                String strResp = response.body();
                return ServiceInvoker.this.decryptResponse(strResp, returnClsType);
            }
        });
    }

    //this method will give the http client for header connection
    private OkHttpClient getHttpClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100, TimeUnit.SECONDS);
        builder.readTimeout(100, TimeUnit.SECONDS);
        builder.writeTimeout(100, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @NotNull
            @EverythingIsNonNull
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                String url = request.url().toString();
                url = url.replace("%3F", "?");

                request = request.newBuilder()
                        .header("Accept", "application/json")
                        .url(url).build();

                return chain.proceed(request);
            }
        });

        if(isDegubBuild) {
            builder.addInterceptor(addLogging());
        }

        return builder.build();

    }

    private <T> T decryptResponse(final String response, final Class<T> responseClass) {
        if (response == null) return null;

        Log.d("response before decrypt", response);
        return gson.fromJson(response, responseClass);
    }

    private class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json.getAsJsonPrimitive().getAsString();
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            if(src == null) {
                return new JsonPrimitive("");
            } else {
                return new JsonPrimitive(src);
            }
        }
    }

}
