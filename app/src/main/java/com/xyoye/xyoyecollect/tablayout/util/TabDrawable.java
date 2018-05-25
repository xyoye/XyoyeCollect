package com.xyoye.xyoyecollect.tablayout.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by xyy on 2018/5/25.
 */

public class TabDrawable extends Drawable{
    public final static int DRAW_LINE = 0;
    public final static int DRAW_RECT = 1;
    public final static int DRAW_CIRCLE = 2;

    private View view;
    private Paint paint;
    private int drawType = 0;
    private float density;

    private int lineWidth = 0;
    private int radius = 0;
    private float paddingLeft;
    private float paddingTop;

    public TabDrawable(View view, int drawType, int lineWidth){
        if (drawType != DRAW_LINE)
            throw new IllegalStateException("The Method \"drawType\" must DRAW_LINE");

        this.view = view;
        this.drawType = drawType;
        this.lineWidth = lineWidth;

        init(0, 2);
    }

    public TabDrawable(View view, int drawType, int radius, int paddingLeft, int paddingTop){
        if (drawType == DRAW_LINE)
            throw new IllegalStateException("The Method \"drawType\" must DRAW_RECT Or DRAW_CIRCLE");

        this.view = view;
        this.drawType = drawType;
        this.radius = radius;

        init(paddingLeft, paddingTop);
    }

    private void init( int paddingLeftt, int paddingTopp){
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        density = view.getResources().getDisplayMetrics().density;
        paddingLeft = paddingLeftt * density;
        paddingTop = paddingTopp * density;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int mIndicatorLeft = getIntValue("mIndicatorLeft");
        int mIndicatorRight = getIntValue("mIndicatorRight");
        int height = view.getHeight();

        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
            if (drawType == DRAW_LINE){
                drawLine(canvas, mIndicatorLeft, mIndicatorRight, height, lineWidth);
            }else if (drawType == DRAW_RECT){
                drawRect(canvas, mIndicatorLeft, mIndicatorRight, height, radius);
            }else if (drawType == DRAW_CIRCLE){
                drawRect(canvas, mIndicatorLeft, mIndicatorRight, height, height / 2);
            }
        }
    }

    private void drawRect(Canvas canvas, int mIndicatorLeft, int mIndicatorRight, int height, int radius){
        canvas.drawRoundRect(new RectF(mIndicatorLeft + (int)paddingLeft,
                        (int)paddingTop,
                        mIndicatorRight - (int)paddingLeft,
                        height - (int)paddingTop),
                radius, radius, paint);
    }

    private void drawLine(Canvas canvas, int mIndicatorLeft, int mIndicatorRight, int height, int width){
        int center = (mIndicatorRight - mIndicatorLeft)/2 + mIndicatorLeft;
        int widthHalf = (int)(width * density) / 2;
        int p = (int)(2 * density);
        paint.setStrokeWidth(p);
        canvas.drawLine(center - widthHalf,
                height - p,
                center + widthHalf,
                height - p, paint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    private int getIntValue(String name){
        try{
            Field field = view.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Object object = field.get(view);
            return (Integer) object;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
