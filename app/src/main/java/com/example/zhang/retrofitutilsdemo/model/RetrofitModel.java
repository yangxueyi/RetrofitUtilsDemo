package com.example.zhang.retrofitutilsdemo.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.zhang.retrofitutilsdemo.contract.RetrofitContract;
import com.example.zhang.retrofitutilsdemo.network.AppHttpClient;
import com.example.zhang.retrofitutilsdemo.network.UploadingFileService;

import java.io.File;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zhang
 * Time 2017/8/8.
 */

public class RetrofitModel implements RetrofitContract.Model {
    Context context;
    private final AppHttpClient client;

    public RetrofitModel(Context context) {
        this.context = context;
        //获取AppHttpClient
        client = AppHttpClient.getInstance(context);
    }

    @Override
    public void uploadingImageResponse(String path, RetrofitContract.UploadingImageCallback callback) {

        if(!path.equals("")){

              File image = new File(path);

            Log.e("MainActivity",  "file = "+image.getPath()+"..........."+image.getName());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),image);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestFile);
            String descriptionString = "This is a description";
            RequestBody description =RequestBody.create( MediaType.parse("multipart/form-data"), descriptionString);

            Call<ResponseBody> call = client.createService(UploadingFileService.class).uploadingFile(description, body);
            call.enqueue(new CallBackUpload(this,callback));
        }

    }


    private static class CallBackUpload implements Callback<ResponseBody> {

        WeakReference<RetrofitModel> mModel;
        RetrofitContract.UploadingImageCallback callback;

        public CallBackUpload(RetrofitModel retrofitModel, RetrofitContract.UploadingImageCallback callback) {
            mModel = new WeakReference<RetrofitModel>(retrofitModel);
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            RetrofitModel model = mModel.get();
            if(model == null){
                return;
            }
            /*if(!response.isSuccessful()){
                callback.onNetworkError();
                Log.e("MainActivity", "onActivityResult:" + "fgsdfgfdgfdgfd积分刚发给");
                return;
            }*/

            if(response.code() == 200){
                callback.uploadingImageSuccess();
            }else {//TODO
                Log.e("MainActivity",  "code = " + response.code());
                callback.uploadingImageFailed();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {//网络问题会走该回调
            callback.onNetworkError();
        }
    }
}
