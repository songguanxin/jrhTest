package cn.jrhlive.main.first.view;

import java.util.List;

import cn.jrhlive.basemvp.BaseView;
import cn.jrhlive.main.entity.MainItem;

/**
 * desc:
 * Created by jiarh on 16/9/14 15:59.
 */
public interface MainView extends BaseView {

    void initViewData(List<MainItem> datas);
}
