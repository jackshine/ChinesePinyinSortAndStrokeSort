package com.oyp.sort.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.oyp.sort.bean.CountryOrRegion;


/**
 * 获取地区号码工具类
 * <p/>
 * created by OuyangPeng on 2017/1/6.
 */
public class AreaCodeUtils {
    private static final String TAG = "AreaCodeUtils";
    // 区号前缀 加号
    public static final String AREA_CODE_PLUS = "+";

    // 默认区号 中国
    public static final String DEFAULT_AREA_CODE = "86";
    // 默认国家 中国
    public static final String DEFAULT_COUNTRY = "CN";

    // 默认区号 中国香港
    public static final String DEFAULT_AREA_CODE_HK = "852";
    // 默认国家--中国香港
    public static final String DEFAULT_COUNTRY_AREA_HK = "HK";

    // 默认区号 中国台湾
    public static final String DEFAULT_AREA_CODE_TW = "886";
    // 默认国家--中国台湾
    public static final String DEFAULT_COUNTRY_AREA_TW = "TW";


    // 默认区号 印尼
    public static final String DEFAULT_AREA_CODE_IN = "62";
    // 国家--印尼
    public static final String DEFAULT_COUNTRY_AREA_IN = "ID";

    /**
     * 根据国家Code返回国家Name
     *
     * @param countryCode
     * @return
     */
    public static String getCountryNameByCountryCode(Context context, String countryCode) {
        //获得对于的Key值  在R文件中的id
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier(countryCode.toUpperCase(), "string", context.getApplicationContext().getPackageName());
        //根据id来获得key对应的value值
        if (0 == resourceId) {
            Log.e(TAG,"countryCode " + countryCode + " is NULL!");
            return "";
        } else {
            return resources.getString(resourceId);
        }
    }

    private static String getLine1Number(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return null;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"getLine1Number = " + telephonyManager.getLine1Number());
            return telephonyManager.getLine1Number();
        }
        return null;
    }

    private static String getSimCountryIso(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return null;
        }
        Log.d(TAG,"getSimCountryIso = " + telephonyManager.getSimCountryIso());
        return telephonyManager.getSimCountryIso();
    }

}
