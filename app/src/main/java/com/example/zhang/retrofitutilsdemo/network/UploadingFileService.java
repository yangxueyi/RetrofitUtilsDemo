package com.example.zhang.retrofitutilsdemo.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by yang
 * Time 2017/8/9.
 *
 * 文件上传的接口
 */

public interface UploadingFileService {

    @Multipart
    @POST("singleupload")//括号里面是服务器接口地址
    Call<ResponseBody> uploadingFile(@Part("description") RequestBody description,
                                     @Part MultipartBody.Part file);

}
