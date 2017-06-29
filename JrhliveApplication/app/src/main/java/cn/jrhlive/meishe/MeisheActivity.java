package cn.jrhlive.meishe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jrhlibrary.utils.PermissionUtil;
import com.jrhlibrary.utils.UriToPathUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.meishe.bean.ParamShortVideo;
import cn.jrhlive.meishe.ui.MeisheMainFragment;
import cn.jrhlive.meishe.ui.VideoFragment;

public class MeisheActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    public static final int REQUEST_CODE_CHOOSE=0x1000;

    @BindView(R.id.container_video_view)
    FrameLayout containerVideoView;
    @BindView(R.id.container_config_view)
    FrameLayout containerConfigView;
    @BindView(R.id.bottom_bar)
    BottomNavigationBar bottomNavigationBar;

    MeisheMainFragment mMeisheFragment;
    VideoFragment mVideoFragment;

    List<Uri> uris;
    List<String> mPaths;
    private final static String[] CAMERA_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        initTopView();
        initBottomView();
        initMiddleView();

    }

    private void initTopView() {
        mVideoFragment = new VideoFragment();
        loadFrgment(R.id.container_video_view,mVideoFragment);
    }

    private void initMiddleView() {
        mMeisheFragment = new MeisheMainFragment();
        loadFrgment(R.id.container_config_view,mMeisheFragment);

    }


    private void initBottomView() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "视频录制").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "制作影片").setActiveColorResource(R.color.green))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_meishe;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {

            case 0:
                break;
            case 1:
                selectPicVideos();
                break;

        }
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        switch (position) {

            case 0:
                break;
            case 1:
                selectPicVideos();
                break;

        }
    }

    private void selectPicVideos() {
        if (!PermissionUtil.check(this, CAMERA_PERMISSIONS)) {
            PermissionUtil.showTips(this, CAMERA_PERMISSIONS);
            return ;
        }
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            uris = Matisse.obtainResult(data);
            mPaths = new ArrayList<>(uris.size());
            for (Uri uri : uris) {
                mPaths.add(UriToPathUtil.getRealFilePath(MeisheActivity.this,uri));
            }
            mVideoFragment.initVideoData(mPaths);

            ParamShortVideo paramShortVideo = new ParamShortVideo();
            paramShortVideo.setmPaths(mPaths);
            mMeisheFragment.setParamShortVideo(paramShortVideo);
        }
    }


}
