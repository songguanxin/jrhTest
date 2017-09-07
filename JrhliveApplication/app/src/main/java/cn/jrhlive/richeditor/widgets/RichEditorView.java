package cn.jrhlive.richeditor.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.jrhlive.richeditor.widgets.bean.FontStyleType;
import cn.jrhlive.richeditor.widgets.listener.AfterInitialLoadListener;
import cn.jrhlive.richeditor.widgets.listener.OnDecorationStateListener;
import cn.jrhlive.richeditor.widgets.listener.OnTextChangeListener;

/**
 * desc:do rich editor
 * Created by jiarh on 17/8/24 16:07.
 */

public class RichEditorView extends WebView {

    private static final String SETUP_HTML = "file:///android_asset/editor.html";
    private static final String CALLBACK_SCHEME = "re-callback://";
    private static final String STATE_SCHEME = "re-state://";
    //网页加载是否完毕
    private boolean isReady = false;
    private String mContents;

    AfterInitialLoadListener mLoadListener;
    private OnTextChangeListener mTextChangeListener;
    private OnDecorationStateListener mDecorationStateListener;
    private int contentLength;


    public RichEditorView(Context context) {
        this(context,null);
    }

    public RichEditorView(Context context, AttributeSet attrs) {
        this(context,attrs, android.R.attr.webViewStyle);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public RichEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());

        setWebViewClient(createWebviewClient());
        loadUrl(SETUP_HTML);
        addJavascriptInterface(this, "android");
//        evaluateJavaScript(this);
        applyAttributes(context, attrs);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        final int[] attrsArray = new int[]{
                android.R.attr.gravity
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

        int gravity = ta.getInt(0, NO_ID);
        switch (gravity) {
            case Gravity.LEFT:
                exec("javascript:RE.setTextAlign(\"left\")");
                break;
            case Gravity.RIGHT:
                exec("javascript:RE.setTextAlign(\"right\")");
                break;
            case Gravity.TOP:
                exec("javascript:RE.setVerticalAlign(\"top\")");
                break;
            case Gravity.BOTTOM:
                exec("javascript:RE.setVerticalAlign(\"bottom\")");
                break;
            case Gravity.CENTER_VERTICAL:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                break;
            case Gravity.CENTER_HORIZONTAL:
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
            case Gravity.CENTER:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
        }

        ta.recycle();
    }

