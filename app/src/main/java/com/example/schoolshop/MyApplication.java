package com.example.schoolshop;

import android.app.Application;
import android.util.DisplayMetrics;

import com.example.schoolshop.view.addialog.utils.DisplayUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.payelves.sdk.EPay;

import io.rong.imkit.RongIM;

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
        Fresco.initialize(this);
        initDisplayOpinion();
    }
    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }
}
