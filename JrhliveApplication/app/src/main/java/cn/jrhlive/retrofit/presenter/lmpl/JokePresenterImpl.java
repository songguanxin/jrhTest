package cn.jrhlive.retrofit.presenter.lmpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jrhlive.basemvp.BasePresenterImp;
import cn.jrhlive.retrofit.ApiFactory;
import cn.jrhlive.retrofit.bean.JokeBean;
import cn.jrhlive.retrofit.presenter.JokePresenter;
import cn.jrhlive.retrofit.provide.JokeApi;
import cn.jrhlive.retrofit.view.RetrofitView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * desc:
 * Created by jiarh on 17/1/20 15:24.
 */

public class JokePresenterImpl extends BasePresenterImp<RetrofitView,List<JokeBean.ResultEntity.DataEntity>> implements JokePresenter {


    @Override
    public void getData() {

        Map<String, String> map = new HashMap<>();
        map.put("key", "1051d9f5220cd9e94f94ab4009b8e23f");
        map.put("page", "1");
        map.put("pagesize", "20");
        map.put("sort", "desc");
        map.put("time", System.currentTimeMillis() / 1000 + "");
        ApiFactory.getApi(JokeApi.class).getJoke(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<JokeBean, List<JokeBean.ResultEntity.DataEntity>>() {
                    @Override
                    public List<JokeBean.ResultEntity.DataEntity> apply(JokeBean jokeBean) throws Exception {
                        return jokeBean.getResult().getData();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<JokeBean.ResultEntity.DataEntity>>() {
                    @Override
                    public void accept(List<JokeBean.ResultEntity.DataEntity> dataEntities) throws Exception {
                        if (mView!=null)
                            mView.showData(dataEntities);
                    }
                });
    }
}
