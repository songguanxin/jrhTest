package cn.jrhlive.meishe.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.meicam.sdk.NvsStreamingContext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.meishe.bean.FilterEffect;
import cn.jrhlive.meishe.bean.SpecialEffect;


/**
 * desc:
 * Created by jiarh on 17/5/5 10:30.
 */

public class HighLevelFragment extends ShortVideoParentFragment implements AdapterView.OnItemClickListener {

    List<String> mFxArrayList;
    @BindView(R.id.lv_fx)
    ListView lvFx;
    Unbinder unbinder;


    @Override
    public int getLayoutId() {

        return R.layout.fragment_high_level;
    }



    @Override
    public void initView() {
        mFxArrayList = new ArrayList();
        mFxArrayList.add("None");
        mFxArrayList.add("Beauty");
        mFxArrayList.addAll(NvsStreamingContext.getInstance().getAllBuiltinCaptureVideoFxNames());
        ArrayAdapter<String> fxAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mFxArrayList);
        lvFx.setAdapter(fxAdapter);
        lvFx.setOnItemClickListener(this);
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SpecialEffect specialEffect = new SpecialEffect("7FFCF99A-5336-4464-BACD-9D32D5D2DC5E.videofx");
        specialEffect.setFilterEffect(new FilterEffect(mFxArrayList.get(position)));
        RxBus.getDefault().post(specialEffect);
    }
}
