package cn.jrhlive.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.jrhlive.R;
import cn.jrhlive.recycleview.bean.MultiItemBean;
import cn.jrhlive.recycleview.viewholder.BaseViewHolder;
import cn.jrhlive.recycleview.viewholder.OneHolder;
import cn.jrhlive.recycleview.viewholder.ThreeHolder;
import cn.jrhlive.recycleview.viewholder.TwoHolder;

/**
 * desc:
 * Created by jiarh on 17/4/27 10:55.
 */

public class MultiItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<MultiItemBean> mDatas;

    public MultiItemAdapter(Context context, List<MultiItemBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        if (mDatas == null)
            mDatas = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case 0:
                holder = new OneHolder(View.inflate(context, R.layout.multi_one_item, null));
                break;
            case 1:
                holder = new TwoHolder(View.inflate(context, R.layout.multi_two_item, null));
                break;
            case 2:
                holder = new ThreeHolder(View.inflate(context, R.layout.multi_three_item, null));
                break;


        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    switch (getItemViewType(position)){
                        case 0:
                            return 4;
                        case 1:
                            return 1;
                        case 2:
                            return 2;

                    }

                    return 4;
                }
            });
        }
    }
}
