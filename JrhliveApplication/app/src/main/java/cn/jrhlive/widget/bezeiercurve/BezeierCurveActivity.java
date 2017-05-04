package cn.jrhlive.widget.bezeiercurve;

import android.graphics.Point;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class BezeierCurveActivity extends BaseActivity {

    @BindView(R.id.bv)
    BezeierView bv;
    @BindView(R.id.sb_anchor2_y)
    SeekBar sbAnchor2Y;
    @BindView(R.id.tv_anchor2_y)
    TextView tvAnchor2Y;
    @BindView(R.id.sb_anchor2_x)
    SeekBar sbAnchor2X;
    @BindView(R.id.tv_anchor2_x)
    TextView tvAnchor2X;
    @BindView(R.id.sb_anchor1_y)
    SeekBar sbAnchor1Y;
    @BindView(R.id.tv_anchor1_y)
    TextView tvAnchor1Y;
    @BindView(R.id.sb_anchor1_x)
    SeekBar sbAnchor1X;
    @BindView(R.id.tv_anchor1_x)
    TextView tvAnchor1X;

    Point mStartPoint,mEndPoint,mAnchorPoint1,mAnchorPoint2;
    /**
     * 距离调解
     */
    private static  final int DIS=30;
    private int ax1,ay1,ax2,ay2;

    @Override
    protected void initEvent() {

        setSwipeBackEnable(false);


        sbAnchor1X.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAnchorPoint1.set(progress,mAnchorPoint1.y);
                refresh();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAnchor1Y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAnchorPoint1.set(mAnchorPoint1.x,progress);
                refresh();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAnchor2X.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAnchorPoint2.set(progress,mAnchorPoint2.y);
                refresh();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAnchor2Y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAnchorPoint2.set(mAnchorPoint2.x,progress);
                refresh();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void refresh() {
        bv.refreshView(mStartPoint,mEndPoint,mAnchorPoint1,mAnchorPoint2);
    }

    @Override
    protected void initView() {


        mAnchorPoint1 = new Point();
        mAnchorPoint2 = new Point();
        bv.post(new Runnable() {
            @Override
            public void run() {

                mStartPoint = new Point(bv.getLeft()+ DIS,bv.getTop()+DIS);
                mEndPoint = new Point(bv.getRight()-2*DIS,bv.getBottom()-2*DIS);
                sbAnchor1X.setMax(bv.getWidth());
                sbAnchor2X.setMax(bv.getWidth());
                sbAnchor1Y.setMax(bv.getHeight());
                sbAnchor2Y.setMax(bv.getHeight());
            }
        });
    }


    @Override
    protected int getViewId() {
        return R.layout.activity_bezeier_curve;
    }


}
