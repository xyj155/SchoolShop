package com.example.schoolshop.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.view.web.Html5Webview;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebViewActivity extends BaseActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.wb_base)
    Html5Webview webView;

    @Override
    public int intiLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        webView.loadUrl(getIntent().getStringExtra("url"));//加载url
        webView.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
//        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        webSettings.setDisplayZoomControls(false);
        //支持屏幕缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webView.setTitle(new Html5Webview.OnReceiveTitle() {
            @Override
            public void setTitle(String title) {
                initToolBar().setToolbarBackIco().setToolbarSubtitle(title);
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

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);
            if (url.equals("http://www.google.com/")) {
                Toast.makeText(WebViewActivity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };
//
//    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
//    private WebChromeClient webChromeClient = new WebChromeClient() {
//        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
//        @Override
//        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
//            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
//            localBuilder.setMessage(message).setPositiveButton("确定", null);
//            localBuilder.setCancelable(false);
//            localBuilder.create().show();
//
//            //注意:
//            //必须要这一句代码:result.confirm()表示:
//            //处理结果为确定状态同时唤醒WebCore线程
//            //否则不能继续点击按钮
//            result.confirm();
//            return true;
//        }
//
//        //获取网页标题
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            super.onReceivedTitle(view, title);
//            initToolBar().setToolbarBackIco().setToolbarSubtitle(title);
//        }
//
//        //加载进度回调
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//
//        }
//    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * JS调用android的方法
     *
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void getClient(String str) {
        Log.i("ansen", "html调用客户端:" + str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView = null;
    }
}
