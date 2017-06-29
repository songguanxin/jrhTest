package cn.jrhlive.meishe.presenter;

import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsVideoTrack;

import cn.jrhlive.meishe.bean.SpecialEffect;

/**
 * desc: 对一个Timleline一个管理
 * 1:创建时间线
 * 2：添加轨道
 * 3：添加视频，图片资源片段
 * 4：添加特效
 *   # 添加主题、移除主题
 *
 * 5：打包文件
 * <p>
 * Created by jiarh on 17/5/10 11:20.
 */

public interface ShortVideoManagePresenter {

    void init(ShortVideoConfig config);

    /**
     * 创建Timeline
     */
    void createTimeline();

    /**
     * 添加视频轨道
     *
     * @return
     */
    NvsVideoTrack appendVideoTrack();

    NvsTimeline getTimeline();

    NvsStreamingContext getNvsStreamingContext();

    NvsVideoTrack getVideoTrack();

    /**
     * 添加视频片段
     */
    void appendVideoClip();

    /**
     * 安装资源包
     * @return
     */
    int installAssetPackage(SpecialEffect specialEffect);

    void addTheme(SpecialEffect specialEffect);
    void removeTheme();

    void addAnimatedSticker(SpecialEffect specialEffect);
    void removeAnimatedSticker();

    void applyCaptionStyle(SpecialEffect specialEffect);
    void removeCaptionStyle();

    void addCaption(SpecialEffect specialEffect);
    void removeCaption();

    void addFilterEffect(SpecialEffect specialEffect);
    void removeFilterEffect();

    /**
     * 生成文件
     */
    boolean compileTimeline();


}
