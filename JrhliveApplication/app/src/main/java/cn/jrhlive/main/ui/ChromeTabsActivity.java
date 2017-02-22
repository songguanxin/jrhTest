package cn.jrhlive.main.ui;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.widget.Button;

import com.jrhlibrary.customtabs.CustomTabActivityHelper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class ChromeTabsActivity extends BaseActivity {

    @BindView(R.id.btn_chrome)
    Button btnChrome;


    String  mUrl = "http://www.baidu.com";

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_chrome_tabs;
    }


    @OnClick(R.id.btn_chrome)
    public void onClick() {


        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();

        CustomTabActivityHelper.openCustomTab(this,customTabsIntent, Uri.parse(mUrl),new WebviewFallback());

    }
}
