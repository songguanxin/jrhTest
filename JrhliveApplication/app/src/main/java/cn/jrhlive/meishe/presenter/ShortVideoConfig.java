package cn.jrhlive.meishe.presenter;

import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsStreamingContext;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 17/5/10 11:10.
 */

public class ShortVideoConfig {

    /**
     * 视频解析 图片宽度 4的倍数
     */
    private int imgWidth;
    /**
     * 视频解析 图片高度 2的倍数   imgWidth*imgHeight < 1920*1080
     */
    private int imgHeight;

    /**
     * 像素比
     */
    private NvsRational imgPAR ;

    /**
     * 视频帧率
     */
    private NvsRational videoFps ;

    /**
     * 音频采样率 目前支持两种：44100 48000
     */
    private int sampleRate;

    /**
     * 音频声道数
     */
    private int channelCount;

    /**
     * 视频文件导出路径
     */
    private String compilePath;

    /**
     * 生成文件输出的视频分辨率
     */
    private int videoResolutionGrade ;

    /**
     * 生成文件输出的视频码率
     */
    private int videoBitrateGrade ;

    /**
     * 视频片段
     */
    private List<String> videoClipPaths ;

    private ShortVideoConfig(Builder builder) {
        imgWidth = builder.imgWidth;
        imgHeight = builder.imgHeight;
        imgPAR = builder.imgPAR;
        videoFps = builder.videoFps;
        sampleRate = builder.sampleRate;
        channelCount = builder.channelCount;
        compilePath = builder.compilePath;
        videoResolutionGrade = builder.videoResolutionGrade;
        videoBitrateGrade = builder.videoBitrateGrade;
        videoClipPaths = builder.videoClipPaths;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public NvsRational getImgPAR() {
        return imgPAR;
    }

    public NvsRational getVideoFps() {
        return videoFps;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public String getCompilePath() {
        return compilePath;
    }

    public int getVideoResolutionGrade() {
        return videoResolutionGrade;
    }

    public int getVideoBitrateGrade() {
        return videoBitrateGrade;
    }

    public List<String> getVideoClipPaths() {
        return videoClipPaths;
    }

    public static final class Builder {
        private int imgWidth = ShortVideoConstant.VIDEO_IMAGE_WIDTH;
        private int imgHeight = ShortVideoConstant.VIDEO_IMAGE_HEIGHT;
        private NvsRational imgPAR = new NvsRational(1,1);
        private NvsRational videoFps= new NvsRational(25,1);
        private int sampleRate = ShortVideoConstant.AUDIO_RES_SIMPLE_RATE_44100;
        private int channelCount=2;
        private String compilePath;
        private int videoResolutionGrade= NvsStreamingContext.COMPILE_VIDEO_RESOLUTION_GRADE_720;
        private int videoBitrateGrade= NvsStreamingContext.COMPILE_BITRATE_GRADE_HIGH;
        private List<String> videoClipPaths;

        public Builder() {
        }

        public Builder imgWidth(int val) {
            imgWidth = val;
            return this;
        }

        public Builder imgHeight(int val) {
            imgHeight = val;
            return this;
        }

        public Builder imgPAR(NvsRational val) {
            imgPAR = val;
            return this;
        }

        public Builder videoFps(NvsRational val) {
            videoFps = val;
            return this;
        }

        public Builder sampleRate(int val) {
            sampleRate = val;
            return this;
        }

        public Builder channelCount(int val) {
            channelCount = val;
            return this;
        }

        public Builder compilePath(String val) {
            compilePath = val;
            return this;
        }

        public Builder videoResolutionGrade(int val) {
            videoResolutionGrade = val;
            return this;
        }

        public Builder videoBitrateGrade(int val) {
            videoBitrateGrade = val;
            return this;
        }

        public Builder videoClipPaths(List<String> val) {
            videoClipPaths = val;
            return this;
        }

        public ShortVideoConfig build() {
            return new ShortVideoConfig(this);
        }
    }
}
