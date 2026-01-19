package com.example.chapter_02;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class MyApplication extends Application {

    public static final String TAG = "LYZ";

    @Override
    public void onCreate() {
        super.onCreate();
        // 调用新添加的方法来获取进程名
        String processName = getProcessName(getApplicationContext());
        Log.i(TAG, "onCreate: " + processName);
    }

    /**
     * 获取当前进程的名称。
     * @param context 上下文
     * @return 当前进程名，获取失败则返回 null。
     */
    public static String getProcessName(Context context) {
        if (context == null) {
            return null;
        }

        // 对于 Android 9.0 (API 28) 及以上版本，可以直接使用 getProcessName()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return getProcessName();
        }

        // 对于旧版本，使用 ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }

        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }

        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
            if (processInfo.pid == myPid) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
