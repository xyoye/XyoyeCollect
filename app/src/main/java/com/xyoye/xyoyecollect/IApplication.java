package com.xyoye.xyoyecollect;

import android.app.Application;
import android.os.Environment;

import com.xyoye.xyoyecollect.carshcollect.carsh.CrashHandler;

/**
 * Created by xyy on 2018/5/22.
 */

public class IApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        String crashPath = Environment.getExternalStorageDirectory() + "/XyoyeCollect/crash_log/";
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext(), crashPath);
    }
}
