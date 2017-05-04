package cn.jrhlive.test.testBrowser;

import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class ActivityA extends BaseActivity {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_goto_brower)
    Button btnGotoBrower;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_activity;
    }


    @OnClick(R.id.btn_back)
    public void onClick() {

        finish();
    }


    @OnClick(R.id.btn_goto_brower)
    public void onGoBrowbserClick() {
        Intent intent = new Intent(this,BrowserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
