package cn.jrhlive.meishe.ui;

import android.content.Intent;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.meishe.bean.ParamShortVideo;


/**
 * desc:
 * Created by jiarh on 17/5/5 10:30.
 */

public class CaptionFragment extends ShortVideoParentFragment {
    @BindView(R.id.tv_caption)
    TextView tvCaption;
    boolean add;

    ParamShortVideo paramShortVideo;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_caption;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initEvent() {

    }


    @OnClick(R.id.tv_caption)
    public void onViewClicked() {
//        SpecialEffect s = new SpecialEffect("1DC56C2A-0F4A-4C06-8ABC-DF41C89E33A1.captionstyle");
//        s.setAddCaption(!add);
//        RxBus.getDefault().post(s);
//        add=!add;

        Intent intent = new Intent(getActivity(),CaptionEditActivity.class);
        intent.putExtra(ParamShortVideo.PARAM_SHORT_VIDEO,paramShortVideo);
        startActivity(intent);
    }

    public void setParamShortVideo(ParamShortVideo paramShortVideo) {
        this.paramShortVideo = paramShortVideo;
    }
}
