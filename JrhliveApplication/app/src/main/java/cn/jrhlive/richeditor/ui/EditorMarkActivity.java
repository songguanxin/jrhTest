package cn.jrhlive.richeditor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.richeditor.RichFunction;
import cn.jrhlive.richeditor.bean.EditorMarkBean;
import cn.jrhlive.richeditor.widgets.listener.OnSendRangeListener;

/**
 * Created by songgx on 2017/10/8.
 */

public class EditorMarkActivity extends BaseActivity implements OnSendRangeListener{
    private static final String EDITOR_MARK_BEAN = "editor_mark_bean";
    public static final String EDITOR_MARK_AUTHOR = "editor_mark_author";
    public static final String EDITOR_NO_CHANGE_TEXT = "editor_no_change_text";
    public static final String SELECT_TEXT_START_POS = "select_text_start_pos";
    public static final String SELECT_TEXT_END_POS = "select_text_end_pos";
    public static final String SELECT_TEXT_RANGE = "select_text_range";

    public static final String EDITOR_HAS_CHANGE_TEXT = "editor_has_change_text";
    private static final int EDITOR_MARK_RESULT = 100;
    @BindView(R.id.editor_mark_back)
    TextView editorMarkBack;
    @BindView(R.id.editor_mark_confirm_or_cancel)
    TextView editorMarkConfirmOrCancel;
    @BindView(R.id.editor_mark_title_layout)
    RelativeLayout editorMarkTitleLayout;
    @BindView(R.id.editor_mark_author_name)
    TextView editorMarkAuthorName;
    @BindView(R.id.editor_mark_author_content_no_change)
    TextView editorNoChangeText;
    @BindView(R.id.editor_mark_author_change)
    TextView editorChangeTitle;
    @BindView(R.id.editor_mark_content)
    EditText editorMarkContent;
    @BindView(R.id.editor_mark_change_reason_title)
    TextView editorChangeReasonTitle;
    @BindView(R.id.editor_mark_content_reason)
    EditText editMarkReason;
    String editorContent;
    String author;
    String noChangeText;
    String reason;
    Object range;

    @Override
    protected void initEvent() {
     RichFunction.getInstance().addSendRangeListener(this);
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        if (intent!=null) {
            author=intent.getExtras().getString(EDITOR_MARK_AUTHOR);
            noChangeText=intent.getExtras().getString(EDITOR_NO_CHANGE_TEXT);
        }
        editorMarkAuthorName.setText("作者："+author);
        editorNoChangeText.setText("原文内容："+noChangeText);
        editorMarkContent.setText(noChangeText);
        editorContent=editorMarkContent.getText().toString().trim();
        getEditorContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichFunction.getInstance().removeSendRangeListener(this);
    }

    /**
     * 动态获取批注的内容
     */
    private void getEditorContent() {
        editorMarkContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editorContent = editable.toString();
            }
        });
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_editor_mark;
    }

    @OnClick({R.id.editor_mark_back, R.id.editor_mark_confirm_or_cancel})
    public void onViewClicked(View view) {
        final EditorMarkBean editorMarkBean=new EditorMarkBean();
        switch (view.getId()) {
            case R.id.editor_mark_back:
                finish();
                break;
            case R.id.editor_mark_confirm_or_cancel:
                reason = editMarkReason.getText().toString().trim();
                if (editorContent.equals("")) {//删除操作
                    if (!reason.equals("")) {
                      delete(editorMarkBean);
                    }
                } else if (editorContent.equals(noChangeText)) {//不做修改
                    noChange();
                }  else {//修改操作
                    if (!reason.equals("")) {
                        change(editorMarkBean);
                    }
                }
                break;
        }
    }

    /**
     * 修改原文内容
     * @param editorMarkBean
     */
    private void change(final EditorMarkBean editorMarkBean) {
        editorMarkBean.setAuthor(author);
        editorMarkBean.setChangeType(EditorMarkBean.CHANGE);
        editorMarkBean.setNoChangeText(noChangeText);
        editorMarkBean.setHasChangeText(editorContent);
        editorMarkBean.setDate(new Date());
        editorMarkBean.setChangeReason(reason);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        builder.setTitle("温馨提示");
        builder.setMessage("修改原文内容?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                RichFunction.getInstance().notifySendMarkDataListener(editorMarkBean);
                finish();
            }
        });
        builder.show();
    }

    /**
     * 不进行修改
     */
    private void noChange() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        builder.setTitle("温馨提示");
        builder.setMessage("原文内容不进行修改?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    /**
     * 删除原文操作
     * @param editorMarkBean
     */
    private void delete(final EditorMarkBean editorMarkBean) {
        editorMarkBean.setAuthor(author);
        editorMarkBean.setChangeType(EditorMarkBean.DELETE);
        editorMarkBean.setNoChangeText(noChangeText);
        editorMarkBean.setHasChangeText(editorContent);
        editorMarkBean.setDate(new Date());
        editorMarkBean.setChangeReason(reason);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        builder.setTitle("温馨提示");
        builder.setMessage("删除原文内容?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                RichFunction.getInstance().notifySendMarkDataListener(editorMarkBean);
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void sendRange(Object o) {
        if (o != null) {
            range=o;
        }
    }
}
