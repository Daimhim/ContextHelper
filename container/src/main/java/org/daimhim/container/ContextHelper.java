package org.daimhim.container;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.List;

public class ContextHelper {
    public static String DEF_MAIN_PROCESS_NAME = "main";
    private ContextHelper() {
    }

    private static Application sApplication;
    public static void init(Application application){
        sApplication = application;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static boolean isMainProcess(){
        return TextUtils.equals(
                getProcessName(),
                DEF_MAIN_PROCESS_NAME
        );
    }

    private static String currentProcessName;
    private static String currentPackageName;
    public static String getProcessName(){
        String packageName = getCurrentPackageName();
        String replace = getCurrentProcessName(getApplication()).replace(packageName, "");
        if (replace.isEmpty()){
            return DEF_MAIN_PROCESS_NAME;
        }
        if (replace.startsWith(":")){
            return replace.substring(1);
        }
        return replace;
    }
    private static String getCurrentPackageName(){
        if (!TextUtils.isEmpty(currentPackageName)) {
            return currentPackageName;
        }
        currentPackageName = getApplication().getPackageName();
        return currentPackageName;
    }

    private static String getCurrentProcessName(Context context) {
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        currentProcessName = getCurrentProcessNameByApplication();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        currentProcessName = getCurrentProcessNameByActivityThread();
        if (!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        }

        currentProcessName = getCurrentProcessNameByActivityManager(context);

        return currentProcessName;
    }


    private static String getCurrentProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    private static String getCurrentProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    private static String getCurrentProcessNameByActivityManager(Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }
}
