package cn.jrhlive.main.ui;

import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class WebViewActivity extends BaseActivity {
    public static final String EXTRA_URL = "extra.url";

    @Override
    protected void initEvent() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setTitle(url);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_web_views;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
