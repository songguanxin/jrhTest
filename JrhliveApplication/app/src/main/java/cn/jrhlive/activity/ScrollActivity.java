package cn.jrhlive.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jrhlibrary.widgets.recyclerview.adapter.CommonAdapter;
import com.jrhlibrary.widgets.recyclerview.base.ViewHolder;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.imp.MainInteractorImp;
import cn.jrhlive.main.first.presenter.imp.MainPresenterImp;
import cn.jrhlive.main.first.view.MainView;

public class ScrollActivity extends BaseActivity implements MainView {


    @BindView(R.id.recycler_view)
    RecyclerView rc;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    CommonAdapter mAdapter;

    MainPresenterImp mainPresenterImp;
    List<MainItem> listDatas;

    @Override
    protected void initEvent() {

        listDatas = new ArrayList<>();
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.addItemDecoration(new PinnedHeaderItemDecoration.Builder(0).setDividerId(R.drawable.diver).enableDivider(true).create());
        mAdapter = new CommonAdapter<MainItem>(this, R.layout.common_item_view, listDatas) {
            @Override
            protected void convert(ViewHolder holder, MainItem mainItem, int positioin) {
                TextView tv = holder.getView(R.id.tv_item);
                tv.setText(mainItem.getItemName());
            }
        };

        rc.setAdapter(mAdapter);

        mainPresenterImp = new MainPresenterImp(new MainInteractorImp());
        mainPresenterImp.onAttachView(this);
        mainPresenterImp.onCreate();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_scroll;
    }


    @Override
    public void initViewData(List<MainItem> datas) {
        if (datas != null) {
            listDatas.addAll(datas);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void hideProgress() {
        closeDialog();
    }

    @Override
    public void showMsg(String msg) {

    }
}
