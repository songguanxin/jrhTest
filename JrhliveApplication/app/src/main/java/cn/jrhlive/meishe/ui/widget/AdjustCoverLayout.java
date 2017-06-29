package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * desc:
 * Created by jiarh on 17/5/18 15:52.
 */

public class AdjustCoverLayout extends FrameLayout {


    private long currentPoint;
    private long inPoint;
    private long outPoint;



    public AdjustCoverLayout(@NonNull Context context) {
        super(context);
    }

    public AdjustCoverLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustCoverLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AdjustCoverLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(AdjustView adjustView,long currentPoint,long in,long out){
        addView(adjustView);
        addCover(adjustView);
        this.currentPoint = currentPoint;
        this.inPoint = in;
        this.outPoint = out;
    }

    private void addCover(AdjustView adjustView) {
        CoverView coverView  = new CoverView(getContext());
        LayoutParams params = new LayoutParams(adjustView.getWidth(),adjustView.getHeight());
        addView(coverView,params);
    }

    public void  refreshCoverView(AdjustView adjustView){
        removeViewAt(1);
        addCover(adjustView);
    }
    public void showAdjustView(boolean visiable){
        AdjustView adjustView = (AdjustView) getChildAt(0);
        if (visiable){
            adjustView.setVisibility(VISIBLE);

        }else {
            adjustView.setVisibility(INVISIBLE);
        }
    }

    public void showCoverView(boolean visiable){
        CoverView coverView = (CoverView) getChildAt(1);

        if (visiable){
            coverView.setVisibility(VISIBLE);
        }else {
            coverView.setVisibility(INVISIBLE);
        }
    }


    public void toggle(long current){
        if (current<outPoint&&current>inPoint){
            showAdjustView(true);
            showCoverView(false);
        }else {
            showAdjustView(false);
            showCoverView(true);
        }
    }


}
