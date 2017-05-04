package cn.jrhlive.qupai;

import android.Manifest;
import android.widget.Button;

import com.duanqu.qupai.permission.EasyPermissions;
import com.duanqu.qupai.utils.AppGlobalSetting;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

import static cn.jrhlive.qupai.QuPaiManagerUtil.qupaiService;


public class QupaiActivity extends BaseActivity {

    private static final int RC_PERMISSION_PERM = 1;
    private static final int RC_SETTINGS_SCREEN = 1002;
    @BindView(R.id.btn_record)
    Button btnRecord;
    private float mDurationLimit;
    private float mMinDurationLimit;
    private int mVideoBitrate;
    private int mWaterMark;
    private String waterMarkPath;
    private int beautySkinProgress;


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_qupai;
    }


    private void startRecord() {


        //引导，只显示一次，这里用SharedPreferences记录
        final AppGlobalSetting sp = new AppGlobalSetting(getApplicationContext());
        Boolean isGuideShow = true;

        /**
         * 建议上面的initRecord只在application里面调用一次。这里为了能够开发者直观看到改变所以可以调用多次
         */
        qupaiService.showRecordPage(this, 10001, isGuideShow);
//
//        sp.saveGlobalConfigItem(
//                AppConfig.PREF_VIDEO_EXIST_USER, false);
    }


    /**
     * 支持6.0动态权限
     */
    public void permissionCheck() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            startRecord();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_tips_camera_audio),
                    RC_PERMISSION_PERM, perms);
        }
    }

    @OnClick(R.id.btn_record)
    public void onViewClicked() {
        permissionCheck();
    }
}
