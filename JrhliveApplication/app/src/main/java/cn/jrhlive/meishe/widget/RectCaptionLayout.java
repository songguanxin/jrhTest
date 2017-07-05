package cn.jrhlive.meishe.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsTimelineCaption;

/**
 * desc:
 * Created by jiarh on 17/7/5 14:07.
 */

public class RectCaptionLayout extends FrameLayout {

    private NvsTimelineCaption mCurrentCaption;
    private NvsLiveWindow mLiveWindow;
    private int liveWindowWidth;
    private int liveWindowHeight;
    private DrawRect mCaptionDrawRect;


    private PointF mCaptionLowerRightCorner;
    private PointF mCaptionTopLeftCorner;
    private PointF mCaptionMapLivedowLowerRight;
    private PointF mCaptionMapLivedowTopLeft;

    DrawRect.OnDeleteClickListener onDeleteClickListener;
    DrawRect.OnScaleDragListener onScaleDragListener;
    DrawRect.OnCaptionMoveListener onCaptionMoveListener;

    public RectCaptionLayout(@NonNull Context context) {
        this(context, null);
    }

    public RectCaptionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectCaptionLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void refreshRect(NvsLiveWindow liveWindow, NvsTimelineCaption caption, int viewMode) {
        this.mLiveWindow = liveWindow;
        this.mCurrentCaption = caption;
        this.liveWindowWidth = mLiveWindow.getWidth();
        this.liveWindowHeight = mLiveWindow.getHeight();
        removeAllViews();
        if (mCurrentCaption == null) {
            return;
        } else {
            if (updateCaptionCoordinate(caption)){
                mCaptionDrawRect = new DrawRect(getContext(), liveWindowWidth, liveWindowHeight, viewMode
                        , mCaptionMapLivedowTopLeft.x, mCaptionMapLivedowTopLeft.y
                        , mCaptionMapLivedowLowerRight.x, mCaptionMapLivedowLowerRight.y);
                if (onDeleteClickListener != null)
                    mCaptionDrawRect.setOnDeleteClickListener(onDeleteClickListener);
                if (onScaleDragListener != null)
                    mCaptionDrawRect.setOnScaleDragListener(onScaleDragListener);
                if (onCaptionMoveListener != null)
                    mCaptionDrawRect.setOnCaptionMoveListener(onCaptionMoveListener);
                addView(mCaptionDrawRect);
            }
        }

    }

    public void upDateCaptionRect(NvsTimelineCaption caption){
        if (mCaptionDrawRect==null){
            return;
        }
        if (caption==null){
            mCaptionDrawRect.setVisibility(GONE);
        }else {
            mCaptionDrawRect.setVisibility(VISIBLE);
        }

       if( updateCaptionCoordinate(caption)){
           mCaptionDrawRect.updateRect(mCaptionMapLivedowTopLeft.x, mCaptionMapLivedowTopLeft.y
                   , mCaptionMapLivedowLowerRight.x, mCaptionMapLivedowLowerRight.y);
       }

    }

    private boolean updateCaptionCoordinate(NvsTimelineCaption caption) {
        if (caption != null) {
            RectF recF = caption.getTextBoundingRect();    // 获取字幕文本矩形框
            mCaptionTopLeftCorner = new PointF(recF.left, recF.top);  // topLeftCorner：字幕的文本矩形框左上角在timeLine上的坐标
            mCaptionLowerRightCorner = new PointF(recF.right, recF.bottom); // lowerRightCorner：字幕的文本矩形框右下角在timeLine上的坐标

            /** 将字幕在时间线上坐标转换成视图坐标 */
            mCaptionMapLivedowTopLeft = mLiveWindow.mapCanonicalToView(mCaptionTopLeftCorner);
            mCaptionMapLivedowLowerRight = mLiveWindow.mapCanonicalToView(mCaptionLowerRightCorner);
            return mCaptionMapLivedowTopLeft!=null&&mCaptionMapLivedowLowerRight!=null;
        }
        return false;
    }

    public void setOnDeleteClickListener(DrawRect.OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnScaleDragListener(DrawRect.OnScaleDragListener onScaleDragListener) {
        this.onScaleDragListener = onScaleDragListener;
    }

    public void setOnCaptionMoveListener(DrawRect.OnCaptionMoveListener onCaptionMoveListener) {
        this.onCaptionMoveListener = onCaptionMoveListener;
    }
}
