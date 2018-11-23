package com.example.schoolshop.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.RunHelperContract;
import com.example.schoolshop.gson.RunHelperGson;
import com.example.schoolshop.presenter.RunHelperPresenter;
import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ConversationActivity extends BaseActivity implements RunHelperContract.View {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    private RunHelperPresenter runHelperPresenter = new RunHelperPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        String title = getIntent().getData().getQueryParameter("title");
        if (title.contains("--店")){
            tvSubmit.setVisibility(View.GONE);
            initToolBar().setToolbarBackIco().setToolbarSubtitle( "和" + getIntent().getData().getQueryParameter("title").replace("--店","")+ "对话中..");
        }else {
            initToolBar().setToolbarBackIco().setToolbarSubtitle( "和" + getIntent().getData().getQueryParameter("title")+ "对话中..");
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

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        EPay.getInstance(this).pay("校园V商城", "跑腿", 7*100,
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
                            Toast.makeText(ConversationActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                            runHelperPresenter.submitRunHelperOrder(getSharedPreferences("user", MODE_PRIVATE).getString("id", ""), getIntent().getData().getQueryParameter("title"),"0");
                        } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                            //支付失败逻辑处理
                            Toast.makeText(ConversationActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dialogClose();
    }

    @Override
    public void loadFailed(String msg) {
        runHelperPresenter.submitOrder(getSharedPreferences("user", MODE_PRIVATE).getString("id", ""), getIntent().getData().getQueryParameter("title"),"1");

    }

    @Override
    public void loadHelperList(List<RunHelperGson> runHelperGsons) {

    }

    @Override
    public void success() {
        runHelperPresenter.submitOrder(getSharedPreferences("user", MODE_PRIVATE).getString("id", ""), getIntent().getData().getQueryParameter("title"),"2");

    }

    @Override
    public void setOrderSuccess() {
        Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOrderFailed() {
        Toast.makeText(this, "下单失败", Toast.LENGTH_SHORT).show();
    }
}
