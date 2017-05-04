package cn.jrhlive.test.testBrowser;

import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class BrowserActivity extends BaseActivity {


    @BindView(R.id.btn_backToA)
    Button btnBackToA;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_browser;
    }

    @OnClick(R.id.btn_backToA)
    public void onClick() {
        Intent intent = new Intent(this,ActivityA.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
