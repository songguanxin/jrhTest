package cn.jrhlive.meishe.presenter.impl;

import android.text.TextUtils;

import com.meicam.sdk.NvsAudioResolution;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsTimelineAnimatedSticker;
import com.meicam.sdk.NvsTimelineCaption;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoResolution;
import com.meicam.sdk.NvsVideoTrack;

import java.util.List;

import cn.jrhlive.meishe.bean.CaptionEffect;
import cn.jrhlive.meishe.bean.SpecialEffect;
import cn.jrhlive.meishe.presenter.ShortVideoConfig;
import cn.jrhlive.meishe.presenter.ShortVideoManagePresenter;


/**
 * desc:
 * Created by jiarh on 17/5/10 11:21.
 */

public class ShortVideoManagePresenterImpl implements ShortVideoManagePresenter, NvsStreamingContext.StreamingEngineCallback, NvsStreamingContext.CompileCallback {

    private NvsTimeline mTimeline;
    private NvsVideoTrack mVideoTrack;
    private NvsStreamingContext mStreamingContext;
    private NvsLiveWindow mLiveWindow;
    private ShortVideoConfig mConfig;

    public ShortVideoManagePresenterImpl(NvsStreamingContext mStreamingContext, NvsLiveWindow mLiveWindow) {
        this.mStreamingContext = mStreamingContext;
        this.mLiveWindow = mLiveWindow;
    }

    @Override
    public void init(ShortVideoConfig config) {
        this.mConfig = config;
        createTimeline();
        appendVideoTrack();
        appendVideoClip();
    }

    @Override
    public void createTimeline() {

        NvsVideoResolution videoEditRes = new NvsVideoResolution();
        videoEditRes.imageWidth = mConfig.getImgWidth();
        videoEditRes.imageHeight = mConfig.getImgHeight();
        videoEditRes.imagePAR = mConfig.getImgPAR();
        NvsRational videoFps = mConfig.getVideoFps();
        NvsAudioResolution audioEditRes = new NvsAudioResolution();
        audioEditRes.sampleRate = mConfig.getSampleRate();
        audioEditRes.channelCount = mConfig.getChannelCount();
        mTimeline = mStreamingContext.createTimeline(videoEditRes, videoFps, audioEditRes);
        mStreamingContext.connectTimelineWithLiveWindow(mTimeline, mLiveWindow);
        mStreamingContext.setStreamingEngineCallback(this);
        mStreamingContext.setCompileCallback(this);

    }


    @Override
    public NvsVideoTrack appendVideoTrack() {
        mVideoTrack = mTimeline.appendVideoTrack();
        return mVideoTrack;
    }

    @Override
    public NvsTimeline getTimeline() {
        return mTimeline;
    }

    @Override
    public NvsStreamingContext getNvsStreamingContext() {
        return mStreamingContext;
    }

    @Override
    public NvsVideoTrack getVideoTrack() {
        return mVideoTrack;
    }

    @Override
    public void appendVideoClip() {

        List<String> videoClipPaths = mConfig.getVideoClipPaths();
        int currentCount = mVideoTrack.getClipCount();
        for (int i = currentCount; i < videoClipPaths.size(); i++) {
            mVideoTrack.appendClip(videoClipPaths.get(i));
        }

    }

    @Override
    public int installAssetPackage(SpecialEffect specialEffect) {
        return mStreamingContext.getAssetPackageManager().installAssetPackage(specialEffect.getPath(),
                specialEffect.getAuthFilePath(), specialEffect.getPacageType(), true, specialEffect.getAssetPackageId());

    }

    @Override
    public void addTheme(SpecialEffect specialEffect) {
        mTimeline.applyTheme(specialEffect.getAssetPackageId().toString());
    }

    @Override
    public void removeTheme() {
        mTimeline.removeCurrentTheme();
    }

    @Override
    public void addAnimatedSticker(SpecialEffect specialEffect) {
        if(specialEffect.getAssetPackageId().length() == 0) {
            return;
        }
        for (int i = 0;i<mVideoTrack.getClipCount();i++) {
            NvsVideoClip videoClip = mVideoTrack.getClipByIndex(i);
            mTimeline.addAnimatedSticker(videoClip.getInPoint(),(videoClip.getOutPoint() - videoClip.getInPoint()),specialEffect.getAssetPackageId().toString());
        }
    }

