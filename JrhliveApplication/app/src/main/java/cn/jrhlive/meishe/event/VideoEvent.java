package cn.jrhlive.meishe.event;

/**
 * desc: 视频播放状态
 * Created by jiarh on 17/5/16 13:56.
 */

public class VideoEvent {
    public static final  int PLAYING =0x2000;
    public static final  int STOP =0x2001;
    public static final  int END =0x2002;

    private int sate;
    public VideoEvent(int state) {
        this.sate = state;
    }

    public int getSate() {
        return sate;
    }

    public void setSate(int sate) {
        this.sate = sate;
    }
}
