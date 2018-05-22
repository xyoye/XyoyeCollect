package com.xyoye.xyoyecollect.carshcollect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xyoye.xyoyecollect.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyy on 2018/5/22.
 */

public class CrashActivity extends AppCompatActivity{
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);
        toolbarTitle.setText("错误收集");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 1/0;
            }
        });
    }
}