    protected void exec(final String trigger) {
        if (isReady) {
            load(trigger);
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    exec(trigger);
                }
            }, 100);
        }
    }

    private void load(String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(trigger, null);
        } else {
            loadUrl(trigger);
        }
    }


    protected EditorWebViewClient createWebviewClient() {
        return new EditorWebViewClient();
    }


    private void callback(String text) {
        mContents = text.replaceFirst(CALLBACK_SCHEME, "");
        if (mTextChangeListener != null) {
            mTextChangeListener.onTextChange(mContents);
        }
        return;
    }


    private void stateCheck(String text) {

        if (!text.contains("@_@")) {
            String state = text.replaceFirst(STATE_SCHEME, "").toUpperCase(Locale.ENGLISH);
            List<FontStyleType> types = new ArrayList<>();
            for (FontStyleType type : FontStyleType.values()) {
                if (TextUtils.indexOf(state, type.name()) != -1) {
                    types.add(type);
                }
            }

            if (mDecorationStateListener != null) {
                mDecorationStateListener.onStateChangeListener(state, types);
            }
            return;
        }

        String state = text.replaceFirst(STATE_SCHEME, "").split("@_@")[0].toUpperCase(Locale.ENGLISH);
        List<FontStyleType> types = new ArrayList<>();
        for (FontStyleType type : FontStyleType.values()) {
            if (TextUtils.indexOf(state, type.name()) != -1) {
                types.add(type);
            }
        }

        if (mDecorationStateListener != null) {
            mDecorationStateListener.onStateChangeListener(state, types);
        }

        if (text.replaceFirst(STATE_SCHEME, "").split("@_@").length > 1) {
            mContents = text.replaceFirst(STATE_SCHEME, "").split("@_@")[1];
            if (mTextChangeListener != null) {
                mTextChangeListener.onTextChange(mContents);
            }
        }
    }
    public void setStrikeThrough() {
        exec("javascript:RE.setStrikeThrough();");
    }

    public void setUnderLine(){
        exec("javascript:RE.setUnderline();");
    }

    public void setHeading(int heading,boolean click){
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setHeading("+heading+","+click+")");
    }
    public void setItalic() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setItalic();");
    }

    public void setBold() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setBold();");
    }

    public void insertHr() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertHr();");
    }
    public void setBlockQuote(boolean isBlock) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setBlockquote("+isBlock+");");
    }
    public void redo(){
        exec("javascript:RE.redo();");
    }

    public void undo(){
        exec("javascript:RE.undo()");
    }

    public void insertLink(String href, String title) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertLink('" + href + "', '" + title + "');");
    }
    public void insertBr() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertBr();");
    }

    public void setTextColor(int color) {
        exec("javascript:RE.prepareInsert();");
        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextColor('" + hex + "');");
    }
    private String convertHexColorString(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
    public void setTextBackgroundColor(int color) {
        exec("javascript:RE.prepareInsert();");

        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextBackgroundColor('" + hex + "');");
    }

    public void searchText(String keyWord){
        exec("javascript:RE.searchText('"+keyWord+"');");
    }
    public void replaceText(String keyWord,String replaceWord){
        exec("javascript:RE.replaceText('"+keyWord+"','"+ replaceWord+"');");
    }

    public void insertImage(String url, String alt) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertImage('" + url + "', '" + alt + "');");
    }
    public void insertVideo(String url) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertVideo('" + url + "');");
    }
    public void insertAudio(String url,String desc) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertAudio('" + url + "','"+desc+"');");
    }

    public void saveTest(String path){
        exec("javascript:RE.saveTest('"+path+"');");

    }

    public void setSubscript() {
        exec("javascript:RE.setSubscript();");
    }

    public void setSuperscript() {
        exec("javascript:RE.setSuperscript();");
    }
    public void setIndent() {
        exec("javascript:RE.setIndent();");
    }

    public void setOutdent() {
        exec("javascript:RE.setOutdent();");
    }

    public void setTextIndent(int em){
        exec("javascript:RE.setTextIndent("+em+");");
    }

    public void setBullets(){
        exec("javascript:RE.setBullets();");
    }
    public void setNumbers(){
        exec("javascript:RE.setNumbers();");
    }
    public void setAlignLeft() {
        exec("javascript:RE.setJustifyLeft();");
    }

    public void setAlignCenter() {
        exec("javascript:RE.setJustifyCenter();");
    }

    public void setAlignRight() {
        exec("javascript:RE.setJustifyRight();");
    }

    public void setLetterSpace(int space){
        exec("javascript:RE.setLetterSpace("+space+");");
    }
    public void setLineHeight(int height){
        exec("javascript:RE.setLineHeight("+height+");");
    }
    /**
     *
     *
     * @param size 1-7
     */
    public void setFontSize(int size){
        exec("javascript:RE.setFontSize("+size+");");
    }
    protected class EditorWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            isReady = url.equalsIgnoreCase(SETUP_HTML);
            if (mLoadListener != null) {
                mLoadListener.onAfterInitialLoad(isReady);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String decode;
            try {
                decode = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // No handling
                return false;
            }

            if (TextUtils.indexOf(url, CALLBACK_SCHEME) == 0) {
                callback(decode);
                return true;
            } else if (TextUtils.indexOf(url, STATE_SCHEME) == 0) {
                stateCheck(decode);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    private void evaluateJavaScript(WebView webView){
        webView.evaluateJavascript("getContentLength()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                setContentLength(Integer.parseInt(s));
            }
        });
    }

    public void setmLoadListener(AfterInitialLoadListener mLoadListener) {
        this.mLoadListener = mLoadListener;
    }

    public void setmTextChangeListener(OnTextChangeListener mTextChangeListener) {
        this.mTextChangeListener = mTextChangeListener;
    }

    public void setmDecorationStateListener(OnDecorationStateListener mDecorationStateListener) {
        this.mDecorationStateListener = mDecorationStateListener;
    }

    public void updateContentLength(){
        exec("javascript:RE.getContentLength();");
    }

    @JavascriptInterface
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public int getContentLength() {

        return contentLength;
    }
}
