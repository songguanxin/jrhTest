package cn.jrhlive.main.ui;

import android.os.Handler;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class DoubleClickTestActivity extends BaseActivity {


    @BindView(R.id.btn_click)
    Button btnClick;

    private boolean mFlag;
    int i=0;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_double_click_test;
    }


    @OnClick(R.id.btn_click)
    public void onClick() {
        i++;
        if (!mFlag) {
            mFlag = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFlag = false;
                    System.out.println("*********************"+i+"");
                }
            },3000);
        }
    }

    Handler mHandler = new Handler();
}
