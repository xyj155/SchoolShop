package com.example.schoolshop.ui;

import android.support.design.widget.BottomSheetDialog;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;

public class CurrierDetailActivity extends BaseActivity {



    @Override
    public int intiLayout() {
        return R.layout.activity_currier_detail;
    }

    @Override
    public void initView() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_goods_sku_layout);
        bottomSheetDialog.show();
    }

    @Override
    public void initData() {

    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }
}
