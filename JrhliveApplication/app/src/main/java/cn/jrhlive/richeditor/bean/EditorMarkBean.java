package cn.jrhlive.richeditor.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by songgx on 2017/10/9.
 */

public class EditorMarkBean implements Serializable{

    public static final int CHANGE=1;
    public static final int DELETE=0;
    private String author;//作者
    private String noChangeText;//原文内容
    private String hasChangeText;//修改后内容
    private int changeType;//修改类型
    private Date date;//日期操作
    private String changeReason;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNoChangeText() {
        return noChangeText;
    }

    public void setNoChangeText(String noChangeText) {
        this.noChangeText = noChangeText;
    }

    public String getHasChangeText() {
        return hasChangeText;
    }

    public void setHasChangeText(String hasChangeText) {
        this.hasChangeText = hasChangeText;
    }

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

}
