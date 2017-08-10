package com.example.zhang.retrofitutilsdemo.network;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yang
 * Time 2017/8/9.
 *
 * 网络请求的header文件
 *
 */

public class HeaderInterceptor implements Interceptor{
    private Request originalRequest;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        //header文件是可变的，需要灵活运用
        Request.Builder builder = originalRequest.newBuilder()
                .header("User-Agent","APPName-Android")
                .header("Content-type","application/json;charset=utf-8")
                .header("Connection","Keep-Alive")
                .method(originalRequest.method(),originalRequest.body());
        Request request = builder.build();
        return chain.proceed(request);
    }

}
