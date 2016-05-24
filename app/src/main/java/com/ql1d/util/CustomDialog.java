/**
 * author liyu
 * date 2014.07.21
 */
package com.ql1d.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ql1d.verify.R;


public class CustomDialog extends Dialog {
	Context context;
	/**
	 * "信息"图标
	 */
	public static int INFORMATION = R.mipmap.ico_dialog_information;
	/**
	 * "询问"图标
	 */
	public static int QUESTION = R.mipmap.ico_dialog_question;
	/**
	 * "警告"图标
	 */
	public static int WARNING = R.mipmap.ico_dialog_warning;
	/**
	 * "错误"图标
	 */
	public static int ERROR = R.mipmap.ico_dialog_error;

	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static class Builder {
		private Context context;
		private int buttonNum;
		private String title;
		private String message;
		private int icon;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;

		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;
		private DialogInterface.OnKeyListener keyListener;

		public Builder(Context context) {
			this.context = context;
		}

		private Builder setButtonNum(int buttonNum) {
			this.buttonNum = buttonNum;
			return this;
		}

		private Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		private Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		private Builder setIcon(int icon) {
			this.icon = icon;
			return this;
		}

		private Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		private Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		private Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		private Builder setPositiveButton(int positiveButtonText,
										  DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		private Builder setPositiveButton(String positiveButtonText,
										  DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		private Builder setNegativeButton(int negativeButtonText,
										  DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		private Builder setNegativeButton(String negativeButtonText,
										  DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		private Builder setOnKeyListener(DialogInterface.OnKeyListener listener) {
			this.keyListener = listener;
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		private CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CustomDialog dialog = new CustomDialog(context,
					R.style.dialog);
			View layout = inflater.inflate(R.layout.custom_dialog, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			((TextView) layout.findViewById(R.id.title)).setText(title);
			switch (buttonNum) {
				case 1:
					layout.findViewById(R.id.div_neutral).setVisibility(View.GONE);
					layout.findViewById(R.id.neutralButton)
							.setVisibility(View.GONE);
					layout.findViewById(R.id.div_negative).setVisibility(View.GONE);
					layout.findViewById(R.id.negativeButton).setVisibility(
							View.GONE);
					layout.findViewById(R.id.positiveButton).setBackgroundResource(
							R.drawable.selector_dialog_btn_one);
					break;
				case 2:
					layout.findViewById(R.id.div_neutral).setVisibility(View.GONE);
					layout.findViewById(R.id.neutralButton)
							.setVisibility(View.GONE);
					break;
			}
			if (icon != 0) {
				((ImageView) layout.findViewById(R.id.customdialogicon))
						.setImageResource(icon);
			}
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
									dialog.cancel();
								}
							});
				}
			} else {
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}

			if (keyListener != null) {
				dialog.setOnKeyListener(keyListener);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.dialogcon))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.dialogcon)).addView(
						contentView, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}

	// （1）///////////////////创建带有"确定"按钮的对话框///////////////////////////////////////
	// （1.1）////////创建带有"信息"图标和"确定"按钮的对话框/////////////////////////////
	/**
	 * 创建一个带有"信息"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithInformation(Context context,
													 String title, String message,
													 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_information).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"信息"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithInformation(Context context,
													 int titleId, String message,
													 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_information).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"信息"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithInformation(Context context,
													 String title, int messageId,
													 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_information)
				.setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"信息"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithInformation(Context context,
													 int titleId, int messageId,
													 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_information)
				.setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （1.2）////////创建带有"警告"图标和"确定"按钮的对话框/////////////////////////////
	/**
	 * 创建一个带有"警告"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithWarning(Context context, String title,
												 String message, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithWarning(Context context, int titleId,
												 String message, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithWarning(Context context, String title,
												 int messageId, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithWarning(Context context, int titleId,
												 int messageId, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （1.3）////////创建带有"错误"图标和"确定"按钮的对话框/////////////////////////////
	/**
	 * 创建一个带有"错误"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithError(Context context, String title,
											   String message, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithError(Context context, int titleId,
											   String message, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(message)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithError(Context context, String title,
											   int messageId, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOKDialogWithError(Context context, int titleId,
											   int messageId, OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(messageId)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （1.4）////////创建带有自定义图标和自定义按钮的对话框/////////////////////////////
	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param buttontext
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, String title,
											 String message, String buttontext, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title).setIcon(icoId)
				.setMessage(message)
				.setPositiveButton(buttontext, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param buttontext
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, int titleId,
											 String message, String buttontext, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId).setIcon(icoId)
				.setMessage(message)
				.setPositiveButton(buttontext, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param buttontext
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, String title,
											 int messageId, String buttontext, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title).setIcon(icoId)
				.setMessage(messageId)
				.setPositiveButton(buttontext, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param buttontext
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, int titleId,
											 int messageId, String buttontext, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId).setIcon(icoId)
				.setMessage(messageId)
				.setPositiveButton(buttontext, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param buttontextId
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, String title,
											 String message, int buttontextId, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title).setIcon(icoId)
				.setMessage(message)
				.setPositiveButton(buttontextId, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param buttontextId
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, int titleId,
											 String message, int buttontextId, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId).setIcon(icoId)
				.setMessage(message)
				.setPositiveButton(buttontextId, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param buttontextId
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, String title,
											 int messageId, int buttontextId, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(title).setIcon(icoId)
				.setMessage(messageId)
				.setPositiveButton(buttontextId, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和自定义按钮文字的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param buttontextId
	 *            按钮文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 */
	public static void CreateOneButtonDialog(Context context, int titleId,
											 int messageId, int buttontextId, int icoId,
											 OnClickListener positiveBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1).setTitle(titleId).setIcon(icoId)
				.setMessage(messageId)
				.setPositiveButton(buttontextId, positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （2）/////////////////////创建带有"确定"和"取消"按钮的对话框/////////////////////////
	// （2.1）/////////////创建带有"询问"图标和"确定"、"取消"按钮的对话框////////////////////
	/**
	 * 创建一个带有"询问"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithQuestion(Context context,
														String title, int messageId,
														OnClickListener positiveBtnOnClickListener,
														OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_question).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"询问"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithQuestion(Context context,
														int titleId, String message,
														OnClickListener positiveBtnOnClickListener,
														OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_question).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"询问"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithQuestion(Context context,
														int titleId, int messageId,
														OnClickListener positiveBtnOnClickListener,
														OnClickListener negativeBtnOnClickListener,
														OnKeyListener keylistener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_question).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener)
				.setOnKeyListener(keylistener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"询问"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithQuestion(Context context,
														String title, String message,
														OnClickListener positiveBtnOnClickListener,
														OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_question).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （2.2）/////////////创建带有"警告"图标和"确定"、"取消"按钮的对话框////////////////////
	/**
	 * 创建一个带有"警告"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithWarning(Context context,
													   String title, int messageId,
													   OnClickListener positiveBtnOnClickListener,
													   OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithWarning(Context context,
													   int titleId, String message,
													   OnClickListener positiveBtnOnClickListener,
													   OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithWarning(Context context,
													   int titleId, int messageId,
													   OnClickListener positiveBtnOnClickListener,
													   OnClickListener negativeBtnOnClickListener,
													   OnKeyListener keylistener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener)
				.setOnKeyListener(keylistener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"警告"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithWarning(Context context,
													   String title, String message,
													   OnClickListener positiveBtnOnClickListener,
													   OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_warning).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （2.3）/////////////创建带有"错误"图标和"确定"、"取消"按钮的对话框////////////////////
	/**
	 * 创建一个带有"错误"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithError(Context context,
													 String title, int messageId,
													 OnClickListener positiveBtnOnClickListener,
													 OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithError(Context context,
													 int titleId, String message,
													 OnClickListener positiveBtnOnClickListener,
													 OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithError(Context context,
													 int titleId, int messageId,
													 OnClickListener positiveBtnOnClickListener,
													 OnClickListener negativeBtnOnClickListener,
													 OnKeyListener keylistener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(messageId)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener)
				.setOnKeyListener(keylistener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有"错误"图标和"确定"、"取消"按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param positiveBtnOnClickListener
	 *            "确定"按钮处理事件
	 * @param negativeBtnOnClickListener
	 *            "取消"按钮处理事件
	 */
	public static void CreateOKCancelDialogWithError(Context context,
													 String title, String message,
													 OnClickListener positiveBtnOnClickListener,
													 OnClickListener negativeBtnOnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title)
				.setIcon(R.mipmap.ico_dialog_error).setMessage(message)
				.setNegativeButton("取消", negativeBtnOnClickListener)
				.setPositiveButton("确定", positiveBtnOnClickListener);
		customBuilder.create().show();
	}

	// （2.4）/////////////创建一个带有自定义图标和两个自定义按钮的对话框////////////////////
	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param button1text
	 *            按钮1文字
	 * @param button2text
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 String title, String message, String button1text,
											 String button2text, int icoId,
											 OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title).setIcon(icoId)
				.setMessage(message)
				.setNegativeButton(button1text, button1OnClickListener)
				.setPositiveButton(button2text, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param button1text
	 *            按钮1文字
	 * @param button2text
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 int titleId, int messageId, String button1text, String button2text,
											 int icoId, OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId).setIcon(icoId)
				.setMessage(messageId)
				.setNegativeButton(button1text, button1OnClickListener)
				.setPositiveButton(button2text, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param button1text
	 *            按钮1文字
	 * @param button2text
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 int titleId, String message, String button1text,
											 String button2text, int icoId,
											 OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId).setIcon(icoId)
				.setMessage(message)
				.setNegativeButton(button1text, button1OnClickListener)
				.setPositiveButton(button2text, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param button1text
	 *            按钮1文字
	 * @param button2text
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 String title, int messageId, String button1text,
											 String button2text, int icoId,
											 OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title).setIcon(icoId)
				.setMessage(messageId)
				.setNegativeButton(button1text, button1OnClickListener)
				.setPositiveButton(button2text, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param button1textId
	 *            按钮1文字
	 * @param button2textId
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 String title, String message, int button1textId, int button2textId,
											 int icoId, OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title).setIcon(icoId)
				.setMessage(message)
				.setNegativeButton(button1textId, button1OnClickListener)
				.setPositiveButton(button2textId, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param button1textId
	 *            按钮1文字
	 * @param button2textId
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 int titleId, int messageId, int button1textId, int button2textId,
											 int icoId, OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId).setIcon(icoId)
				.setMessage(messageId)
				.setNegativeButton(button1textId, button1OnClickListener)
				.setPositiveButton(button2textId, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param message
	 *            对话框提示内容
	 * @param button1textId
	 *            按钮1文字
	 * @param button2textId
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 int titleId, String message, int button1textId, int button2textId,
											 int icoId, OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(titleId).setIcon(icoId)
				.setMessage(message)
				.setNegativeButton(button1textId, button1OnClickListener)
				.setPositiveButton(button2textId, button2OnClickListener);
		customBuilder.create().show();
	}

	/**
	 * 创建一个带有自定义图标和两个自定义按钮的对话框
	 *
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param messageId
	 *            对话框提示内容
	 * @param button1textId
	 *            按钮1文字
	 * @param button2textId
	 *            按钮2文字
	 * @param icoId
	 *            图标 CustomDialog.INFORMATION 信息 CustomDialog.WARNING 警告
	 *            CustomDialog.ERROR 错误
	 * @param button1OnClickListener
	 *            按钮1处理事件
	 * @param button2OnClickListener
	 *            按钮2处理事件
	 */
	public static void CreateTwoButtonDialog(Context context,
											 String title, int messageId, int button1textId, int button2textId,
											 int icoId, OnClickListener button1OnClickListener,
											 OnClickListener button2OnClickListener) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(2).setTitle(title).setIcon(icoId)
				.setMessage(messageId)
				.setNegativeButton(button1textId, button1OnClickListener)
				.setPositiveButton(button2textId, button2OnClickListener);
		customBuilder.create().show();
	}


	/**
	 * 创建一个带有"下载"图标和"确定"按钮的对话框
	 *
	 * @param context
	 * @param titleId
	 *            对话框标题
	 * @param positiveBtnOnClickListener
	 *            "取消"按钮处理事件
	 * @param v
	 * 			  中间部分的试图
	 */
	public static Builder CreateDownloadDialogWithCal(Context context, String titleId,
													  OnClickListener positiveBtnOnClickListener,View v) {
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setButtonNum(1)
				.setTitle(titleId)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setContentView(v)
				.setPositiveButton("取消", positiveBtnOnClickListener);
		return customBuilder;
	}
	public static Dialog creatDialog(Builder builder){
		return builder.create();
	}
}
