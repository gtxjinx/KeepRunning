package com.example.administrator.keeprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/5.
 */

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String url="http://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init_webview();
    }
    public void init_webview()
    {
        webView= (WebView) findViewById(R.id.webView1);
        Intent intent=getIntent();
        Bundle bundle =intent.getBundleExtra("key");
        if (bundle.getString("text1").contentEquals("百度经验"))
        {
            url="http://jingyan.baidu.com/article/72ee561a5abb4ce16038df6c.html";
        }
        else if (bundle.getString("text1").contentEquals("运动常识"))
        {
            url="http://www.360doc.com/content/13/1210/16/6085160_336105143.shtml";
        }
        else if (bundle.getString("text1").contentEquals("你真的会跑步吗？"))
        {
            url="http://tech.ifeng.com/a/20150908/41470894_0.shtml";
        }
        else if (bundle.getString("text1").contentEquals("知乎"))
        {
            url="https://www.zhihu.com/question/34107060";
        }
        else if (bundle.getString("text1").contentEquals("冷知识"))
        {
            url="http://sports.sohu.com/20161216/n476085906.shtml";
        }
        else if (bundle.getString("text1").contentEquals("给越野跑者的备忘录"))
        {
            url="http://sports.sina.com.cn/run/2016-12-15/doc-ifxytqax6045780.shtml?_t_t_t=0.8354679294861853";
        }
        else if (bundle.getString("text1").contentEquals("跑步指南"))
        {
            url="http://weibo.com/nbrunner?refer_flag=1001030102_&is_hot=1";
        }
        else if (bundle.getString("text1").contentEquals("微博热文"))
        {
            url="http://s.weibo.com/list/relpage?search=%E8%B7%91%E6%AD%A5";
        }
        else if (bundle.getString("text1").contentEquals("爱上跑步的13周")) {
            url = "http://www.360doc.com/content/14/0705/11/8519945_392154707.shtml";
        }
        else
        {
            Toast.makeText(WebActivity.this, bundle.getString("text1"), Toast.LENGTH_SHORT).show();
        }
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack())
            {
                webView.goBack();
                return true;
            }
            else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
