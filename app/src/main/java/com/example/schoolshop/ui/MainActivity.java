package com.example.schoolshop.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.HomeContract;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.gson.AdGson;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.presenter.HomePresenter;
import com.example.schoolshop.presenter.ShopCarPresenter;
import com.example.schoolshop.ui.homefragment.HomeFragment;
import com.example.schoolshop.ui.homefragment.ShopCarFragment;
import com.example.schoolshop.ui.homefragment.SortFragment;
import com.example.schoolshop.ui.homefragment.UserFragment;
import com.example.schoolshop.view.addialog.AdConstant;
import com.example.schoolshop.view.addialog.AdManager;
import com.example.schoolshop.view.addialog.bean.AdInfo;
import com.example.schoolshop.view.addialog.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements SortFragment.FragmentChangeListener, ShopCarContract.View, HomeContract.View {


    @InjectView(R.id.contentContainer)
    FrameLayout contentContainer;
    @InjectView(R.id.rb_home)
    RadioButton rbHome;
    @InjectView(R.id.rb_resource)
    RadioButton rbResource;
    @InjectView(R.id.rb_chat)
    RadioButton rbChat;
    @InjectView(R.id.rb_user)
    RadioButton rbUser;
    @InjectView(R.id.bottomBar)
    RadioGroup bottomBar;
    private FragmentManager supportFragmentManager;
    private HomeFragment homeFragment;
    private ShopCarFragment shopcarFragment;
    private SortFragment sortFragment;
    private UserFragment userFragment;
    private HomePresenter homePresenter = new HomePresenter(this);
    public static MainActivity instance;


    public static MainActivity getInstance() {
        if (instance == null) {
            return new MainActivity();
        }
        return instance;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        notSetStatusBarColor();

        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                hideAllFragment(transaction);
                switch (checkedId) {
                    case R.id.rb_home:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            transaction.add(R.id.contentContainer, homeFragment);
                        } else {
                            transaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_resource:
                        if (sortFragment == null) {
                            sortFragment = new SortFragment();
                            transaction.add(R.id.contentContainer, sortFragment);
                        } else {
                            transaction.show(sortFragment);
                        }
                        break;
                    case R.id.rb_chat:
                        if (shopcarFragment == null) {
                            shopcarFragment = new ShopCarFragment();
                            transaction.add(R.id.contentContainer, shopcarFragment);
                        } else {
                            transaction.show(shopcarFragment);
                        }
                        break;
                    case R.id.rb_user:
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                            transaction.add(R.id.contentContainer, userFragment);
                        } else {
                            transaction.show(userFragment);
                        }
                        break;
                }
                transaction.commit();
            }
        });
        showFirstPosition();
    }

    private void showFirstPosition() {
        supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.contentContainer, homeFragment);
        transaction.commit();
    }


    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (sortFragment != null) {
            transaction.hide(sortFragment);
        }
        if (shopcarFragment != null) {
            transaction.hide(shopcarFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
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
        ButterKnife.inject(this);
        homePresenter.getAdBanner("嘉兴");
    }

    private ShopCarPresenter shopCarPresenter=new ShopCarPresenter(this);
    @Override
    public void sendContent(String info) {
        if (!info.isEmpty()){
            supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            hideAllFragment(transaction);
            if (rbResource.isChecked()){
                rbResource.setChecked(false);
                rbChat.setChecked(true);
                shopCarPresenter.setUserOrder("1");
                if (shopcarFragment == null) {
                    shopcarFragment = new ShopCarFragment();
                    transaction.add(R.id.contentContainer, shopcarFragment);
                } else {
                    transaction.show(shopcarFragment);
                }
            }
            transaction.commit();
        }
    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitFailed(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadHomeData(List<BannerGson> bannerGsons, List<GoodGson.GoodsBean> goodGsons) {

    }

    @Override
    public void loadAD(List<AdGson> gsons) {
        ArrayList<AdInfo> advList = new ArrayList<>();
        for (int i = 0; i < gsons.size(); i++) {
            AdInfo adInfo = new AdInfo();
            adInfo.setActivityImg(gsons.get(i).getAd_str());
            advList.add(adInfo);
        }
        AdManager adManager = new AdManager(MainActivity.this, advList);
        adManager.setOverScreen(true)
                .setPageTransformer(new DepthPageTransformer())
                .setBackViewColor(Color.parseColor("#AA333333"))
                .setAnimBackViewTransparent(false)
                .setWidthPerHeight(0.65f)
                .setDialogCloseable(true)
                .setBounciness(15)
                .setSpeed(5)
                .setOnImageClickListener(new AdManager.OnImageClickListener() {
                    @Override
                    public void onImageClick(View view, AdInfo advInfo) {

                    }
                }).setOnCloseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        })

/**
 * 开始执行弹窗的显示操作，可传值为0-360，0表示从右开始弹出，逆时针方向，也可以传入自定义的方向值
 */
                .showAdDialog(AdConstant.ANIM_UP_TO_DOWN);
    }
}
