package cn.jrhlive.nestscrolling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * desc:
 * Created by jiarh on 17/3/30 15:40.
 */

public class NestedScrollChildView extends View implements NestedScrollingChild {

    NestedScrollingChildHelper mNestedSchildHelper;

    private int[] mConsumed = new int[2];

    private int[] mOffset = new int[2];



    public NestedScrollChildView(Context context) {
        this(context, null);
    }


    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        mNestedSchildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);


    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedSchildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedSchildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedSchildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedSchildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedSchildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {

        return mNestedSchildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedSchildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedSchildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedSchildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    int lastPositionY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastPositionY = (int) event.getRawY();

                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:

                int py = (int) event.getRawY();
                int deltaY = py - lastPositionY;
                lastPositionY = py;

                if (dispatchNestedPreScroll(0, deltaY, mConsumed, mOffset)) {

                } else {
                    if ((deltaY < 0 && ((View) getParent()).getScrollY() <= 0) || (deltaY > 0 && ((View) getParent()).getScrollY() > getHeight() - ((View) getParent()).getHeight()))
                        ((View) getParent()).scrollBy(0, -deltaY);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }

}
