package cn.jrhlive.meishe.presenter.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.meicam.sdk.NvsAssetPackageManager;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;

import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.basemvp.BasePresenterImp;
import cn.jrhlive.meishe.bean.SpecialEffect;
import cn.jrhlive.meishe.event.VideoEvent;
import cn.jrhlive.meishe.presenter.ShortVideoManagePresenter;
import cn.jrhlive.meishe.presenter.VideoPresenter;
import cn.jrhlive.meishe.view.VideoView;

/**
 * desc:
 * Created by jiarh on 17/5/5 17:33.
 */

public class VideoPresenterImpl extends BasePresenterImp<VideoView, Object> implements VideoPresenter, NvsStreamingContext.PlaybackCallback {


    private NvsStreamingContext mStreamingContext;
    private ShortVideoManagePresenter shortVideoManage;
    private NvsTimeline mTimeline;


    @Override
    public void init() {
        mStreamingContext.setPlaybackCallback(this);
    }

    @Override
    public void setVideoManager(@NonNull ShortVideoManagePresenter shortVideoManagePresenter) {
        this.shortVideoManage = shortVideoManagePresenter;
        this.mStreamingContext = shortVideoManagePresenter.getNvsStreamingContext();
        this.mTimeline = shortVideoManagePresenter.getTimeline();
        init();
    }


    @Override
    public void playbackTimeline(int startPosition, int endPosition) {

        mStreamingContext.playbackTimeline(getmTimeline(), startPosition,
                endPosition, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, true, 0);
        mView.updateSeekBar();

    }

    @Override
    public void stopPlay() {
        mStreamingContext.stop();

    }

    @Override
    public void seekTimeline(int progress) {
        mView.moveSeekBar(progress);
        mStreamingContext.seekTimeline(getmTimeline(), progress * getmTimeline().getDuration() / 100, NvsStreamingContext.VIDEO_PREVIEW_SIZEMODE_LIVEWINDOW_SIZE, 0);

    }

    @Override
    public void updateSpecialEffect(SpecialEffect specialEffect) {
//        如果assetPackageId为空，既不需要检查是否安装特效包
        if (TextUtils.isEmpty(specialEffect.getAssetPackageId())) {

            //需要添加无字幕样式的字幕
            if (specialEffect.getCaptionEffect() != null) {
                shortVideoManage.addCaption(specialEffect);
            }

            return;
        }
        int error = shortVideoManage.installAssetPackage(specialEffect);

        if (error != NvsAssetPackageManager.ASSET_PACKAGE_MANAGER_ERROR_NO_ERROR
                && error != NvsAssetPackageManager.ASSET_PACKAGE_MANAGER_ERROR_ALREADY_INSTALLED) {
            mView.showMsg("资源包安装失败");
            return;
        }

        switch (specialEffect.getPacageType()) {
            case NvsAssetPackageManager.ASSET_PACKAGE_TYPE_THEME:
                shortVideoManage.removeTheme();
                shortVideoManage.addTheme(specialEffect);
                break;
            case NvsAssetPackageManager.ASSET_PACKAGE_TYPE_ANIMATEDSTICKER:
                shortVideoManage.removeAnimatedSticker();
                shortVideoManage.addAnimatedSticker(specialEffect);
                break;
            case NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOFX:
                shortVideoManage.addFilterEffect(specialEffect);
                break;
            case NvsAssetPackageManager.ASSET_PACKAGE_TYPE_CAPTIONSTYLE:
                if (specialEffect.isAddCaption()) {
                    mView.addCaption(true);
                    shortVideoManage.addCaption(specialEffect);
                    shortVideoManage.applyCaptionStyle(specialEffect);
                } else {
                    mView.addCaption(false);
                    shortVideoManage.removeCaption();
                    shortVideoManage.removeCaptionStyle();
                }
                break;
            case NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOTRANSITION:
                break;
        }

        mView.seek();
        mView.play();
    }

    @Override
    public void removeSpecialEffec(SpecialEffect specialEffect) {
        if (specialEffect==null)return;
        if (!specialEffect.isAddCaption()){
            shortVideoManage.removeCaption();
        }
    }


    @Override
    public void closeStreaming() {
        mStreamingContext.close();
    }


    /**
     * 播放回调
     *
     * @param nvsTimeline
     */
    @Override
    public void onPlaybackPreloadingCompletion(NvsTimeline nvsTimeline) {
        RxBus.getDefault().post(new VideoEvent(VideoEvent.PLAYING));
    }

    @Override
    public void onPlaybackStopped(NvsTimeline nvsTimeline) {
        mView.stopSeekBar();
        RxBus.getDefault().post(new VideoEvent(VideoEvent.STOP));
    }

    @Override
    public void onPlaybackEOF(NvsTimeline nvsTimeline) {
        mView.reset();
        RxBus.getDefault().post(new VideoEvent(VideoEvent.END));

    }

    public NvsTimeline getmTimeline() {
        return mTimeline = shortVideoManage.getTimeline();
    }

    public void setmTimeline(NvsTimeline mTimeline) {
        this.mTimeline = mTimeline;
    }
}
