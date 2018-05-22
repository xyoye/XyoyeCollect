package com.xyoye.xyoyecollect.guideview.guide;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xyy on 2018/5/18.
 */
public interface Component {

  public final static int FIT_START = MaskView.LayoutParams.PARENT_START;

  public final static int FIT_END = MaskView.LayoutParams.PARENT_END;

  public final static int FIT_CENTER = MaskView.LayoutParams.PARENT_CENTER;

  public final static int ANCHOR_LEFT = MaskView.LayoutParams.ANCHOR_LEFT;

  public final static int ANCHOR_RIGHT = MaskView.LayoutParams.ANCHOR_RIGHT;

  public final static int ANCHOR_BOTTOM = MaskView.LayoutParams.ANCHOR_BOTTOM;

  public final static int ANCHOR_TOP = MaskView.LayoutParams.ANCHOR_TOP;

  public final static int ANCHOR_OVER = MaskView.LayoutParams.ANCHOR_OVER;

  /**
   * 圆角矩形&矩形
   */
  public final static int ROUNDRECT = 0;

  /**
   * 圆形
   */
  public final static int CIRCLE = 1;

  /**
   * 需要显示的view
   */
  View getView(LayoutInflater inflater, String tip, int position);

  /**
   * 相对目标View的锚点
   */
  int getAnchor();

  /**
   * 相对目标View的对齐
   */
  int getFitPosition();

  /**
   * 相对目标View的X轴位移，在计算锚点和对齐之后。
   *
   * @return X轴偏移量, 单位 dp
   */
  int getXOffset();

  /**
   * 相对目标View的Y轴位移，在计算锚点和对齐之后。
   *
   * @return Y轴偏移量，单位 dp
   */
  int getYOffset();

  int getPaddingLeft();

  int getPaddingRight();

  int getPaddingTop();

  int getPaddingBottom();
}
