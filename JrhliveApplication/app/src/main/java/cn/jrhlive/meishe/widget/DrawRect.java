package cn.jrhlive.meishe.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import cn.jrhlive.R;


public class DrawRect extends View {

    private Paint m_paint;
    private RectF rect;
    private RectF deleteRect;
    /**
     * 删除图标
     */
    private Bitmap deleteBp;
    /**
     * 放大缩小
     */
    private Bitmap scaleBp;
    private RectF scaleRect;

    private float scaleDx;
    private float scaleDy;




    private boolean isClickDelete;
    private boolean isClickScale;

    OnDeleteClickListener onDeleteClickListener;
    OnScaleDragListener onScaleDragListener;

    public DrawRect(Context context, float left, float top, float right, float bottom) {
        super(context);
        m_paint = new Paint();
        // 设置颜色
        m_paint.setColor(Color.WHITE);
        // 设置抗锯齿
        m_paint.setAntiAlias(true);
        // 设置线宽
        m_paint.setStrokeWidth(2);
        // 设置非填充
        m_paint.setStyle(Paint.Style.STROKE);


        rect = new RectF(left, top, right, bottom);

        deleteBp = BitmapFactory.decodeResource(getResources(), R.drawable.caption_delete_icon);
        scaleBp = BitmapFactory.decodeResource(getResources(), R.drawable.scale_caption);

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect, m_paint);
        drawDelete(canvas);
        drawScale(canvas);
    }

    private void drawScale(Canvas canvas) {
        scaleRect = new RectF(rect.right - scaleBp.getWidth() / 2, rect.bottom - scaleBp.getHeight() / 2, rect.right + scaleBp.getWidth() / 2, rect.bottom + scaleBp.getHeight() / 2);
        canvas.drawBitmap(scaleBp, null, scaleRect, m_paint);
    }

    private void drawDelete(Canvas canvas) {
        deleteRect = new RectF(rect.right - deleteBp.getWidth() / 2, rect.top - deleteBp.getHeight() / 2, rect.right + deleteBp.getWidth() / 2, rect.top + deleteBp.getHeight() / 2);
        canvas.drawBitmap(deleteBp, null, deleteRect, m_paint);
    }
    float lastX = 0, lastY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                isClickDelete=dealDeleteIcon(event);
                isClickScale = dealScaleIcon(event);
                break;

            case MotionEvent.ACTION_MOVE:

                scaleDx = event.getRawX() - lastX;
                scaleDy = event.getRawY() - lastY;
                if (scaleDx>scaleDy){
                    scaleDy = scaleDx;
                }else {
                    scaleDx = scaleDy;
                }
                if (isClickScale) {
                    rect.left -= scaleDx / 2;
                    rect.right +=scaleDx / 2;
                    rect.top -= scaleDy / 2;
                    rect.bottom += scaleDy / 2;
                    invalidate();
                    if (onScaleDragListener!=null){
                        onScaleDragListener.onDrag(scaleDx);
                    }

                }
                lastX = event.getRawX();
                lastY = event.getRawY();

                break;

            case MotionEvent.ACTION_UP:

                break;


        }
        return true;
    }

    /**
     * 处理删除逻辑
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

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnScaleDragListener(OnScaleDragListener onScaleDragListener) {
        this.onScaleDragListener = onScaleDragListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked();
    }
    public interface  OnScaleDragListener{
        void onDrag(float dis);
    }
}
