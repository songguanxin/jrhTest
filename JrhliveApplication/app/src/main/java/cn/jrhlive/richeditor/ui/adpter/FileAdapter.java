package cn.jrhlive.richeditor.ui.adpter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.jrhlive.R;
import cn.jrhlive.richeditor.bean.FileInfo;

/**
 * desc:
 * Created by jiarh on 17/9/8 09:44.
 */

public class FileAdapter  extends BaseQuickAdapter<FileInfo,BaseViewHolder>{


    public FileAdapter(@LayoutRes int layoutResId, @Nullable List<FileInfo> data) {
        super(R.layout.common_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileInfo item) {

        helper.setText(R.id.tv_item,item.getName());
    }
}
