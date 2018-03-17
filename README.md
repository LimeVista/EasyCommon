# EasyCommon [![](https://jitpack.io/v/LimeVista/EasyCommon.svg)](https://jitpack.io/#LimeVista/EasyCommon)
An Android Common Component（一个用于Android快速开发小工具集合）

## How to
* To get a Git project into your build:
* Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

* Step 2. Add the dependency
```groovy
 compile 'com.github.LimeVista:EasyCommon:0.1.0'
```

* Step 3.CompileOptions
```groovy
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
``` 