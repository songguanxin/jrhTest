package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jrhlibrary.utils.Mobile;

import cn.jrhlive.R;


/**
 * desc: 视频裁剪view && 添加字幕
 *
 * <p>
 * Created by jiarh on 17/5/24 13:44.
 */

public class CutView extends View {


    private Paint mPaint;
    private int mColor = Color.parseColor("#f03c38");
    private int mColorCover = Color.parseColor("#cc000000");

    private Bitmap ivLeft;
    private Bitmap ivRight;


    private int ivWidth;

    private int mScreenWidth = Mobile.SCREEN_WIDTH;
    private int mStrokeWidth = 5;

    private float lastX;
    private float dxLeft, dxRight;
    /**
     * 初始化左侧宽度
     */
    private float initLeft;

    private boolean isDragLeft, isDragRight;
    /**
     * 默认初始宽度
     */
    private float mDefaultWidth = mScreenWidth;
    /**
     * 最小宽度
     */
    private float minWidth = 30;
    private int mHeight;

    private OnTrimInChangeListener onTrimInChangeListener;
    private OnTrimOutChangeListener onTrimOutChangeListener;
    private OnDragListener onDragListener;


    private RectF rectRight;
    private RectF rectLeft;

    private RectF midRect;
    /**
     * 是否画两侧的阴影
     */
    private boolean isDrawLeftRectAndRightRect = true;

    private boolean isDrawShadow;
    /**
     * 是否是编辑状态
     */
    private boolean isEditMode;

    public CutView(Context context) {
        this(context, null);
    }

