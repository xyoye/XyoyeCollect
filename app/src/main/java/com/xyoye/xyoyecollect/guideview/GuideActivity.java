package com.xyoye.xyoyecollect.guideview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xyoye.xyoyecollect.R;
import com.xyoye.xyoyecollect.guideview.guide.Component;
import com.xyoye.xyoyecollect.guideview.guide.Guide;
import com.xyoye.xyoyecollect.guideview.guide.GuideBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyy on 2018/5/22.
 */

public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        toolbarTitle.setText("引导层");

        bt1.post(new Runnable(){
            @Override
            public void run() {
                showGuideView();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuideView();
            }
        });
    }

    public void showGuideView(){
        List<View> views = new ArrayList<>();
        views.add(bt1);
        views.add(bt2);
        views.add(bt3);
        List<String> tips = new ArrayList<>();
        tips.add("这是提示一，点击再次开启引导");
        tips.add("这是提示二");
        tips.add("这是提示三");

        GuideBuilder builder = new GuideBuilder(GuideActivity.this);
        //添加需要提示的view
        builder.addTargetViewList(views)
                //提交提示
                .addTipsList(tips)
                //自定义的遮罩
                .setComponent(new GuideComponent())
                //背景透明度
                .setAlpha(200)
                //高亮区域圆角
                .setHighTargetCorner(20)
                //高亮区域图形样式
                .setHighTargetGraphStyle(Component.ANCHOR_BOTTOM)
                //是否覆盖view
                .setOverlayTarget(false)
                //设置遮罩系统是否可点击并处理点击事件
                .setOutsideTouchable(false)
                //设置遮罩可见状态变化时的监听回调
                .setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
                    @Override public void onShown(int showPosition) {
                        //Toast.makeText(MainActivity.this,"show"+showPosition,Toast.LENGTH_LONG).show();
                    }

                    @Override public void onDismiss() {
                        //Toast.makeText(MainActivity.this,"hide",Toast.LENGTH_LONG).show();
                    }
                });
        Guide guide = builder.createGuide();
        guide.show();
    }
}
