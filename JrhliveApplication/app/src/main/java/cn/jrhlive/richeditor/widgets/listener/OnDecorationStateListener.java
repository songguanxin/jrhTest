package cn.jrhlive.richeditor.widgets.listener;

import java.util.List;

import cn.jrhlive.richeditor.widgets.bean.FontStyleType;

/**
 * desc:
 * Created by jiarh on 17/8/25 10:44.
 */

public interface OnDecorationStateListener {
    void onStateChangeListener(String text, List<FontStyleType> types);
}
