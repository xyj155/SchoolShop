package com.example.schoolshop.ui.homefragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseFragment;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ShopCarFragment extends BaseFragment {
    public static ShopCarFragment instance;

    public static ShopCarFragment getInstance() {
        if (instance == null) {
            return new ShopCarFragment();
        }
        return instance;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_shopcar;
    }

    @Override
    protected void init(View view) {

    }


    public void loadList() {
        Log.i(TAG, "loadList: ");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
