package cn.jrhlive.meishe.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.jrhlive.R;


public class DrawRect extends View {

    private Paint mPaint;
    private RectF rect;
    /**
     * 删除图标
     */
    private Bitmap deleteBp;
    private RectF deleteRect;
    /**
     * 放大缩小
     */
    private Bitmap scaleBp;
    private RectF scaleRect;

    /**
     * x 轴缩放大小
     */
    private double scaleDx;
    /**
     * y轴缩放大小
     */
    private double scaleDy;
    /**
     * 点击删除
     */
    private boolean isClickDelete;
    /**
     * 拖动缩放
     */
    private boolean isClickScale;

    /**
     * 当前矩形对角线尺寸
     */
    private double currentDis = 0;
    /**
     * 矩形初始值对角线尺寸
     */
    private double beforeDis = 0;
    /**
     * 最大缩放倍数
     */
    private float maxRadio = 1.3f;
    /**
     * 最小间距控制
     */
    private float minDis = 100;


    private int liveWindowWidth;
    private int liveWidowHeight;
    /**
     * 字幕框 1，贴纸 2
     */
    private int captionMode = 1;
    private int stickerMode = 2;
    private int viewMode = 0;

    float lastX = 0, lastY = 0;
    private int ivWidth;

    OnDeleteClickListener onDeleteClickListener;
    OnScaleDragListener onScaleDragListener;
    OnCaptionMoveListener onCaptionMoveListener;
    OnStickerMoveListener onStickMoveListener;

    private static final String TAG = "DrawRect";

    public DrawRect(Context context, int width, int height, int mode, float l, float t, float r, float b) {
        super(context);

        this.liveWindowWidth = width;
        this.liveWidowHeight = height;
        this.viewMode = mode;

        mPaint = new Paint();
        // 设置颜色
        mPaint.setColor(Color.WHITE);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
        // 设置线宽
        mPaint.setStrokeWidth(5);
        // 设置非填充
        mPaint.setStyle(Paint.Style.STROKE);

        rect = new RectF(l, t, r, b);
        minDis = Math.min(minDis, r - l);
        beforeDis = Math.sqrt(Math.hypot(r - l, b - t));
        deleteBp = BitmapFactory.decodeResource(getResources(), R.drawable.caption_delete_icon);
        scaleBp = BitmapFactory.decodeResource(getResources(), R.drawable.scale_caption);

        ivWidth = scaleBp.getWidth();

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect.right < liveWindowWidth) {
            canvas.drawRect(rect, mPaint);
            drawDelete(canvas);
            drawScale(canvas);
        }

    }

    private void drawScale(Canvas canvas) {
        scaleRect = new RectF(rect.right - scaleBp.getWidth() / 2, rect.bottom - scaleBp.getHeight() / 2, rect.right + scaleBp.getWidth() / 2, rect.bottom + scaleBp.getHeight() / 2);
        canvas.drawBitmap(scaleBp, null, scaleRect, mPaint);
    }

    private void drawDelete(Canvas canvas) {
        deleteRect = new RectF(rect.right - deleteBp.getWidth() / 2, rect.top - deleteBp.getHeight() / 2, rect.right + deleteBp.getWidth() / 2, rect.top + deleteBp.getHeight() / 2);
        canvas.drawBitmap(deleteBp, null, deleteRect, mPaint);
    }


    public void updateRect(float l, float t, float r, float b) {
        rect.set(l, t, r, b);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                isClickDelete = dealDeleteIcon(event);
                isClickScale = dealScaleIcon(event);
                break;

            case MotionEvent.ACTION_MOVE:
                if (isClickScale) {
                    scaleDx = event.getRawX() - lastX;
                    scaleDy = event.getRawY() - lastY;
                    calculateRect(scaleDx, scaleDy);
                    if (onScaleDragListener != null) {
                        onScaleDragListener.onDrag((float) scaleDx);
                    }
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                } else {
                    float dx = event.getRawX() - lastX;
                    float dy = event.getRawY() - lastY;
                    center(this, event, dx, dy);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }

    private void calculateRect(double scaleDx, double scaleDy) {
        if (Math.abs(scaleDx) > Math.abs(scaleDy)) {
            scaleDy = scaleDx;
        } else {
            scaleDx = scaleDy;
        }
        double l = rect.left - scaleDx / 2;
        double r = rect.right + scaleDx / 2;
        double t = rect.top - scaleDy / 2;
        double b = rect.bottom + scaleDy / 2;

        if (r > liveWindowWidth - ivWidth) {
            isClickScale = false;

            return;
        }
        currentDis = Math.sqrt(Math.hypot(r - l, b - t));
        if (isNotAllowDrag()) {
            isClickScale = false;
            return;
        }
        if (r - l < minDis || b - t < minDis) {
            Log.e(TAG, "calculateRect: " + (r - l));
            return;
        }
        rect.set((float) l, (float) t, (float) r, (float) b);
        invalidate();
    }


    private boolean isNotAllowDrag() {
        Log.e(TAG, "isNotAllowDrag: " + currentDis / beforeDis);
        return currentDis / beforeDis > maxRadio;
    }

    /**
     * 处理缩放逻辑
     *
     * @param event
     */
    private boolean dealScaleIcon(MotionEvent event) {
        if (event.getX() >= scaleRect.left && event.getX() <= scaleRect.right && event.getY() <= scaleRect.bottom && event.getY() >= scaleRect.top) {

            return true;
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return false;
    }


    /**
     * 处理删除逻辑
     *
     * @param event
     */
    private boolean dealDeleteIcon(MotionEvent event) {
        if (event.getX() >= deleteRect.left && event.getX() <= deleteRect.right && event.getY() <= deleteRect.bottom && event.getY() >= deleteRect.top) {

            if (onDeleteClickListener != null) {

                onDeleteClickListener.onDeleteClicked();
            }
            return true;
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }


    private void center(View v, MotionEvent event, float dx, float dy) {
        float left = rect.left + dx;
        float top = rect.top + dy;
        float right = rect.right + dx;
        float bottom = rect.bottom + dy;

        float newX = event.getRawX();
        float newY = event.getRawY();

        if (left < 0) {
            newX = (int) lastX;
        }
        if (right > liveWindowWidth - ivWidth) {

            newX = (int) lastX;
        }
        if (top < 0) {
            newY = (int) lastY;
        }
        if (bottom > liveWidowHeight) {
            newY = (int) lastY;
        }


        rect.set(left, top, right, bottom);
        if (viewMode == captionMode && onCaptionMoveListener != null)
            onCaptionMoveListener.onCaptionMove(newX, newY, lastX, lastY);
        else if (viewMode == stickerMode && onStickMoveListener != null)
            onStickMoveListener.onStickerMove(newX, newY, lastX, lastY);
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked();
    }

    public interface OnScaleDragListener {
        void onDrag(float dis);
    }

    public interface OnCaptionMoveListener {
        void onCaptionMove(float newx, float newy, float oldx, float oldy);
    }

    public interface OnStickerMoveListener {
        void onStickerMove(float newx, float newy, float oldx, float oldy);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnScaleDragListener(OnScaleDragListener onScaleDragListener) {
        this.onScaleDragListener = onScaleDragListener;
    }

    public void setOnCaptionMoveListener(OnCaptionMoveListener onCaptionMoveListener) {
        this.onCaptionMoveListener = onCaptionMoveListener;
    }

    public void setOnStickMoveListener(OnStickerMoveListener onStickMoveListener) {
        this.onStickMoveListener = onStickMoveListener;
    }
}
