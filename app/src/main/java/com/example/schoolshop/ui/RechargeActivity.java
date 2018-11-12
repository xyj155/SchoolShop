package com.example.schoolshop.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.util.SoftKeyboardUtil;
import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ty_recharge)
    RecyclerView tyRecharge;
    @InjectView(R.id.et_self)
    EditText etSelf;
    @InjectView(R.id.btn_recharge)
    Button btnRecharge;
    private List<String> rechargeList = new ArrayList<>();
    private RechargeAdapter rechageAdapter = new RechargeAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("充值");
        rechargeList.add("50");
        rechargeList.add("100");
        rechargeList.add("2000");
        rechargeList.add("500");
        rechargeList.add("1000");
        rechargeList.add("1500");
        rechargeList.add("2000");
        rechargeList.add("5000");
        tyRecharge.setLayoutManager(new GridLayoutManager(RechargeActivity.this, 4));
        rechageAdapter.replaceData(rechargeList);
        tyRecharge.setAdapter(rechageAdapter);
        etSelf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rechageAdapter.map.clear();
                Log.i(TAG, "onFocusChange: "+rechageAdapter.map);
                rechageAdapter.notifyDataSetChanged();
            }
        });
        etSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechageAdapter.map.clear();
                Log.i(TAG, "onFocusChange: "+rechageAdapter.map);
                rechageAdapter.notifyDataSetChanged();
            }
        });
        etSelf.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    rechageAdapter.getMap().clear();
                    Log.i(TAG, "onFocusChange: "+rechageAdapter.map);
                    rechageAdapter.notifyDataSetChanged();
                }

                return true;
            }
        });
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

    private int layoutPosition;
    int money = 0;
    @OnClick(R.id.btn_recharge)
    public void onViewClicked() {
        if (rechageAdapter.getMap().isEmpty()&&etSelf.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入充值金额", Toast.LENGTH_SHORT).show();
        }else {
            if (rechageAdapter.getMap().isEmpty()) {
                money = Integer.parseInt(etSelf.getText().toString());
            } else {
                money = Integer.parseInt(rechargeList.get(layoutPosition));
            }
            EPay.getInstance(this).pay("校园V商城用户充值", "充值", money * 100,
                    "", "", "", new PayResultListener() {
                        /**
                         * @param context
                         * @param payId   支付精灵支付id
                         * @param orderId   商户系统订单id
                         * @param payUserId 商户系统用户ID
                         * @param payResult
                         * @param payType   支付类型:1 支付宝，2 微信 3 银联
                         * @param amount    支付金额
                         * @see EPayResult#FAIL_CODE
                         * @see EPayResult#SUCCESS_CODE
                         * 1支付成功，2支付失败
                         */
                        @Override
                        public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                             EPayResult payResult, int payType, Integer amount) {
                            EPay.getInstance(context).closePayView();//关闭快捷支付页面
                            if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                                //支付成功逻辑处理
                                Toast.makeText(RechargeActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                            } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                                //支付失败逻辑处理
                                Toast.makeText(RechargeActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    private class RechargeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        private Map<Integer, Boolean> map = new HashMap<>();
        private boolean onBind;
        private int checkedPosition = -1;

        public Map<Integer, Boolean> getMap() {
            return map;
        }

        public void setMap(Map<Integer, Boolean> map) {
            this.map = map;
        }

        private List<String> data = new ArrayList<>();

        public RechargeAdapter(@Nullable List<String> data) {
            super(R.layout.ry_recharge_item, data);
            this.data = data;
        }


        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            RadioButton view = helper.getView(R.id.tv_recharge);
            helper.setText(R.id.tv_recharge, item);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        map.clear();
                        map.put(helper.getPosition(), true);
                        checkedPosition = helper.getPosition();
                        layoutPosition = helper.getLayoutPosition();
//                        Log.i(TAG, "convert: " + attributeSelectBuilder.length());
                    } else {
                        map.remove(helper.getPosition());
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                    }
                    if (!onBind) {
                        notifyDataSetChanged();
                    }
                }
            });
            onBind = true;
            if (map != null && map.containsKey(helper.getPosition())) {
                helper.setChecked(R.id.tv_recharge, true);
                SoftKeyboardUtil.hideSoftKeyboard(RechargeActivity.this);
                etSelf.setText("");
            } else {
                helper.setChecked(R.id.tv_recharge, false);
            }
            onBind = false;
        }
    }


}
