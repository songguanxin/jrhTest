package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * desc: 取消scrollview 惯性
 * <p>
 * 增加 对scrollview滑动状态的监听 @Date 17/6/29 11:00.
 * Created by jiarh on 17/5/18 09:42.
 */

public class NoFlingScrollView extends HorizontalScrollView {

    private Context context;
    private ScrollViewListenner listenner;
    private NoFlingScrollView currentView;


    private int currentX = Integer.MIN_VALUE;

    /**
     * 滚动状态:
     * IDLE=滚动停止
     * TOUCH_SCROLL=手指拖动滚动
     * FLING=滚动
     */
    public enum ScrollType {
        IDLE,
        TOUCH_SCROLL,
        FLING
    }

    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;


    private Handler mHandler;

    private ScrollTypeListener mScrollTypeListenter;


    public NoFlingScrollView(Context context) {
        super(context);

        init(context);
    }

    public NoFlingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoFlingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NoFlingScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    public void init(Context context) {
        this.context = context;
        mHandler = new Handler();
        mScrollTypeListenter = new ScrollTypeListener() {
            @Override
            public void onScrollChanged(ScrollType scrollType) {

            }
        };

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        currentView = this;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                mScrollTypeListenter.onScrollChanged(scrollType);
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        if (null != listenner) {
            this.listenner.onScrollChanged(currentView, l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (getScrollX() == currentX) {
                //滚动停止,取消监听线程
                scrollType = ScrollType.IDLE;
                if (mScrollTypeListenter != null) {
                    mScrollTypeListenter.onScrollChanged(scrollType);
                }
                mHandler.removeCallbacks(this);
                return;
            } else {
                //手指离开屏幕,但是view还在滚动
                scrollType = ScrollType.FLING;
                if (mScrollTypeListenter != null) {
                    mScrollTypeListenter.onScrollChanged(scrollType);
                }
            }
            currentX = getScrollX();
            //滚动监听间隔:milliseconds
            mHandler.postDelayed(this, 50);
        }
    };


    public interface ScrollViewListenner {
        void onScrollChanged(NoFlingScrollView view, int l, int t, int oldl, int oldt);
    }

    public interface ScrollTypeListener {
        void onScrollChanged(ScrollType scrollType);
    }

    public void setScrollViewListenner(ScrollViewListenner listenner) {
        this.listenner = listenner;
    }

    public void setScrollTypeListenter(ScrollTypeListener scrollTypeListenter) {
        this.mScrollTypeListenter = scrollTypeListenter;
    }
/**
 *
 *阻尼：1000为将惯性滚动速度缩小1000倍，近似drag操作。
 *
 */
//     @Override public void fling(int velocity) {
//     super.fling(velocity / 100);
//     }

}
