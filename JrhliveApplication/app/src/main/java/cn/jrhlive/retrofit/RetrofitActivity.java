package cn.jrhlive.retrofit;

import android.widget.EditText;

import com.jrhlibrary.utils.ListUtils;

import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.retrofit.bean.JokeBean;
import cn.jrhlive.retrofit.presenter.JokePresenter;
import cn.jrhlive.retrofit.presenter.lmpl.JokeRxPresenterImpl;
import cn.jrhlive.retrofit.view.RetrofitView;

//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RetrofitActivity extends BaseActivity implements RetrofitView{


    @BindView(R.id.textView6)
    EditText textView6;

    StringBuffer sb ;

    JokePresenter jokePresenter;

    @Override
    protected void initEvent() {

        sb = new StringBuffer();

//
//        jokePresenter = new JokePresenterImpl();
//        jokePresenter.onAttachView(this);
//        jokePresenter.getData();

        new JokeRxPresenterImpl(this).doGetData();

    }


    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_retrofit;
    }


    @Override
    public void showData(List<JokeBean.ResultEntity.DataEntity> dataEntities) {

        if (ListUtils.isNotEmplyList(dataEntities))
            for (JokeBean.ResultEntity.DataEntity dataEntity : dataEntities) {
                sb.append(dataEntity.getContent());
            }
        textView6.setText(sb.toString());

    }


}
