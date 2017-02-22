package cn.jrhlive.retrofit.view;

import java.util.List;

import cn.jrhlive.basemvp.BaseView;
import cn.jrhlive.retrofit.bean.JokeBean;


/**
 * desc:
 * Created by jiarh on 17/1/20 15:20.
 */

public interface RetrofitView extends BaseView {
    void showData(List<JokeBean.ResultEntity.DataEntity> dataEntities);
}
