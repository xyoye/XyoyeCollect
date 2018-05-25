# TabLayout自定义
目前为止，发现的最好的自定义TabLayout下划线的方法
## 优点
可自定义任意长度的TabLayout下划线，且与文字长度无关。

其它通过反射设置margin值方法不能使下划线短于文字长度， 否则文字长度会改变

## 实现方式

通过反射重新绘制Tab背景

##注意
app:tabBackground=”@null”       去除tab点击的阴影效果 
app:tabIndicatorColor=”@null”   去除tab的下划线 
