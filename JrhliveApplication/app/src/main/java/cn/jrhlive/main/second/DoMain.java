package cn.jrhlive.main.second;

import java.util.List;

import cn.jrhlive.basemvp.BaseInteractor;
import cn.jrhlive.basemvp.BasePresenter;
import cn.jrhlive.basemvp.BaseView;
import cn.jrhlive.basemvp.RequestCallBack;
import cn.jrhlive.main.entity.MainItem;

/**
 *
 * desc:
 * Created by jiarh on 16/11/14 11:06.
 */

public interface DoMain <T>{
    /**
     * 展示view 的界面操作
     */
    interface MainView extends BaseView{
        void initViewData(List<MainItem> datas);
    }

    /**
     * 业务逻辑扩展
     * 1:获取原始数据<br/>
     * 2:是否对数据做过滤操作<br/>
     * 3:数据过滤处理
     * 4:是否提示Toast消息弹出
     */
    interface MainPresenter extends BasePresenter{
        void getDatas();
        boolean isFilter();
        boolean isShowToast();

    }

    /**
     * 数据调度与处理
     * @param <T>
     */
    interface MainInteractor<T> extends BaseInteractor {
        List<MainItem> getFiliterData(List<MainItem> oldList);
        void loadMainItems(RequestCallBack<T> callBack);
    }
}
