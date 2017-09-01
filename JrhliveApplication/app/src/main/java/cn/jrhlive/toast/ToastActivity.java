package cn.jrhlive.toast;

import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.utils.ToastUtil;


public class ToastActivity extends BaseActivity {


    @BindView(R.id.btn_show)
    Button btnShow;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_toast;
    }


    @OnClick(R.id.btn_show)
    public void onViewClicked() {
        ToastUtil.showMessage("hello 1111");
        EToast.makeText(this,"haha",0).show();
    }
}
