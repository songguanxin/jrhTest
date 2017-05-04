package cn.jrhlive.qiniuplayer.activity;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.qiniuplayer.VideoUrls;
import cn.jrhlive.qiniuplayer.widget.MediaController;

public class PlayerMainActivity extends BaseActivity implements PLMediaPlayer.OnPreparedListener, PLMediaPlayer.OnInfoListener, PLMediaPlayer.OnCompletionListener, PLMediaPlayer.OnVideoSizeChangedListener, PLMediaPlayer.OnErrorListener {


    @BindView(R.id.plv_tvideo)
    PLVideoTextureView plvTvideo;

    @Override
    protected void initEvent() {
        setOptions(0);
        plvTvideo.setOnPreparedListener(this);
        plvTvideo.setOnInfoListener(this);
        plvTvideo.setOnCompletionListener(this);
        plvTvideo.setOnVideoSizeChangedListener(this);
        plvTvideo.setOnErrorListener(this);

        plvTvideo.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_FIT_PARENT);

        plvTvideo.setVideoPath(VideoUrls.VIDEO_PATH);
        plvTvideo.start();
    }

    @Override
    protected void initView() {

        MediaController mediaController = new MediaController(this);
        plvTvideo.setBufferingIndicator(mediaController);

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_player_main;
    }

    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }


    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
//        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
//        if (mIsLiveStreaming == 1) {
//            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
//        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        plvTvideo.setAVOptions(options);
    }
}
