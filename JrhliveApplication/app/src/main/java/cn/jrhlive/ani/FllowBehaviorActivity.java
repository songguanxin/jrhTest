package cn.jrhlive.ani;

import android.view.MotionEvent;
import android.view.View;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class FllowBehaviorActivity extends BaseActivity implements View.OnTouchListener {


    @BindView(R.id.first)
    View first;
    @BindView(R.id.second)
    View second;

    @Override
    protected void initEvent() {

        first.setOnTouchListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_fllow_behavior;
    }

    float startX=0,startY=0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                startX= event.getRawX();
                startY= event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX()-startX;
                float deltaY = event.getRawY()-startY;
                startX=event.getRawX();
                startY=event.getRawY();
                ((View)v.getParent()).scrollBy((int)-deltaX,(int)-deltaY);
                break;

        }
        return true;
    }
}
