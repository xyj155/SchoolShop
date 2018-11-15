package com.example.schoolshop.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schoolshop.R;
import com.example.schoolshop.adapter.PaymentPagerAdapter;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.ui.deliver.GoodsFragment;
import com.example.schoolshop.ui.deliver.MySelfFragment;
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
import butterknife.OnClick;

public class PostDeliverActivity extends BaseActivity {

    @InjectView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ll_submit)
    LinearLayout llSubmit;
    private String[] title = {"货品快递", "我的添加"};

    @Override
    public int intiLayout() {
        return R.layout.activity_post_deliver;
    }

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("我的快递");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        ButterKnife.inject(this);
        List<Fragment> fragments = new ArrayList<>();
        PaymentPagerAdapter downloadPagerAdater = new PaymentPagerAdapter(getSupportFragmentManager(), fragments, title);
        fragments.add(new GoodsFragment());
        fragments.add(new MySelfFragment());
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

    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }


    @OnClick(R.id.ll_submit)
    public void onViewClicked() {
        startActivityForResult(new Intent(PostDeliverActivity.this,ExpressUserAddedActivity.class),0);
    }
}
