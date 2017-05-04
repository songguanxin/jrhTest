package cn.jrhlive.recycleview;

import java.util.ArrayList;
import java.util.List;

import cn.jrhlive.recycleview.bean.MultiItemBean;

/**
 * desc:
 * Created by jiarh on 17/4/27 11:26.
 */

public class MultiItemProviderImpl {

    public List<MultiItemBean> getDatas(){

        List<MultiItemBean> data = new ArrayList<>();

        data.add(new MultiItemBean(0));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));

        data.add(new MultiItemBean(0));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(1));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));
        data.add(new MultiItemBean(2));


        return data;
    }
}
