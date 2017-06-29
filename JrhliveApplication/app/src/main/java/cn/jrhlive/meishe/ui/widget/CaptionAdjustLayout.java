package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meicam.sdk.NvsTimelineCaption;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 字幕调解
 * Created by jiarh on 17/6/29 13:54.
 */

public class CaptionAdjustLayout extends FrameLayout {
    private Context mContext;

    /**
     * 当前指针位置
     */
    private float mCurrentX;
    /**
     * 当前操作字幕
     */
    private NvsTimelineCaption mCurrentCaption;
    private List<NvsTimelineCaption> captions;

    private int mRatio = 80;
    private float TIMEBASE = 1000000f;

    onCaptionChangeListener onCaptionChangeListener;


    public CaptionAdjustLayout(Context context) {
        this(context, null);
    }

    public CaptionAdjustLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaptionAdjustLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.mContext = context;
        post(upDateViewRunnable);

    }


    Runnable upDateViewRunnable = new Runnable() {
        @Override
        public void run() {
            refreshView();
        }
    };

    public void refreshView() {

        removeAllViews();
        if (captions == null || captions.size() == 0) return;

        if (captions != null && captions.size() > 0 && mCurrentCaption != null) {
            if (captions.contains(mCurrentCaption)) {
                int index = captions.indexOf(mCurrentCaption);
                if (index != captions.size() - 1) {
                    captions.remove(index);
                    captions.add(mCurrentCaption);
                }
            }
        }

        for (final NvsTimelineCaption caption : captions) {
            CutView cutView = new CutView(mContext);
            cutView.setEditMode(isCurrentCaption(caption));
            cutView.setmDefaultWidth(getDefaultCutViewWidth(caption));
            cutView.setInitLeft(getInitLeft(caption));
            cutView.setDrawLeftRectAndRightRect(false);
            cutView.invalidate();
            cutView.setOnChangeListener(new CutView.OnTrimInChangeListener() {
                @Override
                public void onChange(int to) {

                    caption.changeInPoint((long) (to / mRatio * TIMEBASE));
                    if (onCaptionChangeListener != null) {
                        onCaptionChangeListener.onChangeCaptions(captions);
                    }
                }
            });
            cutView.setOnChangeListener(new CutView.OnTrimOutChangeListener() {
                @Override
                public void onChange(int to) {
                    caption.changeOutPoint((long) (to / mRatio * TIMEBASE));
                    if (onCaptionChangeListener != null) {
                        onCaptionChangeListener.onChangeCaptions(captions);
                    }
                }
            });
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(cutView, params);
        }

    }


    private float getInitLeft(NvsTimelineCaption caption) {
        if (caption == null) return 0;
        return caption.getInPoint() / TIMEBASE * mRatio;
    }

    /**
     * 获取每个字幕占的宽度
     *
     * @param caption
     * @return
     */
    private float getDefaultCutViewWidth(NvsTimelineCaption caption) {
        if (caption == null) return 0;
        long dution = caption.getOutPoint() - caption.getInPoint();
        return dution / TIMEBASE * mRatio;
    }

    /**
     * 判断是否当前字幕
     *
     * @param caption
     * @return
     */
    private boolean isCurrentCaption(NvsTimelineCaption caption) {

        if (mCurrentCaption != null) {
            return mCurrentCaption.getText().equals(caption.getText()) && mCurrentCaption.getInPoint() == caption.getInPoint() && mCurrentCaption.getOutPoint() == caption.getOutPoint();
        }
        return false;
    }


    public void setCurrentX(float mCurrentX) {
        this.mCurrentX = mCurrentX;
    }

    public void setCaptions(List<NvsTimelineCaption> captions) {
        this.captions = captions;
        if (captions == null || captions.size() == 0) return;
        setCurrentCaption(captions.get(captions.size() - 1));
    }

    public void setCurrentCaption(NvsTimelineCaption mCurrentCaption) {
        this.mCurrentCaption = mCurrentCaption;
        refreshView();
    }

    public void addCaption(NvsTimelineCaption caption) {
        if (caption == null)
            return;

        if (captions == null) {
            captions = new ArrayList<>();
        }
        if (captions.size() > 0)
            captions.add(captions.size() - 1, caption);
        else
            captions.add(caption);
        setCurrentCaption(caption);
    }

    public void removeCaption(NvsTimelineCaption caption) {
        if (captions == null || caption == null) return;
        if (isCurrentCaption(caption)) {
            setCurrentCaption(null);
        }
        if (captions.contains(caption)) {
            int index = captions.indexOf(caption);
            captions.remove(index);
            refreshView();
        }

    }

    public void setOnCaptionChangeListener(CaptionAdjustLayout.onCaptionChangeListener onCaptionChangeListener) {
        this.onCaptionChangeListener = onCaptionChangeListener;
    }

    public interface onCaptionChangeListener {
        void onChangeCaptions(List<NvsTimelineCaption> captions);
    }
}
