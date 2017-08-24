package cn.jrhlive.ani;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class BoundAniActivity extends BaseActivity {


    private static final String TAG = "BoundAniActivity";


    String titles[] = {"hello", "android", "java", "ios"};
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.ll)
    LinearLayout ll;

    GvAdapter mAdapter;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        int count = ll.getChildCount();
        for (int i = 0; i <count ; i++) {
            setani(ll.getChildAt(i),i);
        }

    }

    private void setani(View v, int i) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 1000, 0);
        animator.setDuration(800);
        animator.setStartDelay(200 * i);
        animator.setInterpolator(new BounceInterpolator());
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, animator);
        set.start();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_bound_ani;
    }




    class GvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView tv = new TextView(BoundAniActivity.this);
            tv.setText(titles[position]);
            tv.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            tv.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.light_red));
            setani(tv, position + 1);
            return tv;
        }
    }
}
