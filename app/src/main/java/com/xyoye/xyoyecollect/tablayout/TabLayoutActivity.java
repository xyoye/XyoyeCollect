package com.xyoye.xyoyecollect.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xyoye.xyoyecollect.R;
import com.xyoye.xyoyecollect.tablayout.util.TabDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyy on 2018/5/25.
 */

public class TabLayoutActivity extends AppCompatActivity{
    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.tab_layout1)
    TabLayout tabLayout1;
    @BindView(R.id.tab_layout2)
    TabLayout tabLayout2;
    @BindView(R.id.tab_layout3)
    TabLayout tabLayout3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);
        toolbarTitle.setText("TabLayout");

        initView(tabLayout1);
        initView(tabLayout2);
        initView(tabLayout3);

        View view1 = tabLayout1.getChildAt(0);
        View view2 = tabLayout2.getChildAt(0);
        View view3 = tabLayout3.getChildAt(0);

        view1.setBackgroundDrawable(new TabDrawable(view1, TabDrawable.DRAW_LINE, 20));
        view2.setBackgroundDrawable(new TabDrawable(view2, TabDrawable.DRAW_RECT, 10,5,5));
        view3.setBackgroundDrawable(new TabDrawable(view3, TabDrawable.DRAW_CIRCLE, 0,5,5));
    }

    public void initView(TabLayout tabLayout){
        tabLayout.addTab(tabLayout.newTab().setText("新闻"));
        tabLayout.addTab(tabLayout.newTab().setText("娱乐"));
        tabLayout.addTab(tabLayout.newTab().setText("体育"));
        tabLayout.addTab(tabLayout.newTab().setText("财经"));
        tabLayout.addTab(tabLayout.newTab().setText("生活"));
    }
}
