package com.ql1d.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import org.apache.commons.codec.binary.Base64;

/**
 * Created by yuxiao on 2015/7/7.
 */
public class SharePrefUtil {
    private static String tag = SharePrefUtil.class.getSimpleName();
    private final static String SP_NAME = "config";
    private static SharedPreferences sp;

    public interface KEY {

        String FUNCTION_ALL_JSON = "all_function_json";//所有的Funcation Json
        String FUNCTION_SELECTED_ID = "selcted_function_ids";//选中的function ids

        String CATE_ALL_JSON = "all_cate_json";//所有的新闻目录 Json
        String CATE_SELECTED_JSON = "selcted_cate_json";//选中的新闻目录ids
        String CATE_EXTEND_ID = "extend_cate_ids";//推荐的新闻 目录ids

        String VOTE_SELECTED_ID = "selcted_vote_ids";//选中的function ids

        String NIGHT_MODE = "night_mode";           //夜间模式
        String NEWS_PUSH = "news_push";             //新闻推送

        String USER_TOKEN = "user_token";           //用户token
        String USER_INFO = "user_info";                 //用户信息
        //        String USER_CITY = "user_city";             //用户city
        String NEWS_FIRST = "news_first";           //
        String USER_LOGIN_TYPE = "user_login_type"; //用户登录类型
        String USER_LOGIN_INFO = "user_login_info"; //用户登录信息
        String CITY_CHANNEL_LIST = "city_channel_list";
        String CITY_CHANNEL = "city_channel";

        String SUBSCRIBE_CHANNEL_LIST = "subscribe_channel_list";   //订阅频道号
        //用户收藏
        String PERSON_NEWS_FAVOR = "person_news_favor";
        //用户订阅
        String PERSON_SUBSCRIBE = "person_subscribe";
        //用户订单
        String PERSON_ORDER_LIST = "person_order_list";
        //活动头部列表
        String ACTIVITY_HEADER_LIST = "activity_header_list";
        //活动热门列表
        String ACTIVITY_HOT_LIST = "activity_hot_list";
        //活动全部列表
        String ACTIVITY_ALL_LIST = "activity_all_list";
        //生活头部列表
        String LIFE_TOP_LIST = "life_top_list";

        //webview字体大小
        String TXT_FONT_SIZE = "txt_font_size";
        //保存 字号选择后的状态
        String TXT_SIZE_STATE = "txt_size_state";

        String BEAUTY_KEY = "beauty_cache";
    }

    /**
     * 保存布尔值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).commit();

    }

    public static void clear(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().clear().commit();
    }

    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 将对象进行base64编码后保存到SharePref中
     *
     * @param context
     * @param key
     * @param object
     */
    public static void saveObj(Context context, String key, Object object) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            // 将对象的转为base64码
//            String objBase64 = new String(Base64.encodeBase64(baos
//                    .toByteArray()));
//
//            sp.edit()
//                    .putString(key,objBase64).commit();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param context
     * @param key
     * @param
     * @return
     */
    public static Object getObj(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String objBase64 = sp.getString(key, null);
        if (TextUtils.isEmpty(objBase64))
            return null;

        // 对Base64格式的字符串进行解码
//        byte[] base64Bytes = Base64.decodeBase64(objBase64.getBytes());
//        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
//        try {
//            ois = new ObjectInputStream(bais);
//            obj = (Object) ois.readObject();
//            ois.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return obj;
    }
}
