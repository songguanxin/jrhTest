package cn.jrhlive.meishe.ui;

import java.util.List;

import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.meishe.bean.ParamShortVideo;

public class EditCaptionActivity extends BaseActivity {

    EditCaptionFragment mEditfragment;

    List<String> mClipPaths ;

    @Override
    protected void initEvent() {

    }



    @Override
    protected void initView() {

        ParamShortVideo paramShortVideo = getIntent().getParcelableExtra(ParamShortVideo.PARAM_SHORT_VIDEO);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,EditCaptionFragment.newInstance(paramShortVideo)).commit();

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_edit_caption;
    }
}
