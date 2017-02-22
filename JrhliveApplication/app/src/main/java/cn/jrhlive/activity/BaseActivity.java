package cn.jrhlive.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jrhlibrary.widgets.LoadingDialog;

import butterknife.ButterKnife;
import cn.jrhlive.R;
import cn.jrhlive.basemvp.BaseView;
import cn.jrhlive.utils.ToastUtil;
import cn.jrhlive.widget.swip.SwipeBackBaseActivity;
import cn.jrhlive.widget.toolbar.ToolBarHelper;

public abstract class BaseActivity extends SwipeBackBaseActivity implements BaseView{


    LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);
        initViewData();
        initView();
        initEvent();

    }

    private void initViewData() {
        Toolbar toobar = (Toolbar) findViewById(R.id.toolbar);
        ToolBarHelper.init(this,toobar);

    }

    protected abstract void initEvent();
    protected abstract void initView();

    protected abstract int getViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showDialog(){
        if (mLoadingDialog==null){
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    public void closeDialog(){
        if (mLoadingDialog!=null)
            mLoadingDialog.dismiss();
    }

    @Override
    public void showProgress() {

        showDialog();
    }

    @Override
    public void hideProgress() {

        hideProgress();
    }

    @Override
    public void showMsg(String msg) {

        ToastUtil.showMessage(msg);
    }
}
