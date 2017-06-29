package cn.jrhlive.meishe.ui;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsStreamingContext;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.meishe.bean.SpecialEffect;
import cn.jrhlive.meishe.presenter.ShortVideoConfig;
import cn.jrhlive.meishe.presenter.ShortVideoManagePresenter;
import cn.jrhlive.meishe.presenter.VideoPresenter;
import cn.jrhlive.meishe.presenter.impl.ShortVideoManagePresenterImpl;
import cn.jrhlive.meishe.presenter.impl.VideoPresenterImpl;
import cn.jrhlive.meishe.view.VideoView;
import cn.jrhlive.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * desc:
 * Created by jiarh on 17/5/5 17:03.
 */

public class VideoFragment extends ShortVideoParentFragment implements SeekBar.OnSeekBarChangeListener, VideoView {
    @BindView(R.id.live_window)
    NvsLiveWindow mLiveWindow;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.btn_start_pause)
    Button mBtnStartPause;
    private NvsStreamingContext mStreamingContext;

    VideoPresenter videoPresenter;
    ShortVideoManagePresenter shortVideoManagePresenter;

    List<String> mClipPaths;
    Disposable timeDisposable;

    int state;

    @Override
    public int getLayoutId() {
        mStreamingContext = NvsStreamingContext.init(getActivity(), null);
        return R.layout.fragment_video;
    }

    @Override
    public void initView() {
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void initEvent() {
        mClipPaths = new ArrayList<>();

        updateEffect();
    }

    private void updateEffect() {
        RxBus.getDefault().toObservable(SpecialEffect.class)
                .compose(this.<SpecialEffect>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SpecialEffect>() {
                    @Override
                    public void accept(@NonNull SpecialEffect specialEffect) throws Exception {
                        videoPresenter.updateSpecialEffect(specialEffect);
                    }
                });

    }

    public void initVideoData(List<String> clipPaths) {
        if (null == clipPaths || clipPaths.size() == 0) {
            ToastUtil.showMessage("请传入视频 图片");
            return;
        }
        videoPresenter = new VideoPresenterImpl();
        videoPresenter.onAttachView(this);
        shortVideoManagePresenter = new ShortVideoManagePresenterImpl(mStreamingContext, mLiveWindow);
        ShortVideoConfig config = ShortVideoConfig.newBuilder().videoClipPaths(clipPaths).build();
        shortVideoManagePresenter.init(config);
        videoPresenter.setVideoManager(shortVideoManagePresenter);
    }

    @OnClick(R.id.btn_start_pause)
    public void onPlayClicked() {
        if (videoPresenter == null) return;
        state = mStreamingContext.getStreamingEngineState();
        if (state == NvsStreamingContext.STREAMING_ENGINE_STATE_STOPPED || state == NvsStreamingContext.STREAMING_ENGINE_STATE_SEEKING) {
            play();

        } else if (state == NvsStreamingContext.STREAMING_ENGINE_STATE_PLAYBACK) {
            videoPresenter.stopPlay();
            stopSeekBar();
            mBtnStartPause.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void play() {
        mBtnStartPause.setText("暂停");
        int startTime = (int) (mSeekBar.getProgress() * shortVideoManagePresenter.getTimeline().getDuration() / 100);
        videoPresenter.playbackTimeline(startTime, -1);
    }

    @Override
    public void updateSeekBar() {
        timeDisposable = Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                int progress = (int) (100 * mStreamingContext.getTimelineCurrentPosition(shortVideoManagePresenter.getTimeline()) / shortVideoManagePresenter.getTimeline().getDuration());
                mSeekBar.setProgress(progress);
            }
        });
    }

    @Override
    public void addCaption(boolean add) {

    }

    @Override
    public void onStop() {
        videoPresenter.stopPlay();
        super.onStop();
    }

    @Override
    public void stopSeekBar() {
        timeDisposable.dispose();
        videoPresenter.stopPlay();
        mBtnStartPause.setText("播放");
    }

    @Override
    public void seek() {
        videoPresenter.seekTimeline(mSeekBar.getProgress());
    }

    @Override
    public void moveSeekBar(int progress) {
        mSeekBar.setProgress(progress);
    }

    @Override
    public void reset() {
        mSeekBar.setProgress(0);
        mBtnStartPause.setText("播放");

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        videoPresenter.seekTimeline(seekBar.getProgress());
        play();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String msg) {

        ToastUtil.showMessage(msg);
    }

    public NvsStreamingContext getmStreamingContext() {
        return mStreamingContext;
    }

    public VideoPresenter getVideoPresenter() {
        return videoPresenter;
    }

    public ShortVideoManagePresenter getShortVideoManagePresenter() {
        return shortVideoManagePresenter;
    }

    public Disposable getTimeDisposable() {
        return timeDisposable;
    }
}
