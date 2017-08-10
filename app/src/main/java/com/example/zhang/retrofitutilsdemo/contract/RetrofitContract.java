package com.example.zhang.retrofitutilsdemo.contract;

import android.net.Uri;

import java.util.List;

/**
 * Created by Zhang
 * Time 2017/8/8.
 */

public interface RetrofitContract {


    interface UploadingImageCallback{

        void uploadingImageSuccess();
        void uploadingImageFailed();
        void onNetworkError();
    }


    interface Model {
        void uploadingImageResponse(String path, UploadingImageCallback uploadingImageCallback);//上传图片的结果
    }

    interface View {
        void showImage(Uri url);//显示图片
        void toast(String str);
    }

    interface Presenter {
        void showImage(List list);//获取图片
        void uploadingImage(String path);//上传图片
    }
}
