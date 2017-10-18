package cn.jrhlive.richeditor;

import java.util.ArrayList;
import java.util.List;
import cn.jrhlive.richeditor.bean.EditorMarkBean;
import cn.jrhlive.richeditor.widgets.listener.OnSendMarkDataListener;
import cn.jrhlive.richeditor.widgets.listener.OnSendRangeListener;

/**
 * Created by songgx on 2017/9/18.
 * 实现长按事件选项
 */

public class RichFunction {

    private static  RichFunction richFunction;
    private List<OnSendMarkDataListener> mSendMarkDataListeners=new ArrayList<>();
    private List<OnSendRangeListener> mSendRangeListeners=new ArrayList<>();

    public static synchronized RichFunction getInstance() {
        if (richFunction == null) {
            richFunction=new RichFunction();
        }
        return richFunction;
    }

    /**
     * 注册一回调添加批注列表的接口
     */
    public void addSendMarkListener(OnSendMarkDataListener onSendMarkDataListener) {
        if (onSendMarkDataListener == null) {
            return;
        }
        if (mSendMarkDataListeners.indexOf(onSendMarkDataListener) == -1) {
            mSendMarkDataListeners.add(onSendMarkDataListener);
        }
    }

    /**
     * 注销一回调添加批注列表的接口
     */
    public void removeSendMarkListener(OnSendMarkDataListener onSendMarkDataListener) {
        if (onSendMarkDataListener == null) {
            return;
        }
        if (mSendMarkDataListeners.indexOf(onSendMarkDataListener) != -1) {
            mSendMarkDataListeners.remove(onSendMarkDataListener);
        }
    }

    /**
     * @descript 下发通知, 弹出批注功能
     */
    public void notifySendMarkDataListener(EditorMarkBean editorMarkBean) {
        List<OnSendMarkDataListener> _copy = new ArrayList<>(mSendMarkDataListeners);
        for (OnSendMarkDataListener _call : _copy) {
            try {
                _call.setMark(editorMarkBean);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 注册一回调添加批注列表的接口
     */
    public void addSendRangeListener(OnSendRangeListener onSendRangeListener) {
        if (onSendRangeListener == null) {
            return;
        }
        if (mSendRangeListeners.indexOf(onSendRangeListener) == -1) {
            mSendRangeListeners.add(onSendRangeListener);
        }
    }

    /**
     * 注销一回调添加批注列表的接口
     */
    public void removeSendRangeListener(OnSendRangeListener onSendRangeListener) {
        if (onSendRangeListener == null) {
            return;
        }
        if (mSendRangeListeners.indexOf(onSendRangeListener) != -1) {
            mSendRangeListeners.remove(onSendRangeListener);
        }
    }

    /**
     * @descript 下发通知, 弹出批注功能
     */
    public void notifySendRangeListener(Object o) {
        List<OnSendRangeListener> _copy = new ArrayList<>(mSendRangeListeners);
        for (OnSendRangeListener _call : _copy) {
            try {
                _call.sendRange(o);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
