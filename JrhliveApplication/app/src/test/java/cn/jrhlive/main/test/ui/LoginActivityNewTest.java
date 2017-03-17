package cn.jrhlive.main.test.ui;

import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import cn.jrhlive.BuildConfig;
import cn.jrhlive.R;

/**
 * desc:
 * Created by jiarh on 17/3/14 15:32.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityNewTest {

    @Test
    public void testLogin(){

        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.findViewById(R.id.btn_login).performClick();
        TextView name = (TextView) activity.findViewById(R.id.name);
        Assert.assertEquals("androids",name.getText().toString());
    }
}