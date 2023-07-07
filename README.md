# ContextHelper
封装的全局context和进程工具
### 功能
```
//初始化
ContextHelper.init(Application application)
// 获取当前的Application
ContextHelper.getApplication()
// 获取当前进程名
ContextHelper.getProcessName() 
// 主进程名
ContextHelper.DEF_MAIN_PROCESS_NAME
```
#### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.Daimhim:ContextHelper:1.0.3'
	}
```
