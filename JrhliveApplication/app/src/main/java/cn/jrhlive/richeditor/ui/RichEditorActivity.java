package cn.jrhlive.richeditor.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jrhlibrary.utils.PermissionUtil;
import com.jrhlibrary.utils.UriToPathUtil;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.richeditor.widgets.RichEditorView;
import cn.jrhlive.richeditor.widgets.listener.OnTextChangeListener;
import cn.jrhlive.utils.ToastUtil;

import static cn.jrhlive.constants.Constant.CAMERA_PERMISSIONS;
import static cn.jrhlive.meishe.MeisheActivity.REQUEST_CODE_CHOOSE;
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;

public class RichEditorActivity extends BaseActivity implements ColorPickerDialogListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_link)
    ImageView ivLink;
    @BindView(R.id.iv_bold)
    ImageView ivBold;
    @BindView(R.id.iv_insertmore)
    ImageView ivInsertmore;
    @BindView(R.id.iv_italic)
    ImageView ivItalic;
    @BindView(R.id.iv_strike)
    ImageView ivStrike;
    @BindView(R.id.iv_underline)
    ImageView ivUnderline;
    @BindView(R.id.iv_quote)
    ImageView ivQuote;
    @BindView(R.id.tv_h1)
    TextView tvH1;
    @BindView(R.id.tv_h2)
    TextView tvH2;
    @BindView(R.id.tv_h3)
    TextView tvH3;
    @BindView(R.id.tv_h4)
    TextView tvH4;
    @BindView(R.id.tv_h5)
    TextView tvH5;
    @BindView(R.id.more_lay)
    HorizontalScrollView moreLay;
    @BindView(R.id.bottom_lay)
    LinearLayout bottomLay;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.iv_font)
    ImageView ivFont;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_undo)
    ImageView ivUndo;
    @BindView(R.id.iv_todo)
    ImageView ivTodo;
    @BindView(R.id.rich_editor)
    RichEditorView richEditor;
    @BindView(R.id.tv_font_color)
    TextView tvFontColor;
    @BindView(R.id.tv_font_bg)
    TextView tvFontBg;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_mid)
    TextView tvMid;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_letter_space)
    TextView tvLetterSpace;
    @BindView(R.id.tv_row_space)
    TextView tvRowSpace;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_sup)
    TextView tvSup;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_un_sort)
    TextView tvUnSort;
    @BindView(R.id.tv_p_w_retract)
    TextView tvPWRetract;
    @BindView(R.id.tv_p_w_retract_back)
    TextView tvPWRetractBack;
    @BindView(R.id.tv_p_row_retract)
    TextView tvPRowRetract;
    @BindView(R.id.tv_p_space_before)
    TextView tvPSpaceBefore;
    @BindView(R.id.tv_p_space_after)
    TextView tvPSpaceAfter;


    private static final int DIALOG_ID = 0;
    boolean isBlock;
    int fontSize = 1;
    boolean isFontColor;
    boolean isFontBg;
    int letterSpace = 5;
    boolean isTextIndent;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_pic)
    TextView tvPic;
    @BindView(R.id.tv_audio)
    TextView tvAudio;
    List<Uri> uris;
    List<String> mPaths;
    /**
     * 1,2,3 图片，视频，音频
     */
    int type = 0;
    File mFile;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_recover)
    TextView tvRecover;

    @Override
    protected void initEvent() {

        initFiles();
    }

    private void initFiles() {
        mFile = new File(Environment.getExternalStorageDirectory() + "/richEditor");
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    @Override
    protected void initView() {
        richEditor.setmTextChangeListener(new OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
//                richEditor.updateContentLength();
                tvTitle.setText("字数：" + text.trim().length());

            }
        });

    }

    @Override
    protected int getViewId() {

        return R.layout.activity_rich_editor;
    }


    @OnClick({R.id.iv_link, R.id.iv_bold, R.id.iv_insertmore, R.id.iv_italic,
            R.id.iv_strike, R.id.iv_underline, R.id.iv_quote, R.id.tv_h1,
            R.id.tv_h2, R.id.tv_h3, R.id.tv_h4, R.id.tv_h5, R.id.iv_photo,
            R.id.iv_font, R.id.iv_add, R.id.iv_undo, R.id.iv_todo,R.id.tv_save, R.id.tv_recover,
            R.id.tv_font_color, R.id.tv_font_bg, R.id.tv_left, R.id.tv_mid, R.id.tv_right,
            R.id.tv_letter_space, R.id.tv_row_space, R.id.tv_sub, R.id.tv_sup, R.id.tv_sort,
            R.id.tv_un_sort, R.id.tv_p_w_retract, R.id.tv_p_w_retract_back,
            R.id.tv_p_row_retract, R.id.tv_p_space_before, R.id.tv_p_space_after, R.id.tv_video, R.id.tv_pic, R.id.tv_audio

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_link:
                richEditor.insertLink("http://www.baidu.com", "baidu");
                break;
            case R.id.iv_bold:
                richEditor.setBold();
                break;
            case R.id.iv_insertmore:
                richEditor.insertHr();
                break;
            case R.id.iv_italic:

                richEditor.setItalic();
                break;
            case R.id.iv_strike:

                richEditor.setStrikeThrough();
                break;
            case R.id.iv_underline:
                richEditor.setUnderLine();
                break;
            case R.id.iv_quote:

                richEditor.setBlockQuote(isBlock = !isBlock);
                break;
            case R.id.tv_h1:
                richEditor.setHeading(1, true);
                break;
            case R.id.tv_h2:
                richEditor.setHeading(2, true);
                break;
            case R.id.tv_h3:
                richEditor.setHeading(3, true);
                break;
            case R.id.tv_h4:
                richEditor.setHeading(4, true);
                break;
            case R.id.tv_h5:
                richEditor.setHeading(5, true);
                break;
            case R.id.iv_photo:
                type = 1;
                selectPicVideos(1);
                break;
            case R.id.iv_font:
                if (fontSize < 7) {
                    fontSize++;
                } else {
                    fontSize = 1;
                }

                richEditor.setFontSize(fontSize);
                break;
            case R.id.iv_add:
                moreLay.setVisibility(moreLay.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.iv_undo:
                richEditor.undo();
                break;
            case R.id.iv_todo:
                richEditor.redo();
                break;
            case R.id.tv_font_color:
                isFontColor = true;
                showColorDialog();
                break;
            case R.id.tv_font_bg:
                isFontBg = true;
                showColorDialog();
                break;
            case R.id.tv_left:
                richEditor.setAlignLeft();
                break;
            case R.id.tv_mid:
                richEditor.setAlignCenter();
                break;
            case R.id.tv_right:
                richEditor.setAlignRight();
                break;
            case R.id.tv_letter_space:
                richEditor.setLetterSpace(letterSpace++);
                if (letterSpace >= 20) {
                    letterSpace = 1;
                }
                break;
            case R.id.tv_row_space:
                richEditor.setLineHeight(letterSpace += 10);
                if (letterSpace >= 100) {
                    letterSpace = 1;
                }
                break;
            case R.id.tv_sub:
                richEditor.setSubscript();
                break;
            case R.id.tv_sup:
                richEditor.setSuperscript();
                break;
            case R.id.tv_sort:
                richEditor.setNumbers();
                break;
            case R.id.tv_un_sort:
                richEditor.setBullets();
                break;
            case R.id.tv_p_w_retract:
                richEditor.setIndent();
                break;
            case R.id.tv_p_w_retract_back:
                richEditor.setOutdent();
                break;
            case R.id.tv_p_row_retract:
                if (isTextIndent) {
                    richEditor.setTextIndent(2);
                } else {
                    richEditor.setTextIndent(0);
                }

                isTextIndent = !isTextIndent;
                break;
            case R.id.tv_p_space_before:
                richEditor.insertBr();
                break;
            case R.id.tv_p_space_after:
                richEditor.insertBr();

                break;

            case R.id.tv_video:
                type = 2;
                selectPicVideos(2);
                break;
            case R.id.tv_pic:
                type = 1;
                selectPicVideos(1);

                break;
            case R.id.tv_audio:
                type = 3;
                Intent intent3 = new Intent(this, AudioPickActivity.class);
                intent3.putExtra(IS_NEED_RECORDER, true);
                intent3.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent3, Constant.REQUEST_CODE_PICK_AUDIO);
                break;
            case R.id.tv_save:
                richEditor.saveTest(mFile.getPath()+"/"+ System.currentTimeMillis()+".txt");
                break;
            case R.id.tv_recover:
                break;
        }
    }

    private void showColorDialog() {
        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(DIALOG_ID)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .show(this);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        ToastUtil.showMessage(color + "");
        if (isFontColor) {
            richEditor.setTextColor(color);
        }
        if (isFontBg) {
            richEditor.setTextBackgroundColor(color);
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
        isFontColor = false;
        isFontBg = false;

    }

    private void selectPicVideos(int type) {
        if (!PermissionUtil.check(this, CAMERA_PERMISSIONS)) {
            PermissionUtil.showTips(this, CAMERA_PERMISSIONS);
            return;
        }
        Matisse.from(this)
                .choose(type == 1 ? MimeType.ofImage() : MimeType.ofVideo())
                .countable(true)
                .maxSelectable(1)
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
                mPaths.add(UriToPathUtil.getRealFilePath(RichEditorActivity.this, uri));
            }
            if (type == 1) {
                richEditor.insertImage(mPaths.get(0), "图片");
            }
            if (type == 2) {
                richEditor.insertVideo(mPaths.get(0));
            }


        }

        if (type == 3) {

            ArrayList<AudioFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);

            richEditor.insertAudio(list.get(0).getPath(), list.get(0).getName());
        }
    }

}
