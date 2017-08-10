package com.example.zhang.retrofitutilsdemo.network;

import android.content.Context;

import com.example.zhang.retrofitutilsdemo.BuildConfig;
import com.example.zhang.retrofitutilsdemo.network.url.Url;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zhang
 * Time 2017/8/8.
 */

public class AppHttpClient {

    private volatile static AppHttpClient appHttpClient;
    private static Retrofit retrofit;

    public static AppHttpClient getInstance(Context context){
        if(appHttpClient == null){
            synchronized (AppHttpClient.class){
                appHttpClient = new AppHttpClient();
                retrofit = new Retrofit.Builder()
                        .baseUrl(Url.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getNewClient(context))
                        .build();
            }
        }
        return appHttpClient;
    }

    private static OkHttpClient getNewClient(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if(BuildConfig.DEBUG){
            //log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Debug Log模式
            builder.addInterceptor(loggingInterceptor);
        }

        builder.addInterceptor(new HeaderInterceptor());//设置头部拦截器
//        builder.addInterceptor(new ErrorInterceptor(context));//设置错误拦截器
        builder.connectTimeout(60, TimeUnit.SECONDS);//设置连接超时时间
        builder.writeTimeout(60,TimeUnit.SECONDS);//写入超时时间
        builder.readTimeout(60,TimeUnit.SECONDS);//读取超时时间

        return  builder.build();
    }


    /**
     * 获取service
     * */
    public <T>T createService(final Class<T> service){
        return retrofit.create(service);
    }

}
