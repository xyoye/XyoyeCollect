package com.xyoye.xyoyecollect.guideview.guide;

import android.app.Activity;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 遮罩系统的封装,外部需要调用GuideBuilder来创建该实例，实例创建后调用
 * Created by xyy on 2018/5/18.
 */
public class Guide implements View.OnKeyListener, View.OnClickListener {

  private Activity activity;
  private Configuration mConfiguration;
  private MaskView mMaskView;
  private Component mComponent;

  private GuideBuilder.OnVisibilityChangedListener mOnVisibilityChangedListener;

  Guide(Activity activity) {
    this.activity = activity;
  }

  void setConfiguration(Configuration configuration) {
    mConfiguration = configuration;
  }

  void setComponent(Component component) {
    mComponent = component;
  }

  void setCallback(GuideBuilder.OnVisibilityChangedListener listener) {
    mOnVisibilityChangedListener = listener;
  }

  private MaskView onCreateView(Activity activity, View view, int viewId, String tip, int position) {
    ViewGroup content = activity.findViewById(android.R.id.content);
    MaskView maskView = new MaskView(activity);
    maskView.addView(componentToView(activity.getLayoutInflater(), tip ,position));
    mConfiguration.mPaddingLeft = mComponent.getPaddingLeft();
    mConfiguration.mPaddingRight = mComponent.getPaddingRight();
    mConfiguration.mPaddingTop = mComponent.getPaddingTop();
    mConfiguration.mPaddingBottom = mComponent.getPaddingBottom();
    maskView.setPadding(mConfiguration.mPadding);
    maskView.setPaddingLeft(mConfiguration.mPaddingLeft);
    maskView.setPaddingTop(mConfiguration.mPaddingTop);
    maskView.setPaddingRight(mConfiguration.mPaddingRight);
    maskView.setPaddingBottom(mConfiguration.mPaddingBottom);
    maskView.setFullingColor(activity.getResources().getColor(mConfiguration.mFullingColorId));
    maskView.setFullingAlpha(mConfiguration.mAlpha);
    maskView.setHighTargetCorner(mConfiguration.mCorner);
    maskView.setHighTargetGraphStyle(mConfiguration.mGraphStyle);
    maskView.setOverlayTarget(mConfiguration.mOverlayTarget);
    maskView.setOnKeyListener(this);

    int parentY;
    final int[] loc = new int[2];
    content.getLocationInWindow(loc);
    parentY = loc[1];//通知栏的高度

    if (view != null) {
      maskView.setTargetRect(getViewAbsRect(view, parentY));
    } else {
      View target = activity.findViewById(viewId);
      if (target != null) {
        maskView.setTargetRect(getViewAbsRect(target,parentY));
      }
    }

    View fulling = activity.findViewById(mConfiguration.mFullingViewId);
    if (fulling != null) {
      maskView.setFullingRect(getViewAbsRect(fulling, parentY));
    }

    if (mConfiguration.mOutsideTouchable) {
      maskView.setClickable(false);
    } else {
      maskView.setOnClickListener(this);
    }

    return maskView;
  }

  private void onDestroy() {
    mConfiguration = null;
    mOnVisibilityChangedListener = null;
    mMaskView.removeAllViews();
    mMaskView = null;
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
      if (mConfiguration != null && mConfiguration.mAutoDismiss) {
        dismiss();
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  @Override
  public void onClick(View v) {
    if (mConfiguration != null && mConfiguration.mAutoDismiss) {
      dismiss();
    }
  }

  /**
   * Rect在屏幕上去掉状态栏高度的绝对位置
   */
  private Rect getViewAbsRect(View view, int parentY) {
    int[] loc = new int[2];
    view.getLocationInWindow(loc);
    Rect rect = new Rect();
    rect.set(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
    rect.offset(0, -parentY);
    return rect;
  }

  /**
   * 设置Component
   */
  private View componentToView(LayoutInflater inflater, String tip , int position) {
    View view = mComponent.getView(inflater, tip, position);
    final MaskView.LayoutParams lp = new MaskView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    lp.offsetX = mComponent.getXOffset();
    lp.offsetY = mComponent.getYOffset();
    lp.targetAnchor = mComponent.getAnchor();
    lp.targetParentPosition = mComponent.getFitPosition();
    view.setLayoutParams(lp);
    return view;
  }

  /**
   * 显示该遮罩
   */
  public void show() {
    if (mConfiguration.mTargetViewList.size() < 1)
      return;
    mMaskView = null;
    int position = mConfiguration.viewListN;
    View view = mConfiguration.mTargetViewList.get(position);
    String tip = mConfiguration.mTipsList.get(position);
    mMaskView = onCreateView(activity,view,-1,tip,position);
    mConfiguration.viewListN++;
    ViewGroup content = activity.findViewById(android.R.id.content);
    if (mMaskView.getParent() == null) {
      content.addView(mMaskView);
      if (mOnVisibilityChangedListener != null) {
        mOnVisibilityChangedListener.onShown(mConfiguration.viewListN-1);
      }
    }
  }

  /**
   * 显示下一个遮罩
   */
  public void dismiss() {
    if (mMaskView == null) {
      return;
    }
    final ViewGroup content = (ViewGroup) mMaskView.getParent();
    if (content == null) {
      return;
    }
    content.removeView(mMaskView);
    if (mConfiguration.viewListN < mConfiguration.mTargetViewList.size()){
      show();
    }else {
      if (mOnVisibilityChangedListener != null) {
        mOnVisibilityChangedListener.onDismiss();
      }
      onDestroy();
    }
  }

}
