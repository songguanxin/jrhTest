package cn.jrhlive.surfaceview;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SurfaceViewActivity extends BaseActivity {

    @BindView(R.id.content_lay)
    LinearLayout contentLay;
    @BindView(R.id.btn_toggle)
    Button btnToggle;
    @BindView(R.id.video_lay)
    RelativeLayout videoLay;
    @BindView(R.id.activity_surface_view)
    FrameLayout activitySurfaceView;

    boolean isOpen;
    ViewGroup.LayoutParams layoutParams;
    int w, h;
    private static final String TAG = "SurfaceViewActivity";

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        videoLay.post(new Runnable() {
            @Override
            public void run() {
                w = videoLay.getWidth();
                h = videoLay.getHeight();
                layoutParams = videoLay.getLayoutParams();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) contentLay.getLayoutParams();
                lp.topMargin = h;
                contentLay.setLayoutParams(lp);

                Log.d(TAG, "run() called");
            }
        });

    }

    @OnClick(R.id.btn_toggle)
    public void onClick() {

        if (!isOpen) {
            layoutParams.height = getResources().getDisplayMetrics().widthPixels;
            layoutParams.width = getResources().getDisplayMetrics().heightPixels;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            layoutParams.height = h;
            layoutParams.width =w;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        videoLay.setLayoutParams(layoutParams);
        isOpen = !isOpen;

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_surface_view;
    }


}
