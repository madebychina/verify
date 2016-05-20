/**
 * author liyu
 * date 2014.07.20
 */
package com.ql1d.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.verify.R;

/**
 * Toast工具类
 * 
 */
public class CustomToast {
	private static Toast toast = null;
	/**
	 * 默认位置
	 */
	public static int DEFAULT = 0;
	/**
	 * 相对屏幕左边对齐、垂直居中
	 */
	public static int LEFT = 1;
	/**
	 * 相对屏幕顶端对齐、水平居中
	 */
	public static int TOP = 2;
	/**
	 * 相对屏幕右边对齐、垂直居中
	 */
	public static int RIGHT = 3;
	/**
	 * 相对屏幕底端对齐、水平居中
	 */
	public static int BOTTOM = 4;
	/**
	 * 相对屏幕水平、垂直居中
	 */
	public static int CENTER = 5;

	private static int[] gravitys = new int[] { -1, Gravity.LEFT, Gravity.TOP,
			Gravity.RIGHT, Gravity.BOTTOM, Gravity.CENTER };

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * 
	 * 
	 * @param context
	 * @param message
	 */
	public static void showMessageShort(final Context context,
			final String message) {
		showMessage(context, message, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * 
	 * 
	 * @param context
	 * @param message
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessageShort(final Context context,
			final String message, final int gravity) {
		showMessage(context, message, Toast.LENGTH_SHORT, gravity);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * 
	 * 
	 * @param context
	 * @param message
	 */
	public static void showMessageLong(final Context context,
			final String message) {
		showMessage(context, message, Toast.LENGTH_LONG);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * 
	 * 
	 * @param context
	 * @param message
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessageLong(final Context context,
			final String message, final int gravity) {
		showMessage(context, message, Toast.LENGTH_LONG, gravity);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * 
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showMessageShort(final Context context, final int resId) {
		showMessage(context, resId, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_SHORT
	 * 
	 * 
	 * @param context
	 * @param resId
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessageShort(final Context context, final int resId,
			final int gravity) {
		showMessage(context, resId, Toast.LENGTH_SHORT, gravity);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * 
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showMessageLong(final Context context, final int resId) {
		showMessage(context, resId, Toast.LENGTH_LONG);
	}

	/**
	 * Toast发送消息，默认Toast.LENGTH_LONG
	 * 
	 * 
	 * @param context
	 * @param resId
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessageLong(final Context context, final int resId,
			final int gravity) {
		showMessage(context, resId, Toast.LENGTH_LONG, gravity);
	}

	/**
	 * Toast发送消息，自定义显示时间
	 * 
	 * 
	 * @param context
	 * @param resId
	 * @param duration
	 *            单位：ms
	 */
	public static void showMessage(final Context context, final int resId,
			final int duration) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, duration);
		toast.show();
	}

	/**
	 * Toast发送消息，自定义显示时间和位置
	 * 
	 * 
	 * @param context
	 * @param resId
	 * @param duration
	 *            单位：ms
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessage(final Context context, final int resId,
			final int duration, final int gravity) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, duration);
		if (gravity > DEFAULT) {
			toast.setGravity(gravitys[gravity], 0, 0);
		}
		toast.show();
	}

	/**
	 * Toast发送消息，自定义显示时间
	 * 
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 *            单位：ms
	 */
	public static void showMessage(final Context context, final String message,
			final int duration) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	/**
	 * Toast发送消息，自定义显示时间和位置
	 * 
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 *            单位：ms
	 * @param gravity
	 *            显示位置: ToastUtils.DEFAULT:默认位置 ToastUtils.LEFT:相对屏幕左边对齐、垂直居中
	 *            ToastUtils.TOP:相对屏幕顶端对齐、水平居中 ToastUtils.RIGHT:相对屏幕右边对齐、垂直居中
	 *            ToastUtils.BOTTOM:相对屏幕底端对齐、水平居中 ToastUtils.CENTER:相对屏幕水平、垂直居中
	 */
	public static void showMessage(final Context context, final String message,
			final int duration, final int gravity) {
		if (toast != null) {
			toast.cancel();
		}
		toast = new Toast(context.getApplicationContext());
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout, null);
		TextView tv_toast = (TextView)layout.findViewById(R.id.tv_toast);
		tv_toast.setText(message);
		toast.setView(layout);
		if (gravity > DEFAULT) {
			toast.setGravity(gravitys[gravity], 0, 0);
		}
		toast.setDuration(duration);
		toast.show();
	}
}
