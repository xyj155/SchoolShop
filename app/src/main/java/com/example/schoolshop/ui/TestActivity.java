package com.example.schoolshop.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolshop.R;
import com.example.schoolshop.adapter.ShopAdapter;
import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.presenter.ShopCarPresenter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements ShopCarContract.View {
    RecyclerView third_recyclerview;
    LinearLayout third_pay_linear;
    CheckBox third_allselect;
    Button third_submit;
    TextView edittext, third_totalnum, third_totalprice;
    private ShopCarPresenter presenter;
    private ShopAdapter adapter;
    BaseGson<UserShopCarGson> shopBean;
    List<BaseGson<UserShopCarGson>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //找控控件
        third_recyclerview = (RecyclerView) findViewById(R.id.third_recyclerview);
        third_pay_linear = (LinearLayout) findViewById(R.id.third_pay_linear);
        third_allselect = (CheckBox) findViewById(R.id.third_allselect);
        third_totalprice = (TextView) findViewById(R.id.third_totalprice);
        edittext = (TextView) findViewById(R.id.edittext);
        third_totalnum = (TextView) findViewById(R.id.third_totalnum);
        third_submit = (Button) findViewById(R.id.third_submit);
        //调用P层
        presenter = new ShopCarPresenter(this);
//        presenter.submitUserShopCar("1");
        //线性布局
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        third_recyclerview.setLayoutManager(manager);
        //绑定适配器
//        adapter = new ShopAdapter(TestActivity.this);
        third_recyclerview.setAdapter(adapter);

        third_allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.selectAll(third_allselect.isChecked());
            }
        });
        adapter.setListener(new ShopAdapter.UpdateUiListener() {
            @Override
            public void setTotal(String total, String num, int shopCount, boolean allCheck) {
                third_allselect.setChecked(allCheck);
                third_totalnum.setText(num);
                third_totalprice.setText(total);
            }
        });
    }

//    @Override
//    public void onsuccer(AddBean bean) {
//        adapter.add(bean);
//    }

//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {

//    }

//    @Override
//    public void loadFailed(String msg) {
//
//    }
//
//    private static final String TAG = "TestActivity";
//    @Override
//    public void loadShopCarList(BaseGson<UserShopCarGson> userShopCarGsons) {
//        Log.i(TAG, "loadShopCarList: "+userShopCarGsons);
//        adapter.add(userShopCarGsons);
//    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitFailed(String msg) {

    }
}

