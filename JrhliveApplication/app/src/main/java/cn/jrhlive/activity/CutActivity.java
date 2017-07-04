package cn.jrhlive.activity;


import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jrhlibrary.utils.Mobile;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.meishe.ui.widget.CutView;
import cn.jrhlive.meishe.ui.widget.NoFlingScrollView;
import cn.jrhlive.meishe.widget.DrawRect;
import cn.jrhlive.utils.ToastUtil;

public class CutActivity extends BaseActivity {

    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.hzscrollview)
    NoFlingScrollView hzscrollview;

    int mRatio = 100;
    @BindView(R.id.framelayout_in_sc)
    FrameLayout framelayoutInSc;

    private static final String TAG = "CutActivity";

    @Override
    protected void initEvent() {


    }

    @Override
    protected void initView() {

        setSwipeBackEnable(false);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(Mobile.SCREEN_WIDTH + mRatio * 15, ViewGroup.LayoutParams.MATCH_PARENT);
        framelayoutInSc.setLayoutParams(params);

        final CutView cutView = new CutView(this);
        cutView.setEditMode(true);
        cutView.invalidate();

        framelayoutInSc.addView(cutView, params);


        frameContainer.post(new Runnable() {
            @Override
            public void run() {
                DrawRect drawRect = new DrawRect(CutActivity.this, frameContainer.getWidth() / 2, frameContainer.getHeight() / 2, frameContainer.getWidth() / 2 + 100, frameContainer.getHeight() / 2 + 50);
                frameContainer.addView(drawRect);
                drawRect.setOnDeleteClickListener(new DrawRect.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClicked() {
                        ToastUtil.showMessage("delete");
                    }
                });
            }
        });

        hzscrollview.setScrollViewListenner(new NoFlingScrollView.ScrollViewListenner() {
            @Override
            public void onScrollChanged(NoFlingScrollView view, int l, int t, int oldl, int oldt) {

            }
        });
        hzscrollview.setScrollTypeListenter(new NoFlingScrollView.ScrollTypeListener() {
            @Override
            public void onScrollChanged(NoFlingScrollView.ScrollType scrollType) {

                switch (scrollType){
                    case TOUCH_SCROLL:
                        cutView.setEditMode(false);
                        break;
                    case FLING:
                        cutView.setEditMode(false);
                        break;
                    case IDLE:
                        cutView.setEditMode(true);
                        break;
                }

            }
        });


    }

    @Override
    protected int getViewId() {
        return R.layout.activity_cut;
    }


}
