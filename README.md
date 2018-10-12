# Android 圆形渐变带bar进度条

ps：首次编写开源控件，还望多多包涵

### 引用依赖

```javascript
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```


​	
```javascript
dependencies {
	implementation 'com.github.Vennxu:CicleProgressBar:1.0.2'
}
```

### 参数说明

```java
<attr name="round_start_color" format="color"/>  渐变开始颜色
<attr name="round_end_color" format="color"/> 渐变结束颜色
<attr name="round_bg_color" format="color"/> 进度条背景颜色
<attr name="round_thumb_out_color" format="color"/> 圆球外圈背景颜色
<attr name="round_radius" format="dimension"/> 半径
<attr name="round_thumb_radius" format="dimension"/> 圆球半径
<attr name="round_thumb_out_radius" format="dimension"/> 圆球外圈半径
<attr name="round_progress" format="float"/> 进度值
<attr name="round_max_progress" format="integer"/> 最大进度
<attr name="round_bg_progress_width" format="dimension"/> 
<attr name="round_full_progress_width" format="dimension"/>
<attr name="round_start_angle" format="float"/> 起始角度
```

### 例子截图

![image](https://raw.githubusercontent.com/Vennxu/CicleProgressBar/master/img/1.png)