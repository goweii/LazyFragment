# LazyFragment


## DEMO下载

[androidx-demo](https://github.com/goweii/LazyFragment/raw/master/appx/release/app-release.apk)

[support-demo](https://github.com/goweii/LazyFragment/raw/master/app/release/app-release.apk)


## 集成

- ### 添加jitpack库

```java
// build.gradle(Project:)
allprojects {
    repositories {
        ...
            maven { url 'https://www.jitpack.io' }
    }
}
```

- ### 添加依赖

  [点击查看最新版本号](https://github.com/goweii/LazyFragment/releases)
```java
// build.gradle(Module:)
dependencies {
    // support
    implementation 'com.github.goweii.LazyFragment:lazyfragment:1.1.0'
    // androidx
    implementation 'com.github.goweii.LazyFragment:lazyfragmentx:1.1.0'
}
```
