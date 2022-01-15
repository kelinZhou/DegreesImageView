# DegreesImageView
360度图片预览组件。

## 简介
基于Glide和RxJava实现一样代码即可实现图片360度预览。Glide和RxJava为默认的实现方式，也可以通过实现ImageLoader接口使用其他方式实现。

## 更新
1.0.0 完成360预览功能。

## 下载
###### 第一步：添加 JitPack 仓库到你项目根目录的 gradle 文件中。
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
###### 第二步：添加这个依赖。
```
dependencies {
    implementation 'com.github.kelinZhou:DegreesImageView:${last version here}'
}
```

## 效果图
#### 懒得录gif了，凑合看吧。
![静态预览](materials/preview.jpg)
