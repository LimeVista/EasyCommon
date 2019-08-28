# EasyCommon [![](https://jitpack.io/v/LimeVista/EasyCommon.svg)](https://jitpack.io/#LimeVista/EasyCommon)
An Android Common Component（一个用于Android快速开发小工具集合）

## Old Version
* 新版本将支持 AndroidX, 1.x，接口对于 0.9.x 变化很大，0.9.x 不再更新
* 旧版本链接 [0.9.x](https://github.com/LimeVista/EasyCommon/blob/0.9.x/README.md)

## How to
* To get a Git project into your build:
* Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		// ...
		maven { url 'https://jitpack.io' }
	}
}
```

* Step 2. Add the dependency
```groovy
 implementation 'com.github.LimeVista.EasyCommon:common:1.1.0'
```

* Step 3.CompileOptions
```groovy
android {
    // ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
``` 