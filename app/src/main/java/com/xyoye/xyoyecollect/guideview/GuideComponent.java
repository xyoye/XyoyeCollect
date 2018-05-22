package com.xyoye.xyoyecollect.guideview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyoye.xyoyecollect.R;
import com.xyoye.xyoyecollect.guideview.guide.Component;

/**
 * Created by xyy on 2018/5/22.
 */

public class GuideComponent implements Component{
    private int position;

    @Override
    public View getView(LayoutInflater inflater, String tip, int position) {
        this.position = position;
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.component_guide,null);
        TextView tipsView = linearLayout.findViewById(R.id.tips_text);
        tipsView.setText(tip);

        return linearLayout;
    }

    @Override
    public int getAnchor() {
        if (position == 2)
            return Component.ANCHOR_TOP;
        return Component.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_END;
    }

    @Override
    public int getXOffset() {
        switch (position){
            case 0:
                return 120;
            case 1:
                return -20;
            default:
                return -10;
        }
    }

    @Override
    public int getYOffset() {
        if (position == 2)
            return -10;
        return 10;
    }

    @Override
    public int getPaddingLeft() {
        return 0;
    }

    @Override
    public int getPaddingRight() {
        return 0;
    }

    @Override
    public int getPaddingTop() {
        return 0;
    }

    @Override
    public int getPaddingBottom() {
        return 0;
    }
}
