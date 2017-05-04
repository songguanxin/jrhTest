package cn.jrhlive.nestscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * desc:
 * Created by jiarh on 17/3/30 15:33.
 */

public class NestedScrollParentLayout extends LinearLayout implements NestedScrollingParent {

    NestedScrollingParentHelper mNestedPhelper;
    int screenHeight;


    public NestedScrollParentLayout(Context context) {
        super(context);
        initView(context);
    }


    public NestedScrollParentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NestedScrollParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {


        screenHeight = getResources().getDisplayMetrics().heightPixels;
        mNestedPhelper = new NestedScrollingParentHelper(this);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedPhelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedPhelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {


        if ((dy < 0 && ((View) getParent()).getScrollY() <= 0)||(dy > 0 && ((View) getParent()).getScrollY() >= getHeight() - screenHeight)){
            ((View) getParent()).scrollBy(dx, -dy);

            consumed[0] = 0;
            consumed[1] = dy;
        }



    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedPhelper.getNestedScrollAxes();
    }

}
