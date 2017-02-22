package cn.jrhlive.main.test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.main.test.LoginNet;
import cn.jrhlive.main.test.LoginPresenterImp;
import cn.jrhlive.main.test.LoginView;
import cn.jrhlive.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.psd)
    EditText psd;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {

        LoginPresenterImp loginPresenterImp = new LoginPresenterImp(new LoginNet(),this);
        loginPresenterImp.login(name.getText().toString(),psd.getText().toString());


    }

    @Override
    public void showProgress() {

        ToastUtil.showMessage("显示dialog");
    }

    @Override
    public void hideProgress() {

        ToastUtil.showMessage("隐藏Dialog");
    }

    @Override
    public void isloginSuccess(boolean isSuccess) {

        if (isSuccess){
            ToastUtil.showMessage("跳到下个界面");
        }else {
            ToastUtil.showMessage("登录失败");
            name.setText("");
        }
    }
}
