package cn.jrhlive.qupai;

import android.hardware.Camera;

import com.duanqu.qupai.engine.session.MovieExportOptions;
import com.duanqu.qupai.engine.session.ProjectOptions;
import com.duanqu.qupai.engine.session.ThumbnailExportOptions;
import com.duanqu.qupai.engine.session.UISettings;
import com.duanqu.qupai.engine.session.VideoSessionCreateInfo;
import com.duanqu.qupai.sdk.android.QupaiManager;
import com.duanqu.qupai.sdk.android.QupaiService;

import cn.jrhlive.JrhApplication;

/**
 * desc:
 * Created by jiarh on 17/3/27 17:31.
 */

public class QuPaiManagerUtil {
    public  static QupaiService qupaiService;

    public static QupaiService getQupaiService() {
        return qupaiService;
    }

    public static void init(){
//        for (String str : new String[]{"gnustl_shared", "qupai-media-thirdparty", "qupai-media-jni"}) {
//            System.loadLibrary(str);
//        }

       qupaiService = QupaiManager
                .getQupaiService(JrhApplication.APP);
        UISettings _UISettings = new UISettings() {

            @Override
            public boolean hasEditor() {
                return true;
            }

            @Override
            public boolean hasImporter() {
                return super.hasImporter();
            }

            @Override
            public boolean hasGuide() {
                return true;
            }

        };

        MovieExportOptions movie_options = new MovieExportOptions.Builder()
                .setVideoBitrate(Constants.DEFAULT_BITRATE)
                .configureMuxer("movflags", "+faststart")
                .setOutputVideoKeyInt(150)
                .setVideoRateCRF(18)
                .setVideoPreset("faster")
                .setOutputVideoLevel(30)
                .setOutputVideoTune("zerolatency")
                .build();

        ProjectOptions projectOptions = new ProjectOptions.Builder()
                .setVideoSize(480, 480)
                .setVideoFrameRate(30)
                .setDurationRange(Constants.DEFAULT_MIN_DURATION_LIMIT, Constants.DEFAULT_DURATION_LIMIT)
                .get();

        ThumbnailExportOptions thumbnailExportOptions =new ThumbnailExportOptions.Builder()
                .setCount(1).get();

        VideoSessionCreateInfo info =new VideoSessionCreateInfo.Builder()
                .setWaterMarkPath(Constants.WATER_MARK_PATH)
                .setWaterMarkPosition(1)
                .setCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK)
                .setBeautyProgress(80)
                .setBeautySkinOn(true)
                .setMovieExportOptions(movie_options)
                .setThumbnailExportOptions(thumbnailExportOptions)
                .build();

        qupaiService.initRecord(info,projectOptions,_UISettings);

//        qupaiService.addMusic(0, "Athena", "assets://Qupai/music/Athena");
    }
}
