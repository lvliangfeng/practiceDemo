package com.zhongyujiaoyu.swiprefreshlayout.webview;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.zhongyujiaoyu.swiprefreshlayout.R;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAndroidCallJs;
    private WebView webView;
    private Button btnLoadNetPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
        initWebView();
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/javascript.html");
        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
//                builder.setTitle("Alert");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                builder.setCancelable(false);
//                builder.create().show();
//                return true;
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.addJavascriptInterface(new JSInterface(), "jsi");
    }

    private void initView() {
        btnAndroidCallJs = (Button) findViewById(R.id.android_call_js);
        btnLoadNetPage = (Button) findViewById(R.id.load_network_page);
        webView = (WebView) findViewById(R.id.webview);
        btnAndroidCallJs.setOnClickListener(this);
        btnLoadNetPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.android_call_js:
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadUrl("javascript:callJs()");
//                    }
//                });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //效率高，因为执行该方法不会刷新页面，Android 4.4 后才能使用
                    webView.evaluateJavascript("javascript:androidCallJs()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Toast.makeText(WebViewActivity.this, value, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    webView.loadUrl("javascript:androidCallJs()");
                }
                break;
            case R.id.load_network_page:
                WebView netWebView = new WebView(this);
                netWebView.loadUrl("http://www.baidu.com");
                setContentView(netWebView);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    private final class JSInterface {
        
        @JavascriptInterface
        public void showToast(String text) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }

    }
}
