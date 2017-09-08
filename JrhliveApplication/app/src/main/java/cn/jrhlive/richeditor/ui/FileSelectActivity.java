package cn.jrhlive.richeditor.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.richeditor.bean.FileInfo;
import cn.jrhlive.richeditor.constant.RichorConstant;
import cn.jrhlive.richeditor.events.Event;
import cn.jrhlive.richeditor.ui.adpter.FileAdapter;
import cn.jrhlive.utils.ToastUtil;

//import com.jrhlibrary.widgets.recyclerview.adapter.CommonAdapter;

public class FileSelectActivity extends BaseActivity {


    @BindView(R.id.rc_file)
    RecyclerView rcFile;
    FileAdapter fileAdapter;
    List<FileInfo> fileInfos = new ArrayList<>();

    @Override
    protected void initEvent() {

        File file = new File(RichorConstant.FILE_FOLDER);
        if (!file.exists()){
            ToastUtil.showMessage("还没有操作记录");
            return;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            fileInfos.add(new FileInfo(f.getName(), f.getPath()));
        }

        rcFile.setLayoutManager(new LinearLayoutManager(this));
//        rcFile.addItemDecoration(new PinnedHeaderItemDecoration.Builder(0).setDividerId(R.drawable.diver).enableDivider(true).create());

        fileAdapter = new FileAdapter(0,fileInfos);
        rcFile.setAdapter(fileAdapter);
        fileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showMessage(position+"");
                RxBus.getDefault().post(new Event<>(RichorConstant.FILE_RECOVER, fileInfos.get(position)));
                finish();
            }
        });

    }

    @Override
    protected void initView() {


    }

    @Override
    protected int getViewId() {
        return R.layout.activity_file_select;
    }


}
