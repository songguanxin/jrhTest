package cn.jrhlive.main.test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jrhlibrary.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.main.test.LoginNet;
import cn.jrhlive.main.test.LoginPresenterImp;
import cn.jrhlive.main.test.LoginView;
import cn.jrhlive.main.ui.MainActivity;
import cn.jrhlive.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.psd)
    EditText psd;
    @BindView(R.id.btn_login)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        name.setText("android");
        psd.setText("123456");
    }

    @OnClick(R.id.btn_login)
    public void onClick() {

        LoginPresenterImp loginPresenterImp = new LoginPresenterImp(new LoginNet(),this);
        loginPresenterImp.login(name.getText().toString(),psd.getText().toString());


    }

    @Override
    public void showProgress() {

//        ToastUtil.showMessage("显示dialog");
    }

    @Override
    public void hideProgress() {

//        ToastUtil.showMessage("隐藏Dialog");
    }

    @Override
    public void isloginSuccess(boolean isSuccess) {

        if (isSuccess){
            ActivityUtils.startActivity(this, MainActivity.class);
            finish();
        }else {
            ToastUtil.showMessage("登录失败");
            name.setText("");
        }
    }
}
