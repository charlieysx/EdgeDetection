EdgeDetection
===================

图片边缘检测

---

###使用RenderScript前的配置
1.android studio中的配置:
在build.gradle中添加：

```javascript

android {
    defaultConfig {
        ...
    	renderscriptTargetApi 18
        renderscriptSupportModeEnabled true
		...
	}   
}
```
2.eclipse中的配置：
在project.properties中添加：

```javascript

renderscript.target=18
renderscript.support.mode=true

```

RenderScript的使用方法可参考我的代码，或参考我后面附的参考资料

---

###效果图
从左往右分别是原图、灰度图、进行边缘检测后的图

![a1](/img/a1.jpg "a1")
![a2](/img/a2.jpg "a2")
![a3](/img/a3.jpg "a3")

![b1](/img/b1.jpg "b1")
![b2](/img/b2.jpg "b2")
![b3](/img/b3.jpg "b3")

![c1](/img/c1.jpg "c1")
![c2](/img/c2.jpg "c2")
![c3](/img/c3.jpg "c3")

---

###参考资料
1. [Android自动手绘，圆你儿时画家梦!][1]
2. [RenderScript 让你的Android计算速度快的飞上天!][2]
3. [Android高效计算——RenderScript][3]
4. [RenderScript :简单而快速的图像处理][4]
5. [Sobel边缘检测算法][5]


[1]: http://blog.csdn.net/huachao1001/article/details/51518322
[2]: http://blog.csdn.net/huachao1001/article/details/51524502
[3]: http://www.cnblogs.com/willhua/p/5782293.html
[4]: http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0504/4205.html
[5]: http://www.cnblogs.com/lancidie/archive/2011/07/17/2108885.html


```