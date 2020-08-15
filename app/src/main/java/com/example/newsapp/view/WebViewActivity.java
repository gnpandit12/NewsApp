package com.example.newsapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.WebViewClientImpl;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String newsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        newsUrl = getIntent().getStringExtra("news_url");
        webView = findViewById(R.id.web_view_layout);
        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);

        if (!TextUtils.isEmpty(newsUrl)){
            webView.loadUrl(newsUrl);
        }else {
            Toast.makeText(this, "Url Empty!", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}