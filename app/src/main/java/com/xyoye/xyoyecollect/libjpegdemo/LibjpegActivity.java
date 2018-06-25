package com.xyoye.xyoyecollect.libjpegdemo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyoye.xyoyecollect.R;
import com.xyoye.xyoyecollect.libjpegdemo.libjpeg.FileUtils;
import com.xyoye.xyoyecollect.libjpegdemo.libjpeg.ImageUtils;
import com.xyoye.xyoyecollect.permissionchecker.PermissionHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YE on 2018/6/25 0025.
 */

public class LibjpegActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.method_one)
    Button methodOne;
    @BindView(R.id.method_two)
    Button methodTwo;
    @BindView(R.id.original_picture)
    Button original;
    @BindView(R.id.compress_picture)
    Button compress;
    @BindView(R.id.method_two_et)
    EditText methodTEt;
    @BindView(R.id.image_type)
    TextView imageType;
    @BindView(R.id.iv)
    ImageView mImage;

    String filePath = "";
    String fileName = "";
    int ratio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_libjpeg);
        ButterKnife.bind(this);
        toolbarTitle.setText("图片压缩");

        methodOne.setOnClickListener(this);
        methodTwo.setOnClickListener(this);
        original.setOnClickListener(this);
        compress.setOnClickListener(this);

        new PermissionHelper().with(LibjpegActivity.this).request(new PermissionHelper.OnSuccessListener() {
            @Override
            public void onPermissionSuccess() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.method_one:
                if (filePath.isEmpty())
                    Toast.makeText(LibjpegActivity.this,"请先选择原图",Toast.LENGTH_LONG).show();
                else
                    methodOne();
                break;
            case R.id.method_two:
                if (filePath.isEmpty())
                    Toast.makeText(LibjpegActivity.this,"请先选择原图",Toast.LENGTH_LONG).show();
                else if (methodTEt.getText().toString().isEmpty())
                    Toast.makeText(LibjpegActivity.this,"请选择压缩比例",Toast.LENGTH_LONG).show();
                else
                    ratio = Integer.parseInt(methodTEt.getText().toString());
                    methodTwo();
                break;
            case R.id.original_picture:
                if (filePath.isEmpty()){
                    getOriginalImage();
                }else {
                    String tempCompressImgPath = filePath + "/" + fileName +".jpg";
                    mImage.setImageBitmap(BitmapFactory .decodeFile(tempCompressImgPath));
                    imageType.setText("原图：");
                }
                break;
            case R.id.compress_picture:
                final File compressFile = new File(filePath + "/" + fileName + "_temp.jpg");
                mImage.setImageBitmap(BitmapFactory .decodeFile(compressFile.getPath()));
                break;
        }
    }

    /**
     * 先尺寸质量压缩后在用jni libjpeg压缩
     */
    private void methodOne(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final File compressFile = new File(filePath + "/" + fileName + "_temp.jpg");

                String tempCompressImgPath = filePath + "/" + fileName +".jpg";
                ImageUtils.compressBitmap(tempCompressImgPath, compressFile.getPath());
                LibjpegActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImage.setImageBitmap(BitmapFactory.decodeFile(compressFile.getPath()));
                        imageType.setText("压缩后：");
                    }
                });
            }
        }).start();
    }

    /**
     * 直接使用jni libjpeg压缩
     */
    private void methodTwo(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                final File compressFile = new File(filePath + "/" + fileName + "_temp.jpg");
                String originalFilePath = filePath + "/" + fileName +".jpg";//事先准备好的sd卡目录下的图片

                Bitmap bitmap = BitmapFactory.decodeFile(originalFilePath);
                ImageUtils.compressBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), ratio, compressFile.getAbsolutePath().getBytes(), true);

                LibjpegActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImage.setImageBitmap(BitmapFactory .decodeFile(compressFile.getPath()));
                        imageType.setText("压缩后：");
                    }
                });
            }
        }).start();
    }

    private void getOriginalImage(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,101);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                String path = FileUtils.getRealPath(this, data.getData());
                path = path.substring(0, path.lastIndexOf("."));
                filePath = path.substring(0, path.lastIndexOf("/"));
                fileName = path.substring(path.lastIndexOf("/")+1);
                String tempCompressImgPath = filePath + "/" + fileName +".jpg";
                mImage.setImageBitmap(BitmapFactory .decodeFile(tempCompressImgPath));
                imageType.setText("原图：");
                break;
        }
    }
}
