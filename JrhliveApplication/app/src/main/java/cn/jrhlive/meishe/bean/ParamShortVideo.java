package cn.jrhlive.meishe.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 17/5/15 15:05.
 */

public class ParamShortVideo implements Parcelable {

    public static  final String PARAM_SHORT_VIDEO="param_shortvideo";
    private List<String> mPaths;

    public List<String> getmPaths() {
        return mPaths;
    }

    public void setmPaths(List<String> mPaths) {
        this.mPaths = mPaths;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mPaths);
    }

    public ParamShortVideo() {
    }

    protected ParamShortVideo(Parcel in) {
        this.mPaths = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ParamShortVideo> CREATOR = new Parcelable.Creator<ParamShortVideo>() {
        @Override
        public ParamShortVideo createFromParcel(Parcel source) {
            return new ParamShortVideo(source);
        }

        @Override
        public ParamShortVideo[] newArray(int size) {
            return new ParamShortVideo[size];
        }
    };
}
