package cn.jrhlive.splash;


import com.jrhlibrary.utils.ActivityUtils;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.main.test.ui.LoginActivity;
import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.pvView)
    ParticleView pvView;

    @Override
    protected void initEvent() {

        pvView.startAnim();
        pvView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                ActivityUtils.startActivity(SplashActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initView() {


        setSwipeBackEnable(false);

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_splash;
    }



}
