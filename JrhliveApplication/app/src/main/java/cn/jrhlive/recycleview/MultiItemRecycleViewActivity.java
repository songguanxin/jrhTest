package cn.jrhlive.recycleview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.recycleview.adapter.MultiItemAdapter;

public class MultiItemRecycleViewActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    MultiItemAdapter mAdapter;
    MultiItemProviderImpl multiItemProvider;

    @Override
    protected void initEvent() {


    }

    @Override
    protected void initView() {
        multiItemProvider = new MultiItemProviderImpl();
        mAdapter = new MultiItemAdapter(this,multiItemProvider.getDatas());
        GridLayoutManager manager = new GridLayoutManager(rv.getContext(),4, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_multi_item_recycle_view;
    }

}
