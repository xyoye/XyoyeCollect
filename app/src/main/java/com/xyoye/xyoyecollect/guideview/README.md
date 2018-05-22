# 引导层
## 使用

用于引导用户使用，对功能进行介绍。
使用此功能需要自定义一个Component（遮罩层），根据position对遮罩层进行view的改变
就能实现相应功能，另外，在Component中提供了方法用于自定义大小位置等。
在activity中使用guide时，设置Component为自定义Comment。

Activity中使用
```java****
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