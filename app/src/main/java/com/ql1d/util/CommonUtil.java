package com.ql1d.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.verify.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by yuxiao on 2015/7/7.
 */
public class CommonUtil {

    public static File cameraFile;
    public static final int CAMERA_PHOTO = 10002;
    public static final int MODIFY_NICKNAME = 10003;//修改昵称成功返回码
    public static final int MODIFY_PSW = 10004;//修改密码成功返回码


    /**
     * 网络是否在线
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否后台运行
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * return if str is empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("");
    }


    /**
     * 手机匹配
     *
     * @param mobile
     * @return
     */
    public static boolean mobilePatten(String mobile) {
        String regExp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.find();

    }

    /**
     * 按照宽度等比缩放
     *
     * @param url
     * @param maxWidth
     * @return
     */
    public static String formatUrlWidth(String url, int maxWidth) {
        return formatUrl(url, 2, maxWidth, 0);
    }

    /**
     * 按照高度等比缩放
     *
     * @param url
     * @param maxHeight
     * @return
     */
    public static String formatUrlHeight(String url, int maxHeight) {
        return formatUrl(url, 3, 0, maxHeight);
    }

    /**
     * 按照自定义缩放切图
     *
     * @param url
     * @param width
     * @param heigth
     * @return
     */
    public static String formatUrlCustom(String url, int width, int heigth) {
        return formatUrl(url, 1, width, heigth);
    }

    /**
     * 格式化图片URL
     *
     * @param url
     * @param type      1 根据给定大小返回图片; 2 根据宽度进行比例缩放；3 根据高度比例缩放
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static String formatUrl(String url, int type, int maxWidth, int maxHeight) {
//        http://image.qlwbyidian.com/Che2-TL00M__1439878833227__293054_4103?imageView2/4/w/600/h/100
        String fomat = "%1$s?imageView2/%2$d/w/%3$d/h/%4$d";
        return String.format(fomat, url, type, maxWidth, maxHeight);
    }


    public static void showInfoDialog(Context context, String message) {
        showInfoDialog(context, message, "提示", "确定", null);
    }




    public static void showInfoDialog(Context context, String message,
                                      String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (onClickListener == null)
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            };
        localBuilder.setPositiveButton(positiveStr, onClickListener);
        localBuilder.show();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String md5(String string) {
        return md5(string, true);
    }

    /**
     * MD5加密方法
     *
     * @param paramString 需要加密的字符串
     * @param isUpperCase 是否转换为大写，默认为true
     * @return
     */
    public static String md5(String paramString, boolean isUpperCase) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest(), isUpperCase);
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b, boolean isUpper) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            if (isUpper) {
                hexString.append(hex.toUpperCase());
            } else {
                hexString.append(hex);
            }
        }
        return hexString.toString();
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = null;
        try {
            connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */

    public static String getStringDateYMD() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenPicHeight(int screenWidth, Bitmap bitmap) {
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int picScreenHeight = 0;
        picScreenHeight = (screenWidth * picHeight) / picWidth;
        return picScreenHeight;
    }


    private static Drawable createDrawable(Drawable d, Paint p) {

        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap b = bd.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(),
                bd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(b, 0, 0, p); // 关键代码，使用新的Paint画原图，

        return new BitmapDrawable(bitmap);
    }

    /**
     * 设置Selector。 本次只增加点击变暗的效果，注释的代码为更多的效果
     */
    public static StateListDrawable createSLD(Context context, Drawable drawable) {
        StateListDrawable bg = new StateListDrawable();
        int brightness = 50 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Drawable normal = drawable;
        Drawable pressed = createDrawable(drawable, paint);
        bg.addState(new int[]{android.R.attr.state_selected}, pressed);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    public static Bitmap getImageFromAssetsFile(Context ct, String fileName) {
        Bitmap image = null;
        AssetManager am = ct.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    public static String getUploadtime(long created) {
        StringBuffer when = new StringBuffer();
        int difference_seconds;
        int difference_minutes;
        int difference_hours;
        int difference_days;
        int difference_months;
        long curTime = System.currentTimeMillis();
        difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
        if (difference_months > 0) {
            when.append(difference_months + "月");
        }

        difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
        if (difference_days > 0) {
            when.append(difference_days + "天");
        }

        difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
        if (difference_hours > 0) {
            when.append(difference_hours + "小时");
        }

        difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
        if (difference_minutes > 0) {
            when.append(difference_minutes + "分钟");
        }

        difference_seconds = (int) ((curTime % 60) - (created % 60));
        if (difference_seconds > 0) {
            when.append(difference_seconds + "秒");
        }

        return when.append("前").toString();
    }

    public static boolean hasToken(Context ct) {
        String token = SharePrefUtil.getString(ct, "token", "");
        return !TextUtils.isEmpty(token);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static String formatDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(Long.parseLong(data)));
    }

    public static String formatDates(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(Long.parseLong(data)));
    }
    public  static String formatDatess(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        String format = sdf.format(new Date(Long.parseLong(data)));

        return sdf.format(new Date(Long.parseLong(data)));
    }

    /*
    随机生成字符串
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取屏幕高度和宽带
     *
     * @param mContext
     * @return int[高，宽]
     */
    public static int[] getScreen(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);

        // 窗口的宽度
        int screenWidth = dm.widthPixels;

        // 窗口高度
        int screenHeight = dm.heightPixels;
        int screen[] = {screenHeight, screenWidth};
        return screen;

    }

    /**
     * 设置View的高度
     *
     * @param v
     * @param height
     */
    public static void setViewHeight2(View v, int height, boolean isLinearLayout) {
        if (isLinearLayout) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) v
                    .getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams.height = height;// 当控件的高强制设成height
            v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
        } else {
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) v
                    .getLayoutParams(); // 取控件mGrid当前的布局参数
            relativeParams.height = height;// 当控件的高强制设成height
            v.setLayoutParams(relativeParams); // 使设置好的布局参数应用到控件myGrid
        }

    }
    public static void setViewHeight3(View v, int height, boolean isLinearLayout) {
        if (isLinearLayout) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) v
                    .getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams.height = height;// 当控件的高强制设成height
            v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
        } else {
            FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams) v
                    .getLayoutParams(); // 取控件mGrid当前的布局参数
            relativeParams.height = height;// 当控件的高强制设成height
            v.setLayoutParams(relativeParams); // 使设置好的布局参数应用到控件myGrid
        }

    }
    /**
     * 照相获取图片
     *
     * @param mContext
     * @return 图片路径
     */
    public static void selectPicFromCamera(Context mContext) {
        if (!CommonUtil.GetSDState()) {
            CustomToast.showMessage(mContext, "SD卡不存在，不能拍照",
                    Toast.LENGTH_SHORT, CustomToast.CENTER);
            return;
        }
        cameraFile = new File(CommonUtil.getFileAddress(0, mContext.getResources()
                .getString(R.string.app_name), mContext), "userface"
                + System.currentTimeMillis() + ".png");
        try {
            deleteFile(cameraFile);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            cameraFile.getParentFile().mkdirs();
            ((Activity) mContext).startActivityForResult(new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                            MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                    CAMERA_PHOTO);
        }

    }

    /**
     * 返回SD卡存储路径
     *
     * @param state   1：picture 2:video 3:voice 4:ceche 其他是cache
     * @param appName 项目名称
     * @return
     */
    public static String getFileAddress(int state, String appName,
                                        Context mContext) {

        String Address = "";

        if (GetSDState()) {

            Address = Environment.getExternalStorageDirectory().getPath() + "/"
                    + appName + "/";
        } else {
            Address = mContext.getCacheDir().getAbsolutePath() + "/" + appName
                    + "/";
        }
        switch (state) {
            case 1:
                Address = Address + "cache1/";
                break;
            case 2:
                Address = Address + "video/";
                break;
            case 3:
                Address = Address + "voice/";
                break;
            case 4:
                Address = Address + "file/";
                break;
            case 5:
                Address = Address + "photos/";
                break;
            default:
                Address = Address + "cache/";
                break;
        }
        File baseFile = new File(Address);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        return Address;
    }

    // 返回是否有SD卡
    public static boolean GetSDState() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * @param @param file 设定文件
     * @return void 返回类型
     * @throws
     * @Title: deleteFile
     * @Description: TODO(删除文件)
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {

        }
    }

    /**
     * 开启activity
     */
    public static void launchActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    /**
     * 开启activity(带参数)
     */
    public static void launchActivity(Context context, Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    /**
     * 开启activity(带参数)
     */
    public static void launchActivity(Context context, Class<?> activity, String key, int value) {
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        launchActivity(context, activity, bundle);
    }

    public static void launchActivity(Context context, Class<?> activity, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        launchActivity(context, activity, bundle);
    }

    public static void launchActivityForResult(Activity context, Class<?> activity, int requestCode) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivityForResult(intent, requestCode);
    }

    public static void launchActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动一个服务
     */
    public static void launchService(Context context, Class<?> service) {
        Intent intent = new Intent(context, service);
        context.startService(intent);
    }

    public static void stopService(Context context, Class<?> service) {
        Intent intent = new Intent(context, service);
        context.stopService(intent);
    }

    /**
     * 判断字符串是否为空
     *
     * @param text
     * @return true null false !null
     */
    public static boolean isNull(CharSequence text) {
        return text == null || "".equals(text.toString().trim()) || "null".equals(text);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 通过Uri获取图片路径
     */
    public static String query(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
        cursor.moveToNext();
        return cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
    }

    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeybord(Activity activity) {

        if (null == activity) {
            return;
        }
        try {
            final View v = activity.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
}
