package com.example.zhang.retrofitutilsdemo.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Zhang
 * Time 2017/8/9.
 */

public class ErrorInterceptor implements Interceptor{

    Context context;
    public ErrorInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
