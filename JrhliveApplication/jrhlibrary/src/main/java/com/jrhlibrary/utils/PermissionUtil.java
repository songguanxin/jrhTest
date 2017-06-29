package com.jrhlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.jrhlibrary.dialog.ConfirmDialog;


/**
 * 检查权限工具类
 *
 */
public class PermissionUtil {

    public static boolean check(final Activity activity, String permission) {
        return check(activity, permission, 0);
    }

    public static boolean check(final Activity activity, String[] permissions) {
        return check(activity, permissions, 0);
    }

    public static boolean check(final Activity activity, String permission, int requestCode) {
        return check(activity, new String[] { permission }, requestCode);
    }

    /**
     * @return true 权限验证通过；false 未通过
     */
    public static boolean check(final Activity activity, String[] permissions, int requestCode) {
        if (checkSelfPermission(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false;
        }
        return true;
    }

    private static boolean checkSelfPermission(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldShow(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查用户是否授权
     * @return true：全部授权；false：未(全部)授权
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see #showTips(Activity, String[])
     */
    public static boolean showTips(final Activity activity, String permission) {
        return showTips(activity, new String[] { permission });
    }

    public static boolean showTips(final Activity activity, String[] permissions) {
        return showTips(activity, permissions, false);
    }

    /**
     * 假如用户点击了“不再提示”则弹出提示，并返回true；否则不执行任何操作，返回false
     */
    public static boolean showTips(final Activity activity, String[] permissions, boolean finishOnDismiss) {
        if (activity == null || activity.isFinishing()) {
            return false;
        }
        if (!shouldShow(activity, permissions)) {
            ConfirmDialog confirmDialog = new ConfirmDialog(activity).setTitle(getTipsMsg(permissions)).setTwoButtonListener(null, null, "去设置", new ConfirmDialog.OnConfirmDialogClickListener() {

                @Override
                public void onClick(ConfirmDialog dialog, View v) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivity(intent);
                    dialog.dismiss();
                }
            });
            if (finishOnDismiss) {
                confirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.finish();
                    }
                });
            }
            confirmDialog.show();
            return true;
        }
        return false;
    }

    private static String getTipsMsg(String[] permissions) {
        return "在权限中开启" + getPermissionName(permissions) + "权限，以正常使用";
    }

    private static String getPermissionName(String[] permissions) {
        StringBuilder names = new StringBuilder();
        for (String permission : permissions) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                names.append("、存储空间");
            } else if (Manifest.permission.CAMERA.equals(permission)) {
                names.append("、相机");
            } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
                names.append("、麦克风");
            }
        }
        if (names.length() > 0) {
            names.deleteCharAt(0);
        }
        return names.toString();
    }

    public static boolean onRequestPermissionsResult(Activity activity, String[] permissions, int[] grantResults) {
        return onRequestPermissionsResult(activity, permissions, grantResults, false);
    }

    /**
     * @return true 权限验证通过；false 验证失败，并弹出提示框
     */
    public static boolean onRequestPermissionsResult(Activity activity, String[] permissions, int[] grantResults, boolean finishOnDismiss) {
        if (!verifyPermissions(grantResults)) {
            showTips(activity, permissions, finishOnDismiss);
            return false;
        }
        return true;
    }

}
