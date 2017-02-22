package com.jrhlibrary.widgets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;

public class LoadingDialog {
	private ProgressDialog mProgress;
	private boolean mCancelable = true;
	private Activity mContext;

	public LoadingDialog(Activity context) {
		this.mContext = context;
		mProgress = new ProgressDialog(context);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setIndeterminate(false);
		mProgress.setCanceledOnTouchOutside(false);
		mProgress.setCancelable(mCancelable);
		mProgress.setMessage("加载中");
	}

	public LoadingDialog setMessage(String message) {
		mProgress.setMessage(message);
		return this;
	}

	/**
	 * 设置是否可以通过back键关闭dialog
	 */
	public void setCancelable(boolean flag) {
		this.mCancelable = flag;
	}

	public LoadingDialog show() {
		try {
			if (mContext != null && !mContext.isFinishing()) {
                mProgress.show();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public LoadingDialog show(String msg) {
		mProgress.setMessage(msg);
		show();
		return this;
	}

	public void dismiss() {
		try {
			if (mProgress != null && mProgress.isShowing()
					&& mContext != null && !mContext.isFinishing()) {
				mProgress.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		mProgress.setOnCancelListener(onCancelListener);
	}
}
