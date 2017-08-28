package cn.jrhlive.richeditor.ui;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.richeditor.widgets.RichEditorView;
import cn.jrhlive.utils.ToastUtil;

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

    private static final int DIALOG_ID = 0;
    boolean isBlock;
    int fontSize = 1;
    boolean isFontColor;
    boolean isFontBg;
    int letterSpace=5;


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {

        return R.layout.activity_rich_editor;
    }


    @OnClick({R.id.iv_link, R.id.iv_bold, R.id.iv_insertmore, R.id.iv_italic,
            R.id.iv_strike, R.id.iv_underline, R.id.iv_quote, R.id.tv_h1,
            R.id.tv_h2, R.id.tv_h3, R.id.tv_h4, R.id.tv_h5, R.id.iv_photo,
            R.id.iv_font, R.id.iv_add, R.id.iv_undo, R.id.iv_todo,
            R.id.tv_font_color, R.id.tv_font_bg, R.id.tv_left, R.id.tv_mid, R.id.tv_right,
            R.id.tv_letter_space, R.id.tv_row_space

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
                if (letterSpace>=20){
                    letterSpace=1;
                }
                break;
            case R.id.tv_row_space:
                richEditor.setLineHeight(letterSpace+=10);
                if (letterSpace>=100){
                    letterSpace=1;
                }
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

}
