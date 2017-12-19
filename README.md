# YGuider

![](https://i.imgur.com/jsmXJyL.gif)

## 简介
　　这是一个扫描框风格的新手引导，效果如上图。

## 由于在重构代码中，最新版本的Demo暂时不可用，想看Demo的请clone[在Nov 25, 2017前提交的Commit](https://github.com/totond/YGuider/tree/1894de1bea200c195fbfc8ef52a55fa2300d3080)，工作和生活有点忙，我也没想到拖这么久。


## 使用

### Gradle

```
compile 'com.yanzhikaijky:YGuider:0.9.2'
```
### 支持特性

 - 目前暂时只支持一个扫描框的引导（多扫描框的在设计）
 - 传入View对象就会根据它的位置信息生成坐标区域，保存在目标区域中。（所以View对象传入之后位置有更变的，请在`startGuide()`前重新调用`prepare()`）
 - 传入区域坐标矩形（left,top,right,bottom）也可以设置目标。
 - 可以自定义弹窗布局。
 - 可以自定义画扫描框的画笔

 > 具体的详细操作方法可以查看[Wiki](https://github.com/totond/YGuider/wiki)

### 使用方法

#### 简单使用
　　需要传入Activity对象来创建YGuider，然后加入想要被“扫描”的目标，最后调用`prepare()`方法就可以了完成准备了：

```

		mYGuider = new YGuider(this);
        mYGuider.addNextTarget(new RectF(70, 70, 170, 170), "点击这里可以立刻出现所有提示文字，大家好，我是YGuider！", 90, 0);
        mYGuider.addNextTarget(btn1, "这里是根据传入View来确定的区域!", 150, 10);
        mYGuider.addNextTarget(new RectF(50, 500, 150, 650), "这里是根据传入矩形区域坐标来确定的！", 100, 10);
        mYGuider.addNextTarget(
                        btn2, "通过设置偏移量来控制PopupWindow出现的位置，还可以设置它的大小！"
                        , -350, -350
                        , 430, ViewGroup.LayoutParams.WRAP_CONTENT);
        mYGuider.addNextTarget(
                        new RectF(500, 200, 600, 270), "可以动态改变两个按钮的文字",
                        -350, 50
                        , 300, ViewGroup.LayoutParams.WRAP_CONTENT
                        , "", "Finish");

        mYGuider.prepare();
```

　　最后在需要调用的时候，使用`startGuide()`方法开始新手引导：

```
                mYGuider.startGuide();
```

#### 方法介绍
　　由于方法过多，放在这里太占地方，所以请[移步Wiki](https://github.com/totond/YGuider/wiki)查看各个方法的使用。

#### Demo使用
　　Demo里面已经包含了使用YGuider的各种姿势，不过有一些注释掉而已，欢迎Fork来体验。^_^

## 后续
　　YGuider原理分析正在路上，后续更新目标是设计让YGuider可以使用多个扫描框（目前最大的难题是觉得那样会很丑），在这里请求意见和建议，请多多指教小弟。

## 版权

### TyperTextView
　　YGuider里面使用到的那个文字逐字出现的控件，是改写自hanks-zyh的[HTextView](https://github.com/hanks-zyh/HTextView)里面的TyperTextView，虽然被我改了很多，也在这里说明一下。

### 开源协议
　　YGuider遵循Apache2.0协议。

## 关于作者

 > id：炎之铠

 > 炎之铠的邮箱：yanzhikai_yjk@qq.com

 > CSDN：http://blog.csdn.net/totond
