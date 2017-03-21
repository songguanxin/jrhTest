package cn.jrhlive.ani;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SearchBarActivity extends BaseActivity {


    @BindView(R.id.sv_search)
    EditText svSearch;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_search_bar;
    }



    @OnClick(R.id.sv_search)
    public void onClick() {
        Intent intent = new Intent(this, SearchDetailActivity.class);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(svSearch, svSearch.getTransitionName()));
        startActivity(intent, activityOptions.toBundle());
    }
}