    @Override
    public void removeAnimatedSticker() {
        NvsTimelineAnimatedSticker sticker = mTimeline.getFirstAnimatedSticker();
        while (sticker != null) {
            sticker = mTimeline.removeAnimatedSticker(sticker);
        }
    }

    @Override
    public void applyCaptionStyle(SpecialEffect specialEffect) {
        if(specialEffect.getAssetPackageId().length() == 0) {
            return;
        }
        NvsTimelineCaption caption = mTimeline.getFirstCaption();
        while (caption!=null) {
            caption.applyCaptionStyle(specialEffect.getAssetPackageId().toString());
            caption = mTimeline.getNextCaption(caption);
        }
    }

    @Override
    public void removeCaptionStyle() {
        NvsTimelineCaption caption = mTimeline.getFirstCaption();
        while (caption!=null) {
            caption.applyCaptionStyle("");
            caption = mTimeline.getNextCaption(caption);
        }
    }

    @Override
    public void addCaption(SpecialEffect specialEffect) {
//        for(int i = 0;i<mVideoTrack.getClipCount();i++) {
//            NvsVideoClip videoClip = mVideoTrack.getClipByIndex(i);
//
//
//        }

        CaptionEffect captionEffect = specialEffect.getCaptionEffect();
        if (captionEffect!=null&&!TextUtils.isEmpty(captionEffect.getContent())){
            System.out.println(captionEffect.getStartPoint()+"startpoint");
            mTimeline.addCaption(captionEffect.getContent(),captionEffect.getStartPoint(),captionEffect.getEndPoint()-captionEffect.getStartPoint(), TextUtils.isEmpty(specialEffect.getAssetPackageId())?null:specialEffect.getAssetPackageId().toString());
        }
//        NvsTimelineCaption caption = mTimeline.getFirstCaption();
//        while (caption!=null) {
//            caption.setTextColor(new NvsColor(0, 1, 1,1));
//            caption.setFontSize(30);
//            caption.setBold(true);
//            caption.setDrawOutline(true);
//            caption.setItalic(true);
//            caption.setOutlineColor(new NvsColor(1, 0, 1,1));
//            caption.setCaptionTranslation(new PointF(0,0));
//            caption.setDrawShadow(true);
//            caption = mTimeline.getNextCaption(caption);
//
//        }
    }

    @Override
    public void removeCaption() {
        NvsTimelineCaption caption = mTimeline.getFirstCaption();
        while (caption!=null) {
            caption = mTimeline.removeCaption(caption);
        }
    }

    @Override
    public void addFilterEffect(SpecialEffect specialEffect) {
        if(mVideoTrack.getClipCount() <= 0||specialEffect.getFilterEffect()==null)
            return;
        String fxName = specialEffect.getFilterEffect().getName();
        for(int i = 0;i<mVideoTrack.getClipCount();i++) {
            NvsVideoClip videoClip = mVideoTrack.getClipByIndex(i);
            videoClip.removeAllFx();
            if ((fxName == "None")) {
                continue;
            }else if (fxName == "Beauty") {
                videoClip.appendBeautyFx();
            }
            else {
                videoClip.appendBuiltinFx(fxName);
            }

        }
    }

    @Override
    public void removeFilterEffect() {
        for(int i = 0;i<mVideoTrack.getClipCount();i++) {
            NvsVideoClip videoClip = mVideoTrack.getClipByIndex(i);
            videoClip.removeAllFx();
        }
    }

    @Override
    public boolean compileTimeline() {
        return mStreamingContext.compileTimeline(mTimeline, 0, mTimeline.getDuration(), mConfig.getCompilePath(), 0, 0, 0);
    }

    /**
     * 流媒体引擎回调
     * @param i
     */

    @Override
    public void onStreamingEngineStateChanged(int i) {

    }

    @Override
    public void onFirstVideoFramePresented(NvsTimeline nvsTimeline) {

    }


    /**
     * 打包，压缩回调
     * @param nvsTimeline
     * @param i
     */
    @Override
    public void onCompileProgress(NvsTimeline nvsTimeline, int i) {

    }

    @Override
    public void onCompileFinished(NvsTimeline nvsTimeline) {

    }

    @Override
    public void onCompileFailed(NvsTimeline nvsTimeline) {

    }

}
