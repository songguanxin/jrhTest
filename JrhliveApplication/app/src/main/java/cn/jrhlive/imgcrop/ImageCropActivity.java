package cn.jrhlive.imgcrop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;

public class ImageCropActivity extends BaseActivity {


    @BindView(R.id.button)
    Button button;
    @BindView(R.id.img_show)
    ImageView imgShow;

    private static final int REQUEST_SELECT_PICTURE = 0x01;

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_image_crop;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        pickFromGallery();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(ImageCropActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
//            handleCropError(data);
        }
    }


    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
//            ResultActivity.startWithUri(SampleActivity.this, resultUri);
            imgShow.setImageURI(resultUri);
        } else {
//            Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME+".png";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);

        uCrop.start(ImageCropActivity.this);
    }


    private void beginCrop(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped"));

        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(16, 9)
                .withMaxResultSize(maxWidth, maxHeight)
                .start(ImageCropActivity.this);
    }




    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * In most cases you need only to set crop aspect ration and max size for resulting image.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
//        switch (mRadioGroupAspectRatio.getCheckedRadioButtonId()) {
//            case R.id.radio_origin:
//                uCrop = uCrop.useSourceImageAspectRatio();
//                break;
//            case R.id.radio_square:
//                uCrop = uCrop.withAspectRatio(1, 1);
//                break;
//            case R.id.radio_dynamic:
//                // do nothing
//                break;
//            default:
//                try {
//                    float ratioX = Float.valueOf(mEditTextRatioX.getText().toString().trim());
//                    float ratioY = Float.valueOf(mEditTextRatioY.getText().toString().trim());
//                    if (ratioX > 0 && ratioY > 0) {
//                        uCrop = uCrop.withAspectRatio(ratioX, ratioY);
//                    }
//                } catch (NumberFormatException e) {
//                    Log.i(TAG, String.format("Number please: %s", e.getMessage()));
//                }
//                break;
//        }

//        if (mCheckBoxMaxSize.isChecked()) {
//            try {
//                int maxWidth = Integer.valueOf(mEditTextMaxWidth.getText().toString().trim());
//                int maxHeight = Integer.valueOf(mEditTextMaxHeight.getText().toString().trim());
//                if (maxWidth > 0 && maxHeight > 0) {
//                    uCrop = uCrop.withMaxResultSize(maxWidth, maxHeight);
//                }
//            } catch (NumberFormatException e) {
//                Log.e(TAG, "Number please", e);
//            }
//        }

        uCrop.withAspectRatio(16,9);

        return uCrop;
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

//        switch (mRadioGroupCompressionSettings.getCheckedRadioButtonId()) {
//            case R.id.radio_png:
//                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
//                break;
//            case R.id.radio_jpeg:
//            default:
//                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//                break;
//        }
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(80);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.crop_toolbar_color));
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.crop_toolbar_color));


        /*
        If you want to configure how gestures work for all UCropActivity tabs
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        * */

        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.
        options.setMaxBitmapSize(640);
        * */


       /*
        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧
        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);
        options.setToolbarCropDrawable(R.drawable.your_crop_icon);
        options.setToolbarCancelDrawable(R.drawable.your_cancel_icon);
        // Color palette
//        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.your_color_res));
        // Aspect ratio options
        options.setAspectRatioOptions(1,
            new AspectRatio("WOW", 1, 2),
            new AspectRatio("MUCH", 3, 4),
            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
            new AspectRatio("SO", 16, 9),
            new AspectRatio("ASPECT", 1, 1));
       */

        return uCrop.withOptions(options);
    }
}
