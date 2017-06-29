package cn.jrhlive.meishe.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jrhlibrary.utils.Mobile;
import com.meicam.sdk.NvsClip;
import com.meicam.sdk.NvsThumbnailSequenceView;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsTimelineCaption;
import com.meicam.sdk.NvsVideoClip;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.meishe.bean.CaptionEffect;
import cn.jrhlive.meishe.bean.ParamShortVideo;
import cn.jrhlive.meishe.bean.SpecialEffect;
import cn.jrhlive.meishe.event.VideoEvent;
import cn.jrhlive.meishe.presenter.ShortVideoManagePresenter;
import cn.jrhlive.meishe.presenter.VideoPresenter;
import cn.jrhlive.meishe.ui.widget.AdjustCoverLayout;
import cn.jrhlive.meishe.ui.widget.AdjustView;
import cn.jrhlive.meishe.ui.widget.NoFlingScrollView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * desc:
 * Created by jiarh on 17/5/15 14:00.
 */

public class EditCaptionFragment extends ShortVideoParentFragment {


    @BindView(R.id.container_video)
    FrameLayout containerVideo;
    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.thumb_container_lay)
    LinearLayout thumbContainerLay;
    @BindView(R.id.container_thumb)
    NoFlingScrollView containerThumb;
    @BindView(R.id.cursor_line)
    View cursorLine;
    @BindView(R.id.btn_left)
    Button btnLeft;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.bottom_edit_view)
    LinearLayout bottomEditView;

    VideoFragment videoFragment;

    List<String> mClipPaths;

    VideoPresenter videoPresnter;
    ShortVideoManagePresenter shortManage;
    NvsTimeline mTimeLine;
    float progress;
    Disposable thumbDispose;
    boolean isAddCaption;

    public static final float TIMEBASE = 1000000f;
    private int m_ratio = 80;
    private int mScrollX=0;

    int i;
    CountDownTimer countTimer;
    private FrameLayout mFrameLayoutBottomCaption;
    private RelativeLayout mRelativeLayoutAdjustView;

    boolean isFlingFirst=true;

    List<AdjustCoverLayout> mAdjustViewFonts = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_caption;

    }


    public static EditCaptionFragment newInstance(ParamShortVideo param) {
        Bundle args = new Bundle();
        EditCaptionFragment fragment = new EditCaptionFragment();
        if (param == null || param.getmPaths() == null || param.getmPaths().size() == 0)
            throw new IllegalArgumentException("传入的路径不能为空");
        args.putParcelable(ParamShortVideo.PARAM_SHORT_VIDEO, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        mFrameLayoutBottomCaption = new FrameLayout(getActivity());
        mRelativeLayoutAdjustView = new RelativeLayout(getActivity());
        refreshThumbSequenceView();

    }

    private void refreshThumbSequenceView() {


        RxBus.getDefault().toObservable(VideoEvent.class).compose(this.<VideoEvent>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoEvent>() {

                    @Override
                    public void accept(@NonNull VideoEvent videoEvent) throws Exception {
                        if (videoEvent != null) {
                            if (videoEvent.getSate() == VideoEvent.END) {
//                                thumbDispose.dispose();
                                countTimer.cancel();
                                containerThumb.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                            } else if (videoEvent.getSate() == VideoEvent.STOP) {
//                                thumbDispose.dispose();
                                countTimer.cancel();
                            } else if (videoEvent.getSate() == VideoEvent.PLAYING) {
                                updateThumbSequenceView();
                            }
                        }
                    }
                });
    }

    @Override
    public void initEvent() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initTopVideo();
            }
        });

    }

    private void initCaptionSequenceView() {
        shortManage = videoFragment.getShortVideoManagePresenter();
        videoPresnter  =videoFragment.getVideoPresenter();
        //cursor 前半段
        LinearLayout.LayoutParams paramsFront = new LinearLayout.LayoutParams(Mobile.SCREEN_WIDTH / 2 - btnPlay.getWidth() / 2, LinearLayout.LayoutParams.MATCH_PARENT);
        View frontSpace = new View(getActivity());
        frontSpace.setBackgroundColor(Color.LTGRAY);
        thumbContainerLay.addView(frontSpace, paramsFront);


        int durationWidth = (int) (shortManage.getTimeline().getDuration() / TIMEBASE * m_ratio);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(durationWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        thumbContainerLay.addView(mFrameLayoutBottomCaption,params);

        //铺上每一帧
        LinearLayout frameLay = new LinearLayout(getActivity());
        LinearLayout.LayoutParams paramsFrameLayout = new LinearLayout.LayoutParams(durationWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        frameLay.setOrientation(LinearLayout.HORIZONTAL);
        mFrameLayoutBottomCaption.addView(frameLay, paramsFrameLayout);

        for (int i = 0; i < mClipPaths.size(); i++) {
            NvsClip clip = shortManage.getVideoTrack().getClipByIndex(i);
            NvsThumbnailSequenceView view = new NvsThumbnailSequenceView(getActivity());
            view.setMediaFilePath(mClipPaths.get(i));
            view.setStartTime(0);
            view.setDuration(clip.getOutPoint() - clip.getInPoint());
            int sequenceViewWidth = (int) ((clip.getOutPoint() - clip.getInPoint()) / TIMEBASE * m_ratio);
            LinearLayout.LayoutParams paramsSequence = new LinearLayout.LayoutParams(sequenceViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            frameLay.addView(view, paramsSequence);
        }

        mFrameLayoutBottomCaption .addView(mRelativeLayoutAdjustView,paramsFrameLayout);

        //cursor后半段
        LinearLayout.LayoutParams paramsEnd = new LinearLayout.LayoutParams(Mobile.SCREEN_WIDTH / 2 - btnPlay.getWidth() / 2, LinearLayout.LayoutParams.MATCH_PARENT);
        View endSpace = new View(getActivity());
        endSpace.setBackgroundColor(Color.LTGRAY);
        thumbContainerLay.addView(endSpace, paramsEnd);



        /* 对字幕的ThumbnailSequenceView的滑动监听 */
        containerThumb.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int scrollX =mScrollX;
                int scrollY = view.getScrollY();
                progress = ((scrollX / m_ratio) * TIMEBASE) / shortManage.getTimeline().getDuration()*100;
                videoFragment.getVideoPresenter().seekTimeline((int) progress);
//                toggleCover((long)((scrollX / m_ratio) * TIMEBASE));
                containerThumb.smoothScrollTo(scrollX, scrollY);

                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        changeCover(scrollX);
                        break;
                    case MotionEvent.ACTION_UP:
                        isFlingFirst=true;
                        changeCover(scrollX);
                        break;

                }
                return false;
            }
        });

        containerThumb.setScrollViewListenner(new NoFlingScrollView.ScrollViewListenner() {
            @Override
            public void onScrollChanged(NoFlingScrollView view, int l, int t, int oldl, int oldt) {
                 mScrollX = l;
            }
        });


    }

    private void changeCover(int scrollX) {
        if (isFlingFirst){
            showCover((long)((scrollX / m_ratio) * TIMEBASE));
        }
        isFlingFirst=false;
    }

    private void initCaptionAdjustView(long timelineInpoint) {

        float duration = shortManage.getTimeline().getDuration() / TIMEBASE;
        float timelineWidth = duration * m_ratio;
        NvsTimelineCaption caption;
        List<NvsTimelineCaption> captions = shortManage.getTimeline().getCaptionsByTimelinePosition(timelineInpoint);
        if (captions!=null&&captions.size()>0){
            caption = captions.get(0);
        }else {

         caption = shortManage.getTimeline().getFirstCaption();
        }

        long currentPos = 0;

        while (caption!=null) {
            long inPoint = caption.getInPoint();
            long outPoint = caption.getOutPoint();
            final AdjustView adjustView;

            RelativeLayout.LayoutParams paramAdjustView = new RelativeLayout.LayoutParams((int) ((outPoint - inPoint) / TIMEBASE * m_ratio), mFrameLayoutBottomCaption.getHeight());
            adjustView = new AdjustView(getActivity());
            adjustView.setBoundary((int)timelineWidth);
            adjustView.invalidate();



            paramAdjustView.setMarginStart((int)(inPoint/TIMEBASE*m_ratio));

            currentPos = outPoint;
            caption = shortManage.getTimeline().getNextCaption(caption);

            adjustView.setOnChangeListener(new AdjustView.OnTrimInChangeListener() {
                @Override
                public void onChange(int to) {
                    int timelinePosition = (int)((to / (float)m_ratio) * TIMEBASE);
                    List<NvsTimelineCaption> captions = shortManage.getTimeline().getCaptionsByTimelinePosition(timelinePosition);

                    if(captions != null) {
                        for (int i = 0; i < captions.size(); i++) {
                            NvsTimelineCaption captionNow = captions.get(i);
                            captionNow.changeInPoint(timelinePosition);
                        }
                    }

//                    timeline.setText((float)timelinePosition/TIMEBASE + "s");
                }

            });

            adjustView.setOnChangeListener(new AdjustView.OnTrimOutChangeListener() {
                @Override
                public void onChange(int to) {
                    int timelinePosition = (int)((to / (float)m_ratio) * TIMEBASE);
                    List<NvsTimelineCaption> captions = shortManage.getTimeline().getCaptionsByTimelinePosition(timelinePosition);

                    if(captions != null) {
                        for (int i = 0; i < captions.size(); i++) {
                            NvsTimelineCaption captionNow = captions.get(i);
                            captionNow.changeOutPoint(timelinePosition);
                        }
                    }
//                    timeline.setText((float)timelinePosition/TIMEBASE + "s");
                }
            });
        }

    }
    private void initTopVideo() {

        ParamShortVideo paramShortVideo = getArguments().getParcelable(ParamShortVideo.PARAM_SHORT_VIDEO);
        mClipPaths = paramShortVideo.getmPaths();
        videoFragment = new VideoFragment();
        getChildFragmentManager().beginTransaction().add(R.id.container_video, videoFragment).commitNowAllowingStateLoss();
        videoFragment.initVideoData(mClipPaths);
        btnPlay.post(new Runnable() {
            @Override
            public void run() {
                initCaptionSequenceView();

            }
        });


    }

    @OnClick({R.id.btn_play, R.id.btn_left, R.id.btn_add, R.id.btn_right})
    public void onViewClicked(View view) {
        mTimeLine = shortManage.getTimeline();
        switch (view.getId()) {
            case R.id.btn_play:
                videoFragment.play();
                break;
            case R.id.btn_left:
                break;
            case R.id.btn_add:
                i++;
//                isAddCaption = !isAddCaption;
//                if (!isAddCaption){
//                    SpecialEffect special = new SpecialEffect();
//                    special.setAddCaption(false);
//                    videoPresnter.removeSpecialEffec(special);
//                    return;
//                }



//                for (int i = 0; i < shortManage.getVideoTrack().getClipCount(); i++) {
                 SpecialEffect specialEffect = new SpecialEffect();
                    NvsVideoClip videoClip = shortManage.getVideoTrack().getClipByIndex(i);
                    long start =(long)( (mScrollX / m_ratio) * TIMEBASE);
                    long end = (start+4*TIMEBASE)>(int)shortManage.getTimeline().getDuration()?(int)(shortManage.getTimeline().getDuration()):(int)(start+4*TIMEBASE);

//                    long start = videoClip.getInPoint();
//                    long end  = videoClip.getOutPoint();
                    CaptionEffect captionEffect = new CaptionEffect("gaiay"+i, start, end);
                    specialEffect.setCaptionEffect(captionEffect);
                    videoPresnter.updateSpecialEffect(specialEffect);
//                }
                showCover(start);
                initCaptionAdjustView((long)(mScrollX/m_ratio*TIMEBASE));
                break;
            case R.id.btn_right:
                break;
        }
    }

    private void showCover(long start) {
       for (AdjustCoverLayout cl : mAdjustViewFonts){
//           cl.toggle(start);
       }
    }


    private void updateThumbSequenceView() {
        thumbDispose = Observable.interval(1000 / 30, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        containerThumb.scrollBy(m_ratio / 30, LinearLayout.LayoutParams.MATCH_PARENT);
                    }
                });
    }
}
