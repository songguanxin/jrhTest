package cn.jrhlive.main.test.ui;

import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import cn.jrhlive.R;

/**
 * desc:
 * Created by jiarh on 17/3/8 15:15.
 */
@RunWith(RobolectricTestRunner.class)
public class LoginActivityRobolicTest {

    @Test
    public void testClick(){

        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        Button  btn  = (Button) loginActivity.findViewById(R.id.btn_login);
        btn.performClick();

    }
}