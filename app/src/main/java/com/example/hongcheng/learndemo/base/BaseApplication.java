package com.example.hongcheng.learndemo.base;

import android.app.Activity;
import android.app.Application;

import com.example.hongcheng.common.CrashHandler;
import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.db.DBCore;
import com.example.hongcheng.common.util.FileUtils;
import com.example.hongcheng.common.util.ResUtil;
import com.example.hongcheng.common.util.StringUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.apache.log4j.Level;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    private static List<Activity> activityList = new LinkedList<Activity>();

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        DBCore.init(this);
        ResUtil.init(this);
        mRefWatcher = BaseConstants.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
        initLog4j();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(){
        return getInstance().mRefWatcher;
    }

    public static void addActivity(Activity activity)
    {
        if(activityList != null && !activityList.contains(activity))
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity)
    {
        if(activityList != null && activityList.contains(activity))
            activityList.remove(activity);
    }

    public static void exit()
    {
        for(Activity activity : activityList)
        {
            activity.finish();
        }

        System.exit(0);
    }

    private void initLog4j() {
        final LogConfigurator logConfigurator = new LogConfigurator();

        String logPath = FileUtils.getLogFilePath();
        try {
            if (!StringUtils.isEmpty(logPath)) {
                File file = new File(logPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                logPath += File.separator + BaseConstants.LOG_FILE;
                file = new File(logPath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                logConfigurator.setFileName(logPath);
            }
            Level level = BaseConstants.DEBUG ? Level.ALL : Level.ERROR;
            logConfigurator.setRootLevel(level);
            logConfigurator.setLevel("org.apache", Level.ALL);
            logConfigurator.configure();
        } catch (Exception e) {
            android.util.Log.e("Application", e.getMessage(), e);
        }
    }
}
