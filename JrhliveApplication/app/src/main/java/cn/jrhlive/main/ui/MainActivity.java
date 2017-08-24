package cn.jrhlive.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.jrhlibrary.utils.ActivityUtils;
import com.jrhlibrary.widgets.recyclerview.adapter.CommonAdapter;
import com.jrhlibrary.widgets.recyclerview.adapter.MultiItemTypeAdapter;
import com.jrhlibrary.widgets.recyclerview.base.ViewHolder;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.activity.CutActivity;
import cn.jrhlive.activity.ScrollActivity;
import cn.jrhlive.ani.AnimationActivity;
import cn.jrhlive.ani.BallDownUpActivity;
import cn.jrhlive.ani.BoundAniActivity;
import cn.jrhlive.bottomnavigationbar.BottomNavigationBarActivity;
import cn.jrhlive.constraintLayout.ConstraintLayoutActivity;
import cn.jrhlive.eventbus.MainEventBusActivity;
import cn.jrhlive.imgcrop.ImageCropActivity;
import cn.jrhlive.kenburnsview.KenBurnsViewActivity;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.imp.MainInteractorImp;
import cn.jrhlive.main.first.presenter.imp.MainPresenterImp;
import cn.jrhlive.main.first.view.MainView;
import cn.jrhlive.main.second.imp.SMainInteractorImp;
import cn.jrhlive.main.second.imp.SMainPresenterImp;
import cn.jrhlive.meishe.MeisheActivity;
import cn.jrhlive.nestscrolling.NestScrollActivity;
import cn.jrhlive.qiniuplayer.activity.PlayerMainActivity;
import cn.jrhlive.qupai.QupaiActivity;
import cn.jrhlive.recycleview.MultiItemRecycleViewActivity;
import cn.jrhlive.retrofit.RetrofitActivity;
import cn.jrhlive.ritchtext.RitchTextActivity;
import cn.jrhlive.rxandroid.MainRxAndroidActivity;
import cn.jrhlive.surfaceview.SurfaceViewActivity;
import cn.jrhlive.svg.SvgActivity;
import cn.jrhlive.test.NavigationActivity;
import cn.jrhlive.test.TableActivity;
import cn.jrhlive.test.TestActivity;
import cn.jrhlive.utils.ToastUtil;
import cn.jrhlive.widget.bezeiercurve.BezeierCurveActivity;


public class MainActivity extends BaseActivity implements MainView {

    private static final String TAG = "MainActivity";

    MainPresenterImp mainPresenterImp;
    @BindView(R.id.rc)
    RecyclerView rc;

    CommonAdapter<MainItem> mAdapter;
    List<MainItem> listDatas  = new ArrayList<>();

    SMainPresenterImp sMainPresenterImp;

    @Override
    protected void initEvent() {
        first();
//        second();
        webIn();
    }

    private void webIn() {
        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");

                ToastUtil.showMessage("name="+name+";age="+age);
            }
        }
    }

    /**
     * 方式二
     */
    private void second() {
        sMainPresenterImp = new SMainPresenterImp(new SMainInteractorImp());
        sMainPresenterImp.onAttachView(this);
        sMainPresenterImp.onCreate();
    }

    /**
     * 方式一
     */
    private void first() {
        mainPresenterImp = new MainPresenterImp(new MainInteractorImp());
        mainPresenterImp.onAttachView(this);
        mainPresenterImp.onCreate();
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.addItemDecoration(new PinnedHeaderItemDecoration.Builder(0).setDividerId(R.drawable.diver).enableDivider(true).create());
        mAdapter = new CommonAdapter<MainItem>(this,R.layout.common_item_view,listDatas) {
            @Override
            protected void convert(ViewHolder holder, MainItem mainItem, int positioin) {
                TextView tv = holder.getView(R.id.tv_item);
                tv.setText(mainItem.getItemName());
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MainItem>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MainItem o, int position) {
                switch (o.getId()){
                    case 0:
                        ActivityUtils.startActivity(MainActivity.this, NavigationActivity.class);
                        break;
                    case 1:
                        ActivityUtils.startActivity(MainActivity.this, AnimationActivity.class);
                        break;
                    case 2:
                        ActivityUtils.startActivity(MainActivity.this, SurfaceViewActivity.class);
                        break;
                    case 3:
                        ActivityUtils.startActivity(MainActivity.this, SvgActivity.class);
                        break;
                    case 4:
                        ActivityUtils.startActivity(MainActivity.this, ScrollActivity.class);
                        break;
                    case 5:
                        ActivityUtils.startActivity(MainActivity.this, DoubleClickTestActivity.class);
                        break;
                    case 6:
                        ActivityUtils.startActivity(MainActivity.this, ChromeTabsActivity.class);
                        break;
                    case 7:
                        ActivityUtils.startActivity(MainActivity.this, MainEventBusActivity.class);
                        break;
                    case 8:
                        ActivityUtils.startActivity(MainActivity.this, MainRxAndroidActivity.class);
                        break;
                    case 9:
                        ActivityUtils.startActivity(MainActivity.this, RetrofitActivity.class);
                        break;
                    case 10:
                        ActivityUtils.startActivity(MainActivity.this, KenBurnsViewActivity.class);
                        break;
                    case 11:
                        ActivityUtils.startActivity(MainActivity.this, ConstraintLayoutActivity.class);
                        break;
                    case 12:
                        ActivityUtils.startActivity(MainActivity.this, BallDownUpActivity.class);
                        break;
                    case 13:
                        ActivityUtils.startActivity(MainActivity.this, TestActivity.class);
                        break;
                    case 14:
                        ActivityUtils.startActivity(MainActivity.this, PlayerMainActivity.class);
                        break;
                    case 15:
                        ActivityUtils.startActivity(MainActivity.this, QupaiActivity.class);
                        break;
                    case 16:
                        ActivityUtils.startActivity(MainActivity.this, NestScrollActivity.class);
                        break;
                    case 17:
                        ActivityUtils.startActivity(MainActivity.this,BottomNavigationBarActivity.class);
                        break;
                    case 18:
                        ActivityUtils.startActivity(MainActivity.this,RitchTextActivity.class);
                        break;
                    case 19:
                        ActivityUtils.startActivity(MainActivity.this,ImageCropActivity.class);
                        break;
                    case 20:
                        ActivityUtils.startActivity(MainActivity.this,TableActivity.class);
                        break;
                    case 21:
                        ActivityUtils.startActivity(MainActivity.this,MultiItemRecycleViewActivity.class);
                        break;
                    case 22:
                        ActivityUtils.startActivity(MainActivity.this,BezeierCurveActivity.class);
                        break;
                    case 23:
                        ActivityUtils.startActivity(MainActivity.this,MeisheActivity.class);
                        break;
                    case 24:
                        ActivityUtils.startActivity(MainActivity.this,CutActivity.class);
                        break;
                    case 25:
                        ActivityUtils.startActivity(MainActivity.this,BoundAniActivity.class);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MainItem o, int position) {
                return false;
            }
        });
        rc.setAdapter(mAdapter);
    }

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }
    /**
     * 数据回调 展示
     * @param datas
     */
    @Override
    public void initViewData(List<MainItem> datas) {

        if (datas!=null){
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
        if (StringUtils.isEmpty(msg)) return;
        ToastUtil.showMessage(msg);
    }

}
