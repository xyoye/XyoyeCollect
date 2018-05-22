package com.xyoye.xyoyecollect.guideview.guide;

import android.app.Activity;
import android.view.View;

import java.util.List;

/**
 * Created by xyy on 2018/5/18.
 */

public class GuideBuilder {

  private Configuration mConfiguration;

  /**
   * Builder被创建后，不允许在对它进行更改
   */
  private boolean mBuilt;
  private Activity activity;
  private Component mComponent;
  private OnVisibilityChangedListener mOnVisibilityChangedListener;

  public GuideBuilder(Activity activity) {
    mConfiguration = new Configuration();
    this.activity = activity;
  }

  /**
   * 添加view集合
   */
  public GuideBuilder addTargetViewList(List<View> viewList) {
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    } else if (viewList.size() <= 0) {
      throw new RuntimeException("Illegal viewList. viewList size：0");
    }else if ( mConfiguration.mTipsList.size() > 0 && mConfiguration.mTipsList.size() != viewList.size()){
      throw new RuntimeException("ViewList and tipList must be the same");
    }
    mConfiguration.mTargetViewList = viewList;
    return this;
  }

  /**
   * 添加提示集合
   */
  public GuideBuilder addTipsList(List<String> tipList){
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    } else if (tipList.size() <= 0) {
      throw new RuntimeException("Illegal tips. tipsList size：0");
    }else if ( mConfiguration.mTargetViewList.size() > 0 && mConfiguration.mTargetViewList.size() != tipList.size()){
      throw new RuntimeException("ViewList and tipList must be the same");
    }
    mConfiguration.mTipsList = tipList;
    return this;
  }

  /**
   * 设置蒙板
   */
  public GuideBuilder setComponent(Component component){
    if (component == null){
      throw new RuntimeException("setComponent fail. Component not null");
    }
    mComponent = component;
    return this;
  }

  /**
   * 设置蒙板透明度
   */
  public GuideBuilder setAlpha(int alpha) {
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    } else if (alpha < 0 || alpha > 255) {
      throw new RuntimeException("Illegal alpha value, should between [0-255]");
    }
    mConfiguration.mAlpha = alpha;
    return this;
  }

  /**
   * 设置高亮区域的圆角大小
   *
   * @return GuideBuilder
   */
  public GuideBuilder setHighTargetCorner(int corner) {
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    } else if (corner < 0) {
      mConfiguration.mCorner = 0;
    }
    mConfiguration.mCorner = corner;
    return this;
  }

  /**
   * 设置高亮区域的图形样式
   *
   * @return GuideBuilder
   */
  public GuideBuilder setHighTargetGraphStyle(int style) {
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    }
    mConfiguration.mGraphStyle = style;
    return this;
  }

  /**
   * 设置蒙板颜色的资源id
   */
  public GuideBuilder setFullingColorId(int id) {
    if (mBuilt) {
      throw new RuntimeException("Already created. rebuild a new one.");
    } else if (id <= 0) {
      throw new RuntimeException("Illegal color resource id.");
    }
    mConfiguration.mFullingColorId = id;
    return this;
  }

  /**
   * 是否在点击的时候自动退出蒙板
   */
  public GuideBuilder setAutoDismiss(boolean b) {
    if (mBuilt) {
      throw new RuntimeException("Already created, rebuild a new one.");
    }
    mConfiguration.mAutoDismiss = b;
    return this;
  }

  /**
   * 是否覆盖目标
   * @param b true 遮罩将会覆盖整个屏幕
   */
  public GuideBuilder setOverlayTarget(boolean b) {
    if (mBuilt) {
      throw new RuntimeException("Already created, rebuild a new one.");
    }
    mConfiguration.mOverlayTarget = b;
    return this;
  }

  /**
   * 设置遮罩可见状态变化时的监听回调
   */
  public void setOnVisibilityChangedListener(
      OnVisibilityChangedListener onVisibilityChangedListener) {
    if (mBuilt) {
      throw new RuntimeException("Already created, rebuild a new one.");
    }
    mOnVisibilityChangedListener = onVisibilityChangedListener;
  }

  /**
   * 设置遮罩系统是否可点击并处理点击事件
   * @param touchable true 遮罩不可点击，处于不可点击状态 false 可点击，遮罩自己可以处理自身点击事件
   */
  public GuideBuilder setOutsideTouchable(boolean touchable) {
    mConfiguration.mOutsideTouchable = touchable;
    return this;
  }

  /**
   * 创建Guide
   * @return Guide
   */
  public Guide createGuide() {
    if (mConfiguration.mTipsList.size() < 1){
      throw new RuntimeException("must setTipList");
    }
    if (mConfiguration.mTargetViewList.size() < 1){
      throw new RuntimeException("must setTargetViewList");
    }
    if (mComponent == null){
      throw new RuntimeException("must setComponent");
    }
    Guide guide = new Guide(activity);
    guide.setComponent(mComponent);
    guide.setConfiguration(mConfiguration);
    guide.setCallback(mOnVisibilityChangedListener);
    mComponent = null;
    mConfiguration = null;
    mOnVisibilityChangedListener = null;
    mBuilt = true;
    return guide;
  }

  /**
   * 遮罩可见发生变化时的事件监听
   */
  public interface OnVisibilityChangedListener {

    void onShown(int showPosition);

    void onDismiss();
  }
}
