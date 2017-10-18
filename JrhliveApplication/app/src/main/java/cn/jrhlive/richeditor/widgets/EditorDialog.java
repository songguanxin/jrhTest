package cn.jrhlive.richeditor.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.jrhlive.R;

/**
 * Created by Think on 2017/10/9.
 */

public class EditorDialog extends Dialog {
    private Button confirmBtn,cancelBtn;
    private EditText editText;
    private OnConfirmListener onConfirmListener;
    private OnShutDownListener onShutDownListener;
    private String confirmStr;

    public EditorDialog(@NonNull Context context) {
        super(context,R.style.editor_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_mark_dialog);
        setCanceledOnTouchOutside(true);
        initView();
        initEvent();
    }

    private void initEvent() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(confirmStr);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onShutDownListener != null) {
                    onShutDownListener.onShutDown();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.equals("")) {
                    confirmStr = s;
                }
            }
        });
    }

    private void initView() {
        confirmBtn=findViewById(R.id.dialog_editor_confirm);
        cancelBtn=findViewById(R.id.dialog_editor_cancel);
        editText=findViewById(R.id.dialog_editor_edit);
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener=onConfirmListener;
    }

    public void setOnShutDownListener(OnShutDownListener onShutDownListener) {
        this.onShutDownListener=onShutDownListener;
    }


    interface OnConfirmListener {
        void onConfirm(String str);
    }

    interface OnShutDownListener {
        void onShutDown();
    }

}
