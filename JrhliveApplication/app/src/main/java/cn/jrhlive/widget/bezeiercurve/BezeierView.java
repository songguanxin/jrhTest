package cn.jrhlive.widget.bezeiercurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * desc:
 * Created by jiarh on 17/4/27 21:10.
 */

public class BezeierView extends View {

    Point mStartPoint;
    Point mEndPoint;
    Point mAnchorPoint1;
    Point mAnchorPoint2;

    Paint mPaint;
    Path mPath;


    public BezeierView(Context context) {
        this(context, null);
    }

    public BezeierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezeierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);

        mPath =new Path();


    }

    public void refreshView(Point mStartPoint, Point mEndPoint, Point mAnchorPoint1, Point mAnchorPoint2) {
        this.mStartPoint=mStartPoint;
        this.mEndPoint = mEndPoint;
        this.mAnchorPoint1 = mAnchorPoint1;
        this.mAnchorPoint2 = mAnchorPoint2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        if (mStartPoint==null||mEndPoint==null||mAnchorPoint2==null||mAnchorPoint1==null)return;
        mPath.moveTo(mStartPoint.x,mStartPoint.y);
        mPath.cubicTo(mAnchorPoint1.x,mAnchorPoint1.y,mAnchorPoint2.x,mAnchorPoint2.y,mEndPoint.x,mEndPoint.y);
        canvas.drawPath(mPath,mPaint);
        canvas.drawPoint(mAnchorPoint1.x, mAnchorPoint1.y, mPaint);
        canvas.drawPoint(mAnchorPoint2.x, mAnchorPoint2.y, mPaint);

        //画线
//        canvas.drawLine(mStartPoint.x, mStartPoint.y, mAnchorPoint1.x, mAnchorPoint1.y, mPaint);
//        canvas.drawLine(mEndPoint.x, mEndPoint.y, mAnchorPoint2.x, mAnchorPoint2.y, mPaint);
//        canvas.drawLine(mAnchorPoint1.x, mAnchorPoint1.y, mAnchorPoint2.x, mAnchorPoint2.y, mPaint);
    }
}