    public CutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
        init();
    }

    private void initBitmap() {

        ivLeft = BitmapFactory.decodeResource(getResources(), R.drawable.scoller);
        ivRight = BitmapFactory.decodeResource(getResources(), R.drawable.scoller);

        ivWidth = ivLeft.getWidth();

    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight = getHeight();

        if ((mDefaultWidth + ivWidth) >= getWidth())
            mDefaultWidth = getWidth();

        drawIv(canvas);

    }

    private void drawIv(Canvas canvas) {
        resetRects();
        if (isEditMode) {
            drawLeftIv(canvas);
            drawLeftRect(canvas);
            drawMidRect(canvas, false);
            drawRightIv(canvas);
            drawRightRect(canvas);
        } else {
            drawMidRect(canvas, true);
            return;
        }


    }

    private void drawRightRect(Canvas canvas) {
        if (isDrawLeftRectAndRightRect) {
            mPaint.setColor(mColorCover);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectRight.right, 0, getWidth(), mHeight, mPaint);
    }

    private void drawLeftRect(Canvas canvas) {
        if (isDrawLeftRectAndRightRect) {
            mPaint.setColor(mColorCover);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (rectLeft != null)
            canvas.drawRect(0, 0, rectLeft.left, mHeight, mPaint);
    }


    private void drawRightIv(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        canvas.drawBitmap(ivRight, null, rectRight, mPaint);

    }

    /**
     * @param canvas
     * @param isCover 是否单纯绘制封面
     */
    private void drawMidRect(Canvas canvas, boolean isCover) {

        if (!isCover) {
            //绘制拖动区域阴影
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.fourty_percent_black));
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(midRect, mPaint);

            if (isDrawShadow) {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorCover);
            } else {
                mPaint.setColor(mColor);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            canvas.drawRect(midRect, mPaint);

        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColor);
            mPaint.setStrokeWidth(10);
            canvas.drawLines(new float[]{midRect.left, midRect.top, midRect.left, midRect.bottom,
                    midRect.right, midRect.top, midRect.right, midRect.bottom}, mPaint);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.light_red));
            canvas.drawRect(midRect, mPaint);

        }


    }

    private void drawLeftIv(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        canvas.drawBitmap(ivLeft, null, rectLeft, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:


                if (isEditMode && (ev.getX() >= rectLeft.left && ev.getX() <= rectLeft.right) && ev.getY() < getHeight()) {

                    getParent().requestDisallowInterceptTouchEvent(true);
                    //左边
                    lastX = (int) ev.getRawX();
                    isDragLeft = true;

                }
                if (isEditMode && (ev.getX() >= rectRight.left && ev.getX() <= rectRight.right) && ev.getY() < getHeight()) {

                    getParent().requestDisallowInterceptTouchEvent(true);
                    //右边
                    lastX = (int) ev.getRawX();
                    isDragRight = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (isDragLeft) {
                    float dx = ev.getRawX() - lastX;
                    dxLeft += dx;

                    //rectLeft.right > rectRight.left-miniWidth

                    if (dxLeft + initLeft < 0) {
                        dxLeft = 0 - initLeft;
                    }
                    if (dxLeft + initLeft + ivWidth > rectRight.left-minWidth) {
                        dxLeft = rectRight.left-minWidth - initLeft - ivWidth;

                    }

                    if (midRect.left < midRect.right){
                        //对外传值要减去左侧预留ivWidth midRect.left-ivWidth
                        if (onTrimInChangeListener != null)
                            onTrimInChangeListener.onChange(initLeft + dxLeft);
                    }

                }

                if (isDragRight) {
                    float dx = ev.getRawX() - lastX;
                    dxRight += dx;

                    //控制右侧滑出边界
                    if (initLeft + mDefaultWidth + dxRight + 2 * ivWidth > getWidth()) {
                        dxRight = getWidth() - initLeft - mDefaultWidth - 2 * ivWidth;
                    }

                    //midRect.right<rectLeft.right+minWidth;

                    if (mDefaultWidth + initLeft + dxRight + ivWidth < rectLeft.right+minWidth) {
                        dxRight = rectLeft.right+minWidth - mDefaultWidth - initLeft - ivWidth;
                    }


                    if (onTrimOutChangeListener != null) {
                        //对外传值要减去左侧预留ivWidth midRect.right-ivWidth
                        onTrimOutChangeListener.onChange(initLeft + mDefaultWidth + dxRight);
                    }
                }
                invalidate();
                lastX = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (onDragListener!=null){
                    if (isDragRight){
                        onDragListener.dragover(midRect.right-ivWidth);
                    }

                    if (isDragLeft){
                        onDragListener.dragover(midRect.left-ivWidth);
                    }
                }
                isDragLeft = false;
                isDragRight = false;


        }
        return true;
    }

    private void resetRects() {

        rectLeft = new RectF(initLeft + dxLeft, 0, initLeft + dxLeft + ivWidth, mHeight);
        midRect = new RectF(rectLeft.right, 0, initLeft + mDefaultWidth + dxRight + ivWidth, mHeight);
        rectRight = new RectF(midRect.right, 0, midRect.right + ivWidth, mHeight);
    }


    public void setOnChangeListener(OnTrimInChangeListener onChangeListener) {
        this.onTrimInChangeListener = onChangeListener;
    }

    public void setOnChangeListener(OnTrimOutChangeListener onChangeListener) {
        this.onTrimOutChangeListener = onChangeListener;
    }


    /**
     * 字幕入点回调
     */
    public interface OnTrimInChangeListener {
         void onChange(float to);
    }

    /**
     * 字幕出点回调
     */
    public interface OnTrimOutChangeListener {
         void onChange(float to);
    }

    /**
     * 实时位置回调
     */
    public interface  OnDragListener{
        void dragover(float to);
    }
    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public int getmColorCover() {
        return mColorCover;
    }

    public void setmColorCover(int mColorCover) {
        this.mColorCover = mColorCover;
    }

    public boolean isDrawLeftRectAndRightRect() {
        return isDrawLeftRectAndRightRect;
    }

    public void setDrawLeftRectAndRightRect(boolean drawLeftRectAndRightRect) {
        isDrawLeftRectAndRightRect = drawLeftRectAndRightRect;
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    public float getDxLeft() {
        return dxLeft;
    }

    public void setDxLeft(int dxLeft) {
        this.dxLeft = dxLeft;
    }

    public float getDxRight() {
        return dxRight;
    }

    public void setDxRight(int dxRight) {
        this.dxRight = dxRight;
    }

    public float getmDefaultWidth() {
        return mDefaultWidth;
    }

    public void setmDefaultWidth(float mDefaultWidth) {
        this.mDefaultWidth = mDefaultWidth;
    }

    public void setInitLeft(float initLeft) {
        this.initLeft = initLeft;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        invalidate();
    }

    public boolean isDrawShadow() {
        return isDrawShadow;
    }

    public void setDrawShadow(boolean drawShadow) {
        isDrawShadow = drawShadow;
    }
}
