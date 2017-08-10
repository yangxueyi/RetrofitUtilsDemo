package com.example.zhang.retrofitutilsdemo.presenter;

import android.net.Uri;

import com.example.zhang.retrofitutilsdemo.contract.RetrofitContract;
import com.example.zhang.retrofitutilsdemo.model.RetrofitModel;

import java.util.List;

/**
 * Created by Zhang
 * Time 2017/8/8.
 */

public class RetrofitPresenter implements RetrofitContract.Presenter {
     RetrofitContract.View myView;
     RetrofitModel myModel;
    public RetrofitPresenter(RetrofitContract.View view, RetrofitModel retrofitModel) {
        this.myView = view;
        this.myModel = retrofitModel;
    }

    @Override
    public void showImage(List list) {

        if(list.size()!=0) {
            myView.showImage((Uri) list.get(0));
        }else {
            myView.toast("未选择图片，请选择！！！");
        }
    }


    @Override
    public void uploadingImage(String path) {

        if(path.equals("")) {
            myView.toast("请选择图片后再上传");
        }else {
            myModel.uploadingImageResponse(path, new RetrofitContract.UploadingImageCallback() {
                @Override
                public void uploadingImageSuccess() {
                    myView.toast("上传成功");
                }

                @Override
                public void uploadingImageFailed() {
                    myView.toast("上传失败");
                }

                @Override
                public void onNetworkError() {
                    myView.toast("网络异常");
                }
            });


        }


    }
}
