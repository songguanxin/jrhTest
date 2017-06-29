package cn.jrhlive.meishe.presenter;

import cn.jrhlive.basemvp.BasePresenter;
import cn.jrhlive.meishe.bean.SpecialEffect;

/**
 * desc: 短视频
 * 播放，暂停，停止，快进/快退
 * Created by jiarh on 17/5/5 17:31.
 */

public interface VideoPresenter extends BasePresenter {

    void init();

    void setVideoManager(ShortVideoManagePresenter shortVideoManagePresenter);

    /**
     * 播放
     */
    void playbackTimeline(int startPosition,int endPosition);

    /**
     * 停止
     */
    void stopPlay();

    /**
     * 快进/
     */
    void seekTimeline(int progress);

    void  updateSpecialEffect(SpecialEffect specialEffect);
    void   removeSpecialEffec(SpecialEffect specialEffect);
    void closeStreaming();

}
