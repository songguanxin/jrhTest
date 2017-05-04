package cn.jrhlive.bottomnavigationbar;

import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class BottomNavigationBarActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.bn_bar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {
        tvContent.setText("第"+0+"界面");
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Books").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Movies & TV").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Games").setActiveColorResource(R.color.red))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_bottom_navigation_bar;
    }


    @Override
    public void onTabSelected(int position) {
        tvContent.setText("第"+position+"界面");
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
