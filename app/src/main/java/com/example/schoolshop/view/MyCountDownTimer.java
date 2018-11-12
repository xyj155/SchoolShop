package com.example.schoolshop.view;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountDownTimer extends CountDownTimer {

    private TextView timeButton;

    public MyCountDownTimer(TextView timeButton, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeButton = timeButton;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        timeButton.setClickable(false);
        timeButton.setText(l / 1000 + "秒");

    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        timeButton.setText("重新获取");
        //设置可点击
        timeButton.setClickable(true);
    }


}
