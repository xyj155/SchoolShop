package com.example.schoolshop.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.util.StatusBarUtil;
import com.example.schoolshop.view.loadingdialog.view.LoadingDialog;

import java.util.List;


public abstract class BaseActivity extends FragmentActivity {
    /***是否显示标题栏*/
    private boolean isshowtitle = true;
    /***是否显示标题栏*/
    private boolean isshowstate = true;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    private ImageView back;
    private TextView  title;
    public static BaseActivity instances;
    private LoadingDialog loadingDialog;

    public static BaseActivity getInstances() {
        return instances;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置只竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StatusBarUtil.setStatusBarColor(this, 0xffF5C733);
        setContentView(intiLayout());
        initView();
        initData();

        instances = this;


    }

    public BaseActivity showDialog() {
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setLoadingText("加载中")
                .setSuccessText("加载成功")
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .closeSuccessAnim()
                .setRepeatCount(3)
                .setLoadStyle(LoadingDialog.STYLE_RING)
                .show();
        return this;
    }

    public BaseActivity dialogClose() {
        loadingDialog.close();
        return this;
    }

    public BaseActivity initToolBar() {
        back= findViewById(R.id.iv_back);
        title= findViewById(R.id.tv_title);
        return this;
    }

    public BaseActivity setToolbarBackIco() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return this;
    }

    public BaseActivity setToolBarTitle(String tv) {
        title.setText(tv);
        return this;
    }

    public BaseActivity setToolbarSubtitle(String titles) {
        title.setText(titles);
        return this;
    }


    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param context
     * @param activityName 某个界面名称
     * @return
     */
    public static boolean isForeground(Context context, String activityName) {
        if (context == null || TextUtils.isEmpty(activityName)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (activityName.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 设置布局     *     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();


    /**
     * 设置是否显示状态栏     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate = ishow;
    }

    /**
     * 显示长toast     * @param msg
     */
    public void toastLong(String msg) {
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    /**
     * 设置各个页面的状态栏颜色
     *
     * @return 颜色值
     */
    protected abstract int setStatusBarColor();

    /**
     * 显示短toast     * @param msg
     */
    public void toastShort(String msg) {
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    /**
     * 设置不需要设置状态栏的颜色
     * <p>
     * 是否设置StatusBar的颜色，绝大部分是要设置的，特殊的不需要设置，例如一个Activity中有多个Fragment的
     * Activity，多个Fragment的状态栏颜色可能不相同，那就只好交给Fragment自己去设置。遇到这样的Activity
     * 需要返回false
     */
    protected void notSetStatusBarColor() {
        StatusBarUtil.setStatusBarTranslucent(this);
    }
}
