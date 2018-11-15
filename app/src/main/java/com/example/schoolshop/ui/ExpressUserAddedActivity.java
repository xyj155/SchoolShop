package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.UserSelfPackageContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.presenter.UserSelfPackagePresenter;
import com.example.schoolshop.view.popwindow.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ExpressUserAddedActivity extends BaseActivity implements UserSelfPackageContract.View {


    private static final int REQUEST_QR_CODE = 0xff;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.et_num)
    EditText etNum;
    @InjectView(R.id.et_cod)
    TextView etCod;
    @InjectView(R.id.et_comment)
    EditText etComment;
    @InjectView(R.id.ll_container)
    LinearLayout llContainer;
    @InjectView(R.id.tv_commit)
    TextView tvCommit;
    @InjectView(R.id.iv_scan)
    ImageView ivScan;
    private UserSelfPackagePresenter userSelfPresent = new UserSelfPackagePresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_express_user_added;
    }

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("提交快递");
    }

    @Override
    public void initData() {

    }

    private String code;

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    private CustomPopWindow customPopWindow;

    private void showPopWindow(View view) {
        customPopWindow = new CustomPopWindow.Builder(ExpressUserAddedActivity.this)
                .setView(R.layout.pop_user_express_information_eit)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CustomPopWindow.ViewInterface() {
                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        ExpressAdapter expressAdapter = new ExpressAdapter(null);
                        RecyclerView ryExpress = view.findViewById(R.id.ry_express);
                        ryExpress.setLayoutManager(new LinearLayoutManager(ExpressUserAddedActivity.this));
                        List<ExpressBean> list = new ArrayList<ExpressBean>();
                        list.add(new ExpressBean("顺丰速运", "SF"));
                        list.add(new ExpressBean("百世快递", "HTKY"));
                        list.add(new ExpressBean("中通快递", "ZTO"));
                        list.add(new ExpressBean("申通快递", "STO"));
                        list.add(new ExpressBean("圆通速递", "YTO"));
                        list.add(new ExpressBean("韵达速递", "YD"));
                        list.add(new ExpressBean("邮政快递包裹", "YZPY"));
                        list.add(new ExpressBean("EMS", "EMS"));
                        list.add(new ExpressBean("天天快递", "HHTT"));
                        list.add(new ExpressBean("京东快递", "JD"));
                        list.add(new ExpressBean("优速快递", "UC"));
                        list.add(new ExpressBean("德邦快递", "DBL"));
                        expressAdapter.replaceData(list);
                        ryExpress.setAdapter(expressAdapter);
                    }
                })
                .setOutsideTouchable(true)
                .create();
        customPopWindow.showAsDropDown(view, 0, 0);
    }

    private class ExpressAdapter extends BaseQuickAdapter<ExpressBean, BaseViewHolder> {

        public ExpressAdapter(@Nullable List<ExpressBean> data) {
            super(R.layout.ry_express_code_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ExpressBean item) {
            helper.setText(R.id.tv_num, item.getCode())
                    .setOnClickListener(R.id.tv_num, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etCod.setText(item.getCode());
                            code = item.getName();
                            customPopWindow.dismiss();
                        }
                    });
        }
    }

    private class ExpressBean {
        private String code;
        private String name;

        public ExpressBean(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
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
        Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess() {
        Toast.makeText(this, "导入成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ExpressUserAddedActivity.this, PostDeliverActivity.class);
        setResult(0, intent);
        finish();
    }

    @Override
    public void loadExpressList(List<PostPackageGson> postPackageGsonList) {

    }

    @OnClick({R.id.et_cod, R.id.tv_commit, R.id.iv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_cod:
                showPopWindow(findViewById(R.id.et_cod));
                break;
            case R.id.tv_commit:
                userSelfPresent.addUserSelfPackage("0", etNum.getText().toString(), code, etCod.getText().toString(), etComment.getText().toString());
                break;
            case R.id.iv_scan:
                startActivityForResult(new Intent(ExpressUserAddedActivity.this, CaptureActivity.class), REQUEST_QR_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            etNum.setText(result);
        }
    }


}
