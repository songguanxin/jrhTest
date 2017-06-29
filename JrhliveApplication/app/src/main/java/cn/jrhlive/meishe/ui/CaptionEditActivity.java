package cn.jrhlive.meishe.ui;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jrhlibrary.utils.Mobile;
import com.meicam.sdk.NvsClip;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsThumbnailSequenceView;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsTimelineCaption;
import com.meicam.sdk.NvsVideoTrack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.meishe.bean.ParamShortVideo;
import cn.jrhlive.meishe.presenter.ShortVideoConfig;
import cn.jrhlive.meishe.presenter.ShortVideoManagePresenter;
import cn.jrhlive.meishe.presenter.VideoPresenter;
import cn.jrhlive.meishe.presenter.impl.ShortVideoManagePresenterImpl;
import cn.jrhlive.meishe.ui.widget.CaptionAdjustLayout;
import cn.jrhlive.meishe.ui.widget.NoFlingScrollView;
import cn.jrhlive.utils.ToastUtil;

import static cn.jrhlive.meishe.ui.EditCaptionFragment.TIMEBASE;

/**
 * desc:
 * Created by jiarh on 17/6/29 14:48.
 */

public class CaptionEditActivity extends BaseActivity {
    @BindView(R.id.live_window)
    NvsLiveWindow liveWindow;
    @BindView(R.id.tv_now_time)
    TextView tvNowTime;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.time_lay)
    RelativeLayout timeLay;
    @BindView(R.id.iv_pause_start)
    ImageView ivPauseStart;
    @BindView(R.id.bottom_linearLayout)
    LinearLayout bottomLinearLayout;
    @BindView(R.id.scrollview_squence_view)
    NoFlingScrollView scrollviewSquenceView;
    @BindView(R.id.view_cursor)
    View viewCursor;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.framelay_in_Bottom)
    FrameLayout framelayInBottom;

    private NvsStreamingContext mStreamingContext;

    NvsTimeline timeline;

    VideoPresenter videoPresenter;
    NvsVideoTrack mVideoTrack;
    ShortVideoManagePresenter shortVideoManagePresenter;

    LinearLayout linearLayoutSquence;
    CaptionAdjustLayout captionAdjustLayout;
    List<String> mClipPaths;

    int mRatio = 80;

    int mScrollX;
    int mScrollY;

    int mLeftX;

    List<NvsTimelineCaption> mCaptions;

    @Override
    protected void initEvent() {

        mCaptions = new ArrayList<>();

        scrollviewSquenceView.setScrollViewListenner(new NoFlingScrollView.ScrollViewListenner() {
            @Override
            public void onScrollChanged(NoFlingScrollView view, int l, int t, int oldl, int oldt) {
                mLeftX = l;
            }
        });

        scrollviewSquenceView.setScrollTypeListenter(new NoFlingScrollView.ScrollTypeListener() {
            @Override
            public void onScrollChanged(NoFlingScrollView.ScrollType scrollType) {
                switch (scrollType) {
                    case FLING:
                    case TOUCH_SCROLL:
                        captionAdjustLayout.setCurrentCaption(null);
                        break;
                    case IDLE:
                        captionAdjustLayout.setCurrentCaption(getCurrentCaption());
                        break;
                }
            }
        });

        scrollviewSquenceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int maxScrollX = (int) ((timeline.getDuration()) / TIMEBASE * mRatio);
                mScrollX = v.getScrollX();
                mScrollY = v.getScrollY();
                if (mScrollX > maxScrollX)
                    mScrollX = maxScrollX;
                seekTimeline((float) mScrollX / mRatio, mStreamingContext.STREAMING_ENGINE_SEEK_FLAG_SHOW_CAPTION_POSTER);
                scrollviewSquenceView.smoothScrollTo(mScrollX, mScrollY);
                return false;
            }
        });
    }

    @Override
    protected void initView() {

        ParamShortVideo paramShortVideo = getIntent().getParcelableExtra(ParamShortVideo.PARAM_SHORT_VIDEO);
        mClipPaths = paramShortVideo.getmPaths();

        if (null == mClipPaths || mClipPaths.size() == 0) {
            ToastUtil.showMessage("请传入视频 图片");
            return;
        }
        shortVideoManagePresenter = new ShortVideoManagePresenterImpl(mStreamingContext, liveWindow);
        ShortVideoConfig config = ShortVideoConfig.newBuilder().videoClipPaths(mClipPaths).build();
        shortVideoManagePresenter.init(config);


        timeline = shortVideoManagePresenter.getTimeline();
        mVideoTrack = shortVideoManagePresenter.getVideoTrack();
        seekTimeline(0, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE);


        linearLayoutSquence = new LinearLayout(this);


        scrollviewSquenceView.post(new Runnable() {
            @Override
            public void run() {
                initSquenceView();
            }
        });

    }


    private void initSquenceView() {
        int durationWidth = (int) (timeline.getDuration() / TIMEBASE * mRatio);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(durationWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayoutSquence = new LinearLayout(this);
        framelayInBottom.addView(linearLayoutSquence, params);


        LinearLayout.LayoutParams paramsFront = new LinearLayout.LayoutParams(Mobile.SCREEN_WIDTH / 2 - scrollviewSquenceView.getLeft(), LinearLayout.LayoutParams.MATCH_PARENT);
        View frontSpace = new View(this);
        frontSpace.setBackgroundColor(Color.TRANSPARENT);
        bottomLinearLayout.addView(frontSpace, 0, paramsFront);


        for (int i = 0; i < mVideoTrack.getClipCount(); i++) {
            NvsClip clip = mVideoTrack.getClipByIndex(i);
            NvsThumbnailSequenceView view = new NvsThumbnailSequenceView(this);
            view.setMediaFilePath(clip.getFilePath());
            int sequenceViewWidth = (int) ((clip.getOutPoint() - clip.getInPoint()) / TIMEBASE * mRatio);
            LinearLayout.LayoutParams paramsSequence = new LinearLayout.LayoutParams(sequenceViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayoutSquence.addView(view, paramsSequence);
        }
        //cursor后半段
        LinearLayout.LayoutParams paramsEnd = new LinearLayout.LayoutParams(Mobile.SCREEN_WIDTH / 2, LinearLayout.LayoutParams.MATCH_PARENT);
        View endSpace = new View(this);
        endSpace.setBackgroundColor(Color.TRANSPARENT);
        bottomLinearLayout.addView(endSpace, paramsEnd);

        captionAdjustLayout = new CaptionAdjustLayout(this);
        framelayInBottom.addView(captionAdjustLayout, params);
        captionAdjustLayout.setOnCaptionChangeListener(new CaptionAdjustLayout.onCaptionChangeListener() {
            @Override
            public void onChangeCaptions(List<NvsTimelineCaption> captions) {
                mCaptions.clear();
                mCaptions.addAll(captions);
            }
        });


    }


    @Override
    protected int getViewId() {
        mStreamingContext = NvsStreamingContext.init(this, null);
        return R.layout.activity_caption_edit;
    }


    @OnClick({R.id.iv_pause_start, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pause_start:
                break;
            case R.id.tv_add:
                addCaption();
                break;
        }
    }

    private void addCaption() {

        String ss = "hello gaiay .";
        timeline.addCaption(ss, getCurrentCaptionInTime(), getCurrentCaptionOutTime() - getCurrentCaptionInTime(), null);

        captionAdjustLayout.addCaption(getCurrentCaption());

    }


    private void refreshSquenceView() {
        captionAdjustLayout.setCaptions(getCaptions());

    }

    public NvsTimelineCaption getCurrentCaption() {
        List<NvsTimelineCaption> captionsByTimelinePosition = timeline.getCaptionsByTimelinePosition(getCurrentCaptionInTime());
        if (captionsByTimelinePosition != null && captionsByTimelinePosition.size() != 0) {
            return captionsByTimelinePosition.get(captionsByTimelinePosition.size() - 1);
        }
        return null;
    }

    public List<NvsTimelineCaption> getCaptions() {
        mCaptions.clear();
        NvsTimelineCaption caption = getFirstCaption();
        while (null != caption) {
            mCaptions.add(caption);
            caption = timeline.getNextCaption(caption);
        }
        return mCaptions;
    }

    public NvsTimelineCaption getFirstCaption() {
        return timeline.getFirstCaption();
    }

    /**
     * 添加字幕时，获取字幕inpointtime;
     *
     * @return
     */
    public long getCurrentCaptionInTime() {
        return (long) (mLeftX / mRatio * TIMEBASE);
    }

    /**
     * 添加字幕时，获取字幕outpointtime;
     *
     * @return
     */
    public long getCurrentCaptionOutTime() {
        long endTime = getCurrentCaptionInTime() + (long) (4 * TIMEBASE);
        if (endTime > getToalTimeLong()) {
            endTime = getToalTimeLong();
        }
        return endTime;
    }

    /**
     * 视频总时长
     *
     * @return
     */
    public long getToalTimeLong() {
        return timeline.getDuration();
    }

    private void seekTimeline(float second, int seekShowMode) {
        /* seekTimeline
        * param1: 当前时间线
        * param2: 时间戳 取值范围为  [0, timeLine.getDuration()) (左闭右开区间)
        * param3: 图像预览模式
        * param4: 引擎定位的特殊标志
        * */
        mStreamingContext.seekTimeline(timeline, (long) (second * TIMEBASE), NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, seekShowMode);
    }


}
