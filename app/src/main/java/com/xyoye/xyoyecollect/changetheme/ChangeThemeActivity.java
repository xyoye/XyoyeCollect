package com.xyoye.xyoyecollect.changetheme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.xyoye.xyoyecollect.R;
import com.xyoye.xyoyecollect.changetheme.weight.RippleAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyy on 2018/5/18.
 */

public class ChangeThemeActivity extends AppCompatActivity{
    @BindView(R.id.theme_layout)
    RelativeLayout themeLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.change_theme_view_group1)
    RelativeLayout changeThemeViewGroup1;
    @BindView(R.id.change_theme_view_group2)
    RelativeLayout changeThemeViewGroup2;
    @BindView(R.id.change_theme_view_group3)
    RelativeLayout changeThemeViewGroup3;
    @BindView(R.id.change_theme_view1)
    TextView changeThemeView1;
    @BindView(R.id.change_theme_view2)
    TextView changeThemeView2;
    @BindView(R.id.change_theme_view3)
    TextView changeThemeView3;

    @BindView(R.id.theme_switch)
    Switch themeSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_change_theme);
        ButterKnife.bind(this);

        toolbarTitle.setText("主题切换");

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                RippleAnimation.create(buttonView).setDuration(800).start();

                if (isChecked){
                    System.out.println("黑主题");
                    themeLayout.setBackgroundColor(Color.parseColor("#212121"));
                    toolbar.setBackgroundColor(Color.parseColor("#303030"));
                    changeThemeViewGroup1.setBackgroundColor(Color.parseColor("#303030"));
                    changeThemeViewGroup2.setBackgroundColor(Color.parseColor("#303030"));
                    changeThemeViewGroup3.setBackgroundColor(Color.parseColor("#303030"));
                    toolbarTitle.setTextColor(Color.parseColor("#b5b5b5"));
                    changeThemeView1.setTextColor(Color.parseColor("#b5b5b5"));
                    changeThemeView2.setTextColor(Color.parseColor("#b5b5b5"));
                    changeThemeView3.setTextColor(Color.parseColor("#b5b5b5"));
                }else {
                    System.out.println("白主题");
                    themeLayout.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    toolbar.setBackgroundColor(Color.parseColor("#1a97f9"));
                    changeThemeViewGroup1.setBackgroundColor(Color.parseColor("#ffffff"));
                    changeThemeViewGroup2.setBackgroundColor(Color.parseColor("#ffffff"));
                    changeThemeViewGroup3.setBackgroundColor(Color.parseColor("#ffffff"));
                    toolbarTitle.setTextColor(Color.parseColor("#ffffff"));
                    changeThemeView1.setTextColor(Color.parseColor("#000000"));
                    changeThemeView2.setTextColor(Color.parseColor("#000000"));
                    changeThemeView3.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
    }
}
