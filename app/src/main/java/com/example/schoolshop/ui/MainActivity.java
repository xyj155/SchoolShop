package com.example.schoolshop.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.ui.homefragment.HomeFragment;
import com.example.schoolshop.ui.homefragment.ShopCarFragment;
import com.example.schoolshop.ui.homefragment.SortFragment;
import com.example.schoolshop.ui.homefragment.UserFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {


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
    }


}
