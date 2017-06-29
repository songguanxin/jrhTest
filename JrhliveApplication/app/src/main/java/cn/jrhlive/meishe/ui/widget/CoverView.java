package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.jrhlive.R;

/**
 * desc: 字幕调解封面
 *
 * Created by jiarh on 17/5/18 14:17.
 */

public class CoverView extends View {
    private Context mContext;
    //当前指针位置
    private long  currentPoint;

    private long inPoint;
    private long outPoint;


    public  CoverView create(long in,long out){
        this.inPoint = in;
        this.outPoint = out;
        return this;
    }

    public CoverView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setVisibility(INVISIBLE);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.cover_color));
    }

    public CoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public CoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    public CoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView();
    }

    public void toggle(long currentPoint){
        if (currentPoint>=inPoint&&currentPoint<=outPoint){
            setVisibility(getVisibility()==VISIBLE?INVISIBLE:VISIBLE);
        }

    }
}
