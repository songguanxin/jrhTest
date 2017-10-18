package cn.jrhlive.richeditor.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cn.jrhlive.richeditor.RichFunction;
import cn.jrhlive.richeditor.ui.EditorMarkActivity;
import cn.jrhlive.richeditor.widgets.bean.FontStyleType;
import cn.jrhlive.richeditor.widgets.listener.ActionSelectListener;
import cn.jrhlive.richeditor.widgets.listener.AfterInitialLoadListener;
import cn.jrhlive.richeditor.widgets.listener.OnDecorationStateListener;
import cn.jrhlive.richeditor.widgets.listener.OnTextChangeListener;
import cn.jrhlive.utils.ToastUtil;

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

    /**
     * 自定义长按弹出框-------start--------
     */
    ActionMode mActionMode;

    List<String> mActionList = new ArrayList<>();

    ActionSelectListener mActionSelectListener;
    /**
     * 自定义长按弹出框-------end--------
     */


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
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        //支持获取手势焦点
        requestFocusFromTouch();
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");

        //支持缩放
        settings.setSupportZoom(false);

        setWebViewClient(createWebviewClient());
        loadUrl(SETUP_HTML);
        initAlert(this);
        //addJavascriptInterface(this, "android");
//        evaluateJavaScript(this);
        applyAttributes(context, attrs);
        setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtil.showMessage(message);
                return super.onJsAlert(view, url, message, result);
            }
        });
    }


    /**
     * 初始化弹出框
     * @param richEditorView
     */
    public void initAlert(final RichEditorView richEditorView) {
        List<String> list = new ArrayList<>();
        list.add("批注");

        //设置item
        richEditorView.setActionList(list);

        //链接js注入接口，使能选中返回数据
        richEditorView.linkJSInterface();

        richEditorView.getSettings().setBuiltInZoomControls(true);
        richEditorView.getSettings().setDisplayZoomControls(false);
        //使用javascript
        richEditorView.getSettings().setJavaScriptEnabled(true);
        richEditorView.getSettings().setDomStorageEnabled(true);
        richEditorView.setActionSelectListener(new ActionSelectListener() {
            @Override
            public void onClick(String title, final String selectText) {
                switch (title) {
                    case "批注":
                        final Context context = richEditorView.getContext();
                        final EditorDialog dialog=new EditorDialog(context);
                        dialog.setOnConfirmListener(new EditorDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm(String str) {
                                Intent intent=new Intent(context, EditorMarkActivity.class);
                                intent.putExtra(EditorMarkActivity.EDITOR_MARK_AUTHOR,str);
                                intent.putExtra(EditorMarkActivity.EDITOR_NO_CHANGE_TEXT,selectText);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnShutDownListener(new EditorDialog.OnShutDownListener(){

                            @Override
                            public void onShutDown() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                }
            }
        });
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
        exec("javascript:RE.onImgClicked();");

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
    public void setHtml(String content){
        exec("javascript:RE.setHtml('"+content+"');");
    }
    public void reset(){
        exec("javascript:RE.reset()");
        exec("javascript:RE.prepareInsert();");
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

    @JavascriptInterface
    public void onImgClicked(String url){
        ToastUtil.showMessage(url);
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

    /**
     * 处理相关点击事件---------------------------------------------------------start
     */

    /**
     * 处理item，处理点击
     * @param actionMode
     */
    private ActionMode resolveActionMode(ActionMode actionMode) {
        if (actionMode != null) {
            final Menu menu = actionMode.getMenu();
            mActionMode = actionMode;
            menu.clear();
            for (int i = 0; i < mActionList.size(); i++) {
                menu.add(mActionList.get(i));
            }
            for (int i = 0; i < menu.size(); i++) {
                MenuItem menuItem = menu.getItem(i);
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//                        getSelectedData((String) item.getTitle());
                        replaceTextToSpan((String) item.getTitle());
                        releaseAction();
                        return true;
                    }
                });
            }
        }
        mActionMode = actionMode;
        return actionMode;
    }

    //设置判断是弹出复制粘贴框还是弹出批注框
    boolean showPiZhu=false;
    public void setLongPressMode(boolean showPiZhu) {
        this.showPiZhu=showPiZhu;
    }

    public boolean getLongPressMode() {
        return showPiZhu;
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionMode actionMode = super.startActionMode(callback);
        if (showPiZhu) {
            return resolveActionMode(actionMode);
        } else {
            return actionMode;
        }
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ActionMode actionMode = super.startActionMode(callback, type);
        if (showPiZhu) {
            return resolveActionMode(actionMode);
        } else {
            return actionMode;
        }
    }

    private void releaseAction() {
        if (mActionMode != null) {
            mActionMode.finish();
            mActionMode = null;
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     */
    private void getSelectedData(String title) {
        String js = "(function getSelectedText() {" +
                "var txt;" +
                "var selection;"+
                "var title = \"" + title + "\";" +
                "if (window.getSelection) {" +
                "selection= window.getSelection();"+
                "txt = window.getSelection().toString();" +
                "console.log(txt);"
                + "selection.deleteFromDocument();"
                + "var range = sel.getRangeAt(0);"
                + "var selFrag = range.cloneContents();"
                + "var span = document.createElement('span');"
                + "span.innerHTML = txt;"
                +"console.log(span.innerHTML);"
                + "span.style.backgroundColor=\"red\";"
                + "range.insertNode(span);"
                + "}"
                + "android.callback(txt,title);" + "})()"
                ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:" + js, null);
        } else {
            loadUrl("javascript:" + js);
        }
    }

    public void replaceTextToSpan(String title) {
        exec("javascript:RE.replaceSelectionText(\"" + title + "\");");
    }

    public void getSpan(int action,String text){
        exec("javascript:RE.getSpan(\"" + action + "\",\""+text+"\");");
    }

    public void linkJSInterface() {
        addJavascriptInterface(new ActionSelectInterface(this), "android");
    }

    /**
     * 设置弹出action列表
     * @param actionList
     */
    public void setActionList(List<String> actionList) {
        mActionList = actionList;
    }

    /**
     * 设置点击回掉
     * @param actionSelectListener
     */
    public void setActionSelectListener(ActionSelectListener actionSelectListener) {
        this.mActionSelectListener = actionSelectListener;
    }
    /**
     * 隐藏消失Action
     */
    public void dismissAction() {
        releaseAction();
    }


    /**
     * js选中的回掉接口
     */
    private class ActionSelectInterface {

        RichEditorView mContext;

        ActionSelectInterface(RichEditorView c) {
            mContext = c;
        }

        @JavascriptInterface
        public void callback(final String title, final String value) {
            if(mActionSelectListener != null) {
                mActionSelectListener.onClick(value, title);
            }
        }
    }

    /**
     * 处理相关点击事件---------------------------------------------------------end
     */

    public void addComment() {
        exec("javascript:RE.addComments();");

    }

//    public void replaceTextToSpan(String selectText) {
//        exec("javascript:RE.replaceSelectionText("+selectText+");");
//    }
}
