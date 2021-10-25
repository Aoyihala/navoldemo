package com.mimimi.libs;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.hawk.Hawk;

import org.xutils.x;

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //注册ARouter
        ARouter.init(this);
        x.Ext.init(this);
        Hawk.init(this).build();
        context = this;

    }

    public static Context getContext() {
        return context;
    }
}
