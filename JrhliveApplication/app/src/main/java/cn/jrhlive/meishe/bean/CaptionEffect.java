package cn.jrhlive.meishe.bean;

/**
 * desc: 字幕特效
 * Created by jiarh on 17/5/16 14:42.
 */

public class CaptionEffect {

    private String content;
    private long startPoint;
    private long endPoint;

    public CaptionEffect(String content, long startPoint, long endPoint) {
        this.content = content;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(long startPoint) {
        this.startPoint = startPoint;
    }

    public long getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(long endPoint) {
        this.endPoint = endPoint;
    }
}
