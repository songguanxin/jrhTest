package cn.jrhlive.meishe.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jrhlive.R;

@SuppressLint("NewApi")
public class AdjustView1 extends View {

    int lastX;
    int cursorPosition;
    float cursorFloat;
    private int m_imageWidth = 18;
    private boolean isClicked = true;
    private int  mBoundary = 10;
    LinearLayout layout;
    ImageView imageLeft;
    ImageView imageRight;
    ImageView space;
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private TextView captionView;
    private boolean isDragLeft = false;
    private boolean isDragRight = false;


    private OnTrimInChangeListener onTrimInChangeListener;
    private OnTrimOutChangeListener onTrimOutChangeListener;

    public AdjustView1(Context context) {
        super(context);
        bitmapLeft = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        bitmapRight = BitmapFactory.decodeResource(getResources(), R.drawable.right);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLUE);


        RectF rectLeft = new RectF(getCursorPosition(), 0, getCursorPosition()+m_imageWidth, getHeight());
        canvas.drawBitmap(bitmapLeft, null, rectLeft, p);

        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(m_imageWidth+getCursorPosition(), 0, m_imageWidth+getCursorPosition()+getBoundary(), getHeight(), p);

        p.setColor(Color.BLUE);
        RectF rectRight = new RectF(getCursorPosition()+m_imageWidth+getBoundary(), 0, getBoundary()+getCursorPosition()+2*m_imageWidth, getHeight());
        canvas.drawBitmap(bitmapRight, null, rectRight, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(ev.getX() < m_imageWidth  && ev.getY() < getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //左边
                    lastX = (int) ev.getRawX();
                    isDragLeft = true;
                }
                if(getWidth() - ev.getX() < m_imageWidth  && ev.getY() < getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //右边
                    lastX = (int) ev.getRawX();
                    isDragRight = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDragLeft) {
                    int dx = (int) ev.getRawX() - lastX;

                    int left = getLeft() + dx;
                    if (left < 0) {
                        left = 0;
                    }
                    lastX = (int) ev.getRawX();
                    onTrimInChangeListener.onChange(left);
                    layout(left, getTop(), getRight() , getBottom());
                }

                if(isDragRight) {
                    int dx = (int) ev.getRawX() - lastX;
                    int right = getRight() + dx;
                    lastX = (int) ev.getRawX();
                    onTrimOutChangeListener.onChange(right);
                    layout(getLeft(), getTop(), right , getBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                isDragLeft = false;
                isDragRight = false;

        }
        return true;
    }


    public int getBoundary() {
        if (mBoundary>getWidth()-m_imageWidth)
            return mBoundary;
        if (mBoundary<m_imageWidth)
            return m_imageWidth;
        return mBoundary;
    }

    public void setBoundary(int mBoundary) {
        this.mBoundary = mBoundary;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public void setOnChangeListener(OnTrimInChangeListener onChangeListener){
        this.onTrimInChangeListener = onChangeListener;
    }

    public void setOnChangeListener(OnTrimOutChangeListener onChangeListener){
        this.onTrimOutChangeListener = onChangeListener;
    }


    public int getCursorPosition() {
        if (cursorFloat!=0){
            return (int)(getWidth()*cursorFloat);
        }
        return cursorPosition;
    }

    public void setCursorPosition(int cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public interface OnTrimInChangeListener {
        public void onChange(int to);
    }

    public interface OnTrimOutChangeListener {
        public void onChange(int to);
    }

    public float getCursorFloat() {
        return cursorFloat;
    }

    public void setCursorFloat(float cursorFloat) {
        this.cursorFloat = cursorFloat;
    }
}
