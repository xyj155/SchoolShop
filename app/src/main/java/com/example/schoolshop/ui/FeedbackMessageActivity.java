package com.example.schoolshop.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.FeedBackContract;
import com.example.schoolshop.presenter.FeedBackPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FeedbackMessageActivity extends BaseActivity implements FeedBackContract.View {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.btn_submit)
    Button btnSubmit;
    private FeedBackPresenter feedBackPresenter = new FeedBackPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_feedback_message;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("反馈");
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
    public void submitSuccess() {
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (etContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
        } else {
            feedBackPresenter.Userfeedback("1", etContent.getText().toString());
        }
    }
}
