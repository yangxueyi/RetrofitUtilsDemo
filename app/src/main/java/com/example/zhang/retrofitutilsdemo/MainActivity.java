package com.example.zhang.retrofitutilsdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhang.retrofitutilsdemo.contract.RetrofitContract;
import com.example.zhang.retrofitutilsdemo.model.RetrofitModel;
import com.example.zhang.retrofitutilsdemo.presenter.RetrofitPresenter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RetrofitContract.View,View.OnClickListener{

    private Button obtian;
    private Button uploading;
    private ImageView iv;
    private RetrofitPresenter presenter;

    private static final int REQUEST_CODE_CHOOSE = 001;
    private List<Uri> uris = new ArrayList<Uri>();

    private Button show;
    private String srcPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

    }

    private void initView() {
        obtian = (Button) findViewById(R.id.btn_obtian);
        show = (Button) findViewById(R.id.btn_show);
        uploading = (Button) findViewById(R.id.btn_uploading);
        iv = (ImageView) findViewById(R.id.iv);

        presenter = new RetrofitPresenter(this,new RetrofitModel(getApplicationContext()));
    }

    private void initListener() {
        obtian.setOnClickListener(this);
        show.setOnClickListener(this);
        uploading.setOnClickListener(this);
    }

    @Override
    public void showImage(Uri url) {
        Glide.with(this)
                .load(url)
                .into(iv);
    }

    @Override
    public void toast(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_obtian:
                //跳转到图片库
                goImagerLibrary();
                break;
            case R.id.btn_show:
                    presenter.showImage(uris);
                break;
            case R.id.btn_uploading:
                presenter.uploadingImage(srcPath);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            uris = Matisse.obtainResult(data);//获取图片的url集合
            //将uri转换为图片地址
            srcPath = getPath();
            Log.e("MainActivity", "onActivityResult:" + srcPath);
        }
    }

    private void goImagerLibrary() {
        Matisse
                .from(MainActivity.this)
                .choose(MimeType.ofAll())//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(9)//最大选择数量为9
                .gridExpectedSize(getResources() .getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
//                        .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new GlideEngine())//加载方式
                .forResult(REQUEST_CODE_CHOOSE);//请求码
    }

    @NonNull
    private String getPath() {
        ContentResolver cr = this.getContentResolver();
        Cursor c= cr.query(uris.get(0), null, null, null, null);
        c.moveToFirst();
        //这是获取的图片保存在sdcard中的位置
        int colunm_index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        String srcPath = c.getString(colunm_index);
        File fileImage = new File(srcPath);
        return srcPath;
    }
}
