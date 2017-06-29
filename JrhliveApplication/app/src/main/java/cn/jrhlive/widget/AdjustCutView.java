package cn.jrhlive.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jrhlive.R;

/**
 * desc:
 * Created by jiarh on 17/6/20 16:08.
 */

public class AdjustCutView extends View {

    private int mColor = Color.parseColor("#f03c38");
    private Bitmap ivLeft;
    private Bitmap ivRight;

    private int mIvWidth;
    private int lastX;
    private boolean isDragLeft;
    private boolean isDragRight;

    private OnTrimInChangeListener onTrimInChangeListener;
    private OnTrimOutChangeListener onTrimOutChangeListener;

    int leftMargin = 300;
    int rightMargin = 300;

    public AdjustCutView(Context context) {
        this(context, null);
    }

    public AdjustCutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdjustCutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        ivLeft = BitmapFactory.decodeResource(getResources(), R.drawable.scoller);
        ivRight = BitmapFactory.decodeResource(getResources(), R.drawable.scoller);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mIvWidth = ivLeft.getWidth();

        drawDrag(canvas);

    }

    private void drawDrag(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(mColor);

        RectF rectLeft = new RectF(0, 0, mIvWidth, getHeight());
        canvas.drawBitmap(ivLeft, null, rectLeft, p);

        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mIvWidth, 0, getWidth() - mIvWidth, getHeight(), p);

        p.setColor(Color.BLUE);
        RectF rectRight = new RectF(getWidth() - mIvWidth, 0, getWidth(), getHeight());
        canvas.drawBitmap(ivRight, null, rectRight, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        leftMargin = 0;
        rightMargin = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (ev.getX() < mIvWidth && ev.getY() < getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //左边
                    lastX = (int) ev.getRawX();
                    isDragLeft = true;
                }
                if (getWidth() - ev.getX() < mIvWidth && ev.getY() < getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //右边
                    lastX = (int) ev.getRawX();
                    isDragRight = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragLeft) {
                    int dx = (int) ev.getRawX() - lastX;

                    int left = getLeft() + dx;
                    if (left < 0) {
                        left = 0;
                    }
                    lastX = (int) ev.getRawX();
                    if (onTrimInChangeListener != null)
                        onTrimInChangeListener.onChange(left);

                    layout(left, getTop(), getRight(), getBottom());

                }

                if (isDragRight) {
                    int dx = (int) ev.getRawX() - lastX;
                    int right = getRight() + dx;
                    lastX = (int) ev.getRawX();
                    if (onTrimOutChangeListener != null)
                        onTrimOutChangeListener.onChange(right);
                    layout(getLeft(), getTop(), right, getBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                isDragLeft = false;
                isDragRight = false;

        }
        return true;
    }


    @Override
    public void layout(@Px int l, @Px int t, @Px int r, @Px int b) {
        l = l + leftMargin;
        r = r - rightMargin;
        super.layout(l, t, r, b);

    }

    public void setOnChangeListener(OnTrimInChangeListener onChangeListener) {
        this.onTrimInChangeListener = onChangeListener;
    }

    public void setOnChangeListener(OnTrimOutChangeListener onChangeListener) {
        this.onTrimOutChangeListener = onChangeListener;
    }


    public interface OnTrimInChangeListener {
        public void onChange(int to);
    }

    public interface OnTrimOutChangeListener {
        public void onChange(int to);
    }

}
