package com.oyp.sort.utils;

import android.content.Context;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;


import java.util.Locale;

/**
 * 获取手机系统语言.
 */
public class SystemLanguageUtil {
    private static final String TAG = "SystemLanguageUtil";
    public static final String LANGUAGE_ZH_CN_VALUE_DETAILS = "zh-CN";
    /**
     * 中文-大陆简体
     */
    private static final String LANGUAGE_ZH_CN = "zh_CN";
    private static final String LANGUAGE_HANS = "Hans";
    private static final String LANGUAGE_ZH_CN_VALUE = "zh";

    /**
     * 中文-新加坡简体
     */
    private static final String LANGUAGE_ZH_SG = "zh_SG";
    public static final String LANGUAGE_ZH_SG_VALUE = "zh-SG";
    /**
     * 繁体中文
     */
    public static final String LANGUAGE_HANT = "Hant";
    /**
     * 泰文
     */
    private static final String LANGUAGE_TH = "th";
    /**
     * 英文
     */
    private static final String LANGUAGE_EN = "en";
    /**
     * 中文-台湾地区繁体
     */
    private static final String LANGUAGE_ZH_TW = "zh_TW";
    public static final String LANGUAGE_ZH_TW_VALUE = "zh-TW";

    /**
     * 中文-香港地区繁体
     */
    private static final String LANGUAGE_ZH_HK = "zh_HK";
    public static final String LANGUAGE_ZH_HK_VALUE = "zh-HK";

    /**
     * 印尼语
     */
    private static final String LANGUAGE_INA = "in";

    /**
     * 中文-澳门地区繁体
     */
    private static final String LANGUAGE_ZH_MO = "zh_MO";
    public static final String LANGUAGE_ZH_MO_VALUE = "zh-MO";

    /**
     * 中文-大陆简体-数字值
     */
    private static final String LANGUAGE_ZH_CN_0 = "0";
    private static final int INT_LANGUAGE_ZH_CN_0 = 0;

    /**
     * 泰文-数字值
     */
    private static final String LANGUAGE_TH_1 = "1";
    private static final int INT_LANGUAGE_TH_1 = 1;

    /**
     * 英文-数字值
     */
    private static final String LANGUAGE_EN_2 = "2";
    private static final int INT_LANGUAGE_EN_2 = 2;

    /**
     * 中文-香港台湾地区繁体-数字值
     */
    private static final String LANGUAGE_ZH_TW_3 = "3";
    private static final int INT_LANGUAGE_ZH_TW_3 = 3;

    /**
     * 印度尼西亚-数字值
     */
    private static final String LANGUAGE_INA_4 = "4";
    private static final int INT_LANGUAGE_INA_4 = 4;


    /**
     * 获取 header 需要的 APP 语言参数，0中文、1泰文、2英文
     *
     * @return header 需要的 APP 语言参数
     */
    public static String getAppLanguageForHeader(Context context) {
        Locale locale = getSysPreferredLocale(context);
        String language = locale.getLanguage();
        Log.d(TAG,"getAppLanguageForHeader locale:" + locale.toString());
        Log.d(TAG,"getAppLanguageForHeader getAppLanguageForSSO language: " + language);
        Log.d(TAG,"getAppLanguageForHeader locale.getCountry():" + locale.getCountry());

        String strLocale = locale.toString();
        Log.d(TAG,"strLocale:" + strLocale);

        if (strLocale.contains(LANGUAGE_ZH_CN)) {
            return LANGUAGE_ZH_CN_0;
        } else if (strLocale.contains(LANGUAGE_ZH_SG)) {
            return LANGUAGE_ZH_CN_0;
        } else if (strLocale.contains(LANGUAGE_ZH_TW)) {
            return LANGUAGE_ZH_TW_3;
        } else if (strLocale.contains(LANGUAGE_ZH_HK)) {
            return LANGUAGE_ZH_TW_3;
        } else if (strLocale.contains(LANGUAGE_ZH_MO)) {
            return LANGUAGE_ZH_TW_3;
        } else if (strLocale.contains(LANGUAGE_TH)) {
            return LANGUAGE_TH_1;
        } else if (strLocale.contains(LANGUAGE_INA)) {
            return LANGUAGE_INA_4;
        } else {
            return LANGUAGE_EN_2;
        }
    }

    /**
     * 获取Android系统Locale
     * 参考链接：https://blog.csdn.net/ys743276112/article/details/71547134
     *
     * @return Android系统Locale
     */
    public static Locale getLocal() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
            //Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 获取系统首选语言
     * <p>
     * 注意：该方法获取的是用户实际设置的不经API调整的系统首选语言
     */
    public static Locale getSysPreferredLocale(Context context) {
        Locale locale = getLocal();
        if (LANGUAGE_ZH_CN_VALUE.equals(getSystemLanguage(context))) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else if (LANGUAGE_ZH_TW_VALUE.equals(getSystemLanguage(context)) || LANGUAGE_ZH_HK_VALUE.equals(getSystemLanguage(context))) {
            locale = Locale.TRADITIONAL_CHINESE;
        }
        return locale;
    }

    public static String getLocalCountry(Context context) {
        String country = getSysPreferredLocale(context).getCountry();
        Log.d(TAG,"getLocalCountry = " + country);
        return country;
    }

    /**
     * 兼容7.0系统，获取Android系统语言，比如：zh-CN zh-HK en-US
     * <p>
     * 参考链接：https://blog.csdn.net/ys743276112/article/details/71547134
     *
     * @return Android系统语言
     */
    public static String getLocalLanguageAndCountry(Context context) {
        Locale locale = getSysPreferredLocale(context);
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    /**
     * 获取当前系统的语言环境
     */
    public static String getSystemLanguage(Context context) {
        if (context == null) {
            return null;
        }
        Locale locale = getLocal();
        Log.d(TAG,"getSystemLanguage locale--->" + locale);
        String language = locale.getLanguage();
        Log.d(TAG,"getSystemLanguage language--->" + language);
        String strLocale = locale.toString();
        Log.d(TAG,"getSystemLanguage strLocale:" + strLocale);
        //业务逻辑
        if (strLocale.contains(LANGUAGE_ZH_CN) || strLocale.contains(LANGUAGE_ZH_SG) || strLocale.contains(LANGUAGE_HANS)) {
            return LANGUAGE_ZH_CN_VALUE;
        } else if (strLocale.contains(LANGUAGE_ZH_TW) || strLocale.contains(LANGUAGE_ZH_HK)
                || strLocale.contains(LANGUAGE_ZH_MO) || strLocale.contains(LANGUAGE_HANT)) {
            return LANGUAGE_ZH_HK_VALUE;
        } else if (strLocale.contains(LANGUAGE_TH)) {
            return LANGUAGE_TH;
        } else if (strLocale.contains(LANGUAGE_INA)) {
            return LANGUAGE_INA;
        } else {
            return LANGUAGE_EN;
        }
    }

}
