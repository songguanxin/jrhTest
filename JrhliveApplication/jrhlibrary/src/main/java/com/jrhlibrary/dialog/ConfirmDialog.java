package com.jrhlibrary.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.jrhlibrary.R;
import com.jrhlibrary.utils.Mobile;
import com.jrhlibrary.utils.StringUtil;
import com.jrhlibrary.utils.ViewUtil;


/**
 * Dialog的一个工具类，暂时只有两种样式，<b>单个按钮Dialog</b>和<b>两个并排的按钮Dialog</b>。<br>
 * 1.提示文字分为标题和内容，都设置时分两行显示。<br>
 * 2.需要展示单行文字时，可根据需求，只调用{@link #setTitle(String)}或者{@link #setMessage(String)}即可，一般使用{@link #setTitle(String)}。<br>
 * 3.调用{@link #setSingleButtonListener(String, OnConfirmDialogClickListener)}方法，会显示单个按钮的Dialog。<br>
 * 4.调用{@link #setTwoButtonListener(String, OnConfirmDialogClickListener, String, OnConfirmDialogClickListener)}方法，会显示两个按钮的Dialog.<br>
 * 5.3、4都不调用的情况下，默认会显示单个按钮的Dialog。
 * 
 * @author RenTao
 * @time Feb 11, 2015 5:53:19 PM
 */
public class ConfirmDialog {
	private Activity mContext;
	private Builder mBuilder;
	private AlertDialog mDialog;
	private View mView;
	private Object mTag;
	// 标题
	private String mTitle;
	private TextView mTvTitle;
	// 内容
	private String mMessage;
	private TextView mTvMessage;
	// 两个按钮
	private View mTwoBtns;
	private TextView mBtn1;
	private String mBtn1Text;
	private OnConfirmDialogClickListener mBtn1Listener;
	private TextView mBtn2;
	private String mBtn2Text;
	private OnConfirmDialogClickListener mBtn2Listener;
	// 单个按钮
	private TextView mSingleBtn;
	private String mSingleBtnText;
	private OnConfirmDialogClickListener mSingleBtnListener;
	private OnDismissListener mOnDismissListener;
	/** 按钮数量 */
	private int mBtnNum = 1;
	/** 是否已经初始化 */
	private boolean mHasInit = false;

	private boolean mCancelable = true;

	private int mDialogWidth;

