# EasyCommon [![](https://jitpack.io/v/LimeVista/EasyCommon.svg)](https://jitpack.io/#LimeVista/EasyCommon)
An Android Common Component（一个用于Android快速开发小工具集合）

## Old Version
* [0.9.x](https://github.com/LimeVista/EasyCommon/blob/0.9.x/README.md)
* [1.x](https://github.com/LimeVista/EasyCommon/blob/1.x/README.md)

# New
* RxJava3
* Android API 19+

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
 implementation 'com.github.LimeVista.EasyCommon:common:2.0.0'
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
