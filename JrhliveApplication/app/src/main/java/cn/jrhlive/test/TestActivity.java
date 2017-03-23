package cn.jrhlive.test;


import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class TestActivity extends BaseActivity {


    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.et_span)
    EditText etSpan;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        String str = "android";
        etSpan.setText(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        Drawable drawable = getDrawable(R.mipmap.ic_launcher);
        ImageSpan span = new ImageSpan(drawable, str, DynamicDrawableSpan.ALIGN_BOTTOM);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableStringBuilder.setSpan(span, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan.setText(spannableStringBuilder);

    }

    public void menu(View v) {
        if (ivMenu.getDrawable() instanceof Animatable) {
            ((Animatable) ivMenu.getDrawable()).start();
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_test;
    }


}