	public ConfirmDialog(Activity context) {
		this.mContext = context;
		mBuilder = new AlertDialog.Builder(context);
		mView = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null);
		mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
		mTvMessage = (TextView) mView.findViewById(R.id.tv_message);
		mSingleBtn = (TextView) mView.findViewById(R.id.btn_single_button);
		mTwoBtns = mView.findViewById(R.id.ll_two_button);
		mBuilder.setView(mView);
		// 初始化Dialog的宽度，屏幕的6/7
		mDialogWidth = Mobile.SCREEN_WIDTH * 6 / 7;
	}

	/**
	 * 是否显示右上角的关闭按钮，默认为隐藏
	 */
	public ConfirmDialog showClose(boolean isShow) {
		if (isShow) {
			View close = mView.findViewById(R.id.btn_close);
			close.setVisibility(View.VISIBLE);
			close.setOnClickListener(new DialogDismissListener());
		}
		return this;
	}

	/**
	 * 为ConfirmDialog设置tag
	 */
	public ConfirmDialog setTag(Object tag) {
		this.mTag = tag;
		return this;
	}

	public Object getTag() {
		return mTag;
	}

	/**
	 * 设置标题文字，假如title为空，则会隐藏该控件
	 */
	public ConfirmDialog setTitle(String title) {
		this.mTitle = title;
		if (mHasInit) {
			ViewUtil.setTextOrHide(mTvTitle, title);
		}
		return this;
	}

	/**
	 * 设置内容，假如message为空，则会隐藏该控件
	 */
	public ConfirmDialog setMessage(String message) {
		this.mMessage = message;
		if (mHasInit) {
			ViewUtil.setTextOrHide(mTvMessage, message);
		}
		return this;
	}

	/**
	 * 设置单个按钮Dialog的文字和点击事件
	 * 
	 * @param text
	 *            按钮文字，为空则会设置默认的文字
	 * @param l
	 *            点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
	 */
	public ConfirmDialog setSingleButtonListener(String text, OnConfirmDialogClickListener l) {
		this.mSingleBtnText = text;
		this.mSingleBtnListener = l;
		mBtnNum = 1;
		return this;
	}

	/**
	 * 设置单个按钮Dialog的点击事件(按钮为“知道了”)
	 * 
	 * @param l
	 *            点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
	 */
	public ConfirmDialog setSingleButtonListener(OnConfirmDialogClickListener l) {
		return setSingleButtonListener(null, l);
	}

	/**
	 * 设置两个按钮Dialog的文字和点击事件，点击事件为空则会设置一个默认事件---点击关闭Dialog
	 * 
	 * @param text1
	 *            第一个按钮的文字，为空则会设置默认的文字
	 * @param l1
	 *            第一个按钮的点击事件
	 * @param text2
	 *            第二个按钮的文字，为空则会设置默认的文字
	 * @param l2
	 *            第二个按钮的点击事件
	 */
	public ConfirmDialog setTwoButtonListener(String text1, OnConfirmDialogClickListener l1, String text2,
                                              OnConfirmDialogClickListener l2) {
		this.mBtn1Text = text1;
		this.mBtn1Listener = l1;
		this.mBtn2Text = text2;
		this.mBtn2Listener = l2;
		mBtnNum = 2;
		return this;
	}

	/**
	 * 设置两个按钮Dialog的点击事件(左边的按钮是“取消”，右边是“确定”)，点击事件为空则会设置一个默认事件---点击关闭Dialog
	 * 
	 * @param l1
	 *            第一个按钮的点击事件
	 * @param l2
	 *            第二个按钮的点击事件
	 */
	public ConfirmDialog setTwoButtonListener(OnConfirmDialogClickListener l1, OnConfirmDialogClickListener l2) {
		return setTwoButtonListener(null, l1, null, l2);
	}

	public ConfirmDialog setOnDismissListener(OnDismissListener l) {
		this.mOnDismissListener = l;
		return this;
	}

	public ConfirmDialog setCancelable(boolean flag) {
		this.mCancelable = flag;
		return this;
	}

	/**
	 * 显示Dialog
	 */
	public void show() {
		if (!mHasInit) {
			// 初始化title
			ViewUtil.setTextOrHide(mTvTitle, mTitle);
			// 初始化message
			ViewUtil.setTextOrHide(mTvMessage, mMessage);
			// 初始化按钮
			// 1个按钮时
			if (mBtnNum == 1) {
				if (StringUtil.isNotBlank(mSingleBtnText)) {
					mSingleBtn.setText(mSingleBtnText);
				}
				if (mSingleBtnListener == null) {
					mSingleBtn.setOnClickListener(new DialogDismissListener());
				} else {
					mSingleBtn.setOnClickListener(new OnDialogButtonClick(mSingleBtnListener));
				}
				mSingleBtn.setVisibility(View.VISIBLE);
				mTwoBtns.setVisibility(View.GONE);
			} // 2个按钮时
			else {
				if (mBtn1 == null) {
					mBtn1 = (TextView) mTwoBtns.findViewById(R.id.btn1);
				}
				if (StringUtil.isNotBlank(mBtn1Text)) {
					mBtn1.setText(mBtn1Text);
				}
				if (mBtn1Listener == null) {
					mBtn1.setOnClickListener(new DialogDismissListener());
				} else {
					mBtn1.setOnClickListener(new OnDialogButtonClick(mBtn1Listener));
				}
				if (mBtn2 == null) {
					mBtn2 = (TextView) mTwoBtns.findViewById(R.id.btn2);
				}
				if (StringUtil.isNotBlank(mBtn2Text)) {
					mBtn2.setText(mBtn2Text);
				}
				if (mBtn2Listener == null) {
					mBtn2.setOnClickListener(new DialogDismissListener());
				} else {
					mBtn2.setOnClickListener(new OnDialogButtonClick(mBtn2Listener));
				}
				mSingleBtn.setVisibility(View.GONE);
				mTwoBtns.setVisibility(View.VISIBLE);
			}
			mDialog = mBuilder.create();
			mDialog.setOnDismissListener(mOnDismissListener);
			mDialog.setCancelable(mCancelable);
			mDialog.setCanceledOnTouchOutside(mCancelable);
			mHasInit = true;
		}
		try {
			if (mContext != null && !mContext.isFinishing()) {
				mDialog.show();
				// 设置Dialog宽度
				WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
				lp.width = mDialogWidth;
				mDialog.getWindow().setAttributes(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShowing() {
		return mDialog != null && mDialog.isShowing();
	}

	/**
	 * 隐藏Dialog
	 */
	public void dismiss() {
		mDialog.dismiss();
	}

	private class DialogDismissListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			mDialog.dismiss();
		}

	}

	public interface OnConfirmDialogClickListener {
		void onClick(ConfirmDialog dialog, View v);
	}

	private class OnDialogButtonClick implements OnClickListener {
		private OnConfirmDialogClickListener mOnConfirmDialogClickListener;

		public OnDialogButtonClick(OnConfirmDialogClickListener l) {
			this.mOnConfirmDialogClickListener = l;
		}

		@Override
		public void onClick(View v) {
			if (mOnConfirmDialogClickListener != null) {
				mOnConfirmDialogClickListener.onClick(ConfirmDialog.this, v);
			}
		}
	}
}
