package com.xyoye.xyoyecollect;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xyoye.xyoyecollect.carshcollect.CrashActivity;
import com.xyoye.xyoyecollect.changetheme.ChangeThemeActivity;
import com.xyoye.xyoyecollect.guideview.GuideActivity;
import com.xyoye.xyoyecollect.permissionchecker.PermissionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.change_theme_module)
    Button changeThemeModule;
    @BindView(R.id.guide_view)
    Button guideView;
    @BindView(R.id.crash)
    Button crash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbarTitle.setText("XyoyeCollect");

        initListener();
    }

    private void initListener(){
        changeThemeModule.setOnClickListener(this);
        guideView.setOnClickListener(this);
        crash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_theme_module:
                launchActivity(ChangeThemeActivity.class);
                break;
            case R.id.guide_view:
                launchActivity(GuideActivity.class);
                break;
            case R.id.crash:
                new PermissionHelper().with(MainActivity.this).request(new PermissionHelper.OnSuccessListener() {
                    @Override
                    public void onPermissionSuccess() {
                        launchActivity(CrashActivity.class);
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    private void launchActivity(Class clas){
        Intent intent = new Intent(MainActivity.this, clas);
        startActivity(intent);
    }
}
