package com.example.schoolshop;

import android.app.Application;

import com.payelves.sdk.EPay;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2018/11/5.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        EPay.getInstance(getApplicationContext()).init("wAwS4BHkB", "1b0ccf51458c4053ae2931772fbbfb97",
                "7169149861036033", "baidu");

    }
}
