package com.example.schoolshop.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.schoolshop.R;
import com.example.schoolshop.adapter.PaymentPagerAdapter;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.ui.paymentfragment.DeliverFragment;
import com.example.schoolshop.ui.paymentfragment.EvaluateFragment;
import com.example.schoolshop.ui.paymentfragment.ReceivedFragment;
import com.example.schoolshop.ui.paymentfragment.WaitPayingFragment;
import com.example.schoolshop.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserPaymentProcessActivity extends BaseActivity {

    @InjectView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    private String[] title = {"待付款", "待发货", "待收货", "待评价"};

    @Override
    public int intiLayout() {
        return R.layout.activity_user_payment_process;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("处理中心");
        List<Fragment> fragments = new ArrayList<>();
        PaymentPagerAdapter downloadPagerAdater = new PaymentPagerAdapter(getSupportFragmentManager(), fragments, title);
        fragments.add(new WaitPayingFragment());
        fragments.add(new DeliverFragment());
        fragments.add(new ReceivedFragment());
        fragments.add(new EvaluateFragment());
        viewPager.setAdapter(downloadPagerAdater);
        viewPager.setCurrentItem(0);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(title[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#1E90FF"), Color.parseColor("#1E90FF"), Color.parseColor("#1E90FF"), Color.parseColor("#1E90FF"), Color.parseColor("#1E90FF"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        Intent intent = getIntent();
        if (intent!=null){
            int index = intent.getIntExtra("index", 8);
            viewPager.setCurrentItem(index);
        }

        switch (getIntent().getIntExtra("index", 6)) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
