package cn.jrhlive.meishe.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.jrhlive.R;


/**
 * 左侧、右侧、中间。
 * desc: 使用在字幕调解界面
 * Created by jiarh on 17/6/30 14:30.
 */

public class CaptionSquenceGroupLayout extends ViewGroup {

    private Context mContext;
    private View leftView;
    private View rightView;
    private View MidView;

    private int ivWidth;

    public CaptionSquenceGroupLayout(Context context) {
        this(context, null);
    }

    public CaptionSquenceGroupLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaptionSquenceGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setIvWidth(int ivWidth) {
        this.ivWidth = ivWidth;
    }

    public void init(Context context) {

        this.mContext = context;
        Bitmap iv = BitmapFactory.decodeResource(getResources(), R.drawable.scoller);
        ivWidth = iv.getWidth();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int twidth = sizeWidth;
        int theight = sizeHeight;

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            int cHeight = childView.getMeasuredHeight();
            twidth += cWidth;
            theight = cHeight;
        }
        twidth -= 2 * ivWidth;
        setMeasuredDimension(twidth, theight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        leftView = getChildAt(0);
        rightView = getChildAt(1);
        MidView = getChildAt(2);

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            int cl = 0, ct = 0, cr = 0, cb = 0;
            switch (i) {
                case 0:
                    cl=0;
                    break;
                case 1:
                    cl=leftView.getMeasuredWidth()+MidView.getMeasuredWidth()-2*ivWidth;
                    break;
                case 2:
                    cl=leftView.getMeasuredWidth()-ivWidth;
                    break;

            }
            cr = cl+cWidth;
            cb = ct+cHeight;
            childView.layout(cl,ct,cr,cb);
        }
    }
}
