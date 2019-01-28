package com.oyp.sort.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * 获取地区号码工具类
 * <p/>
 * created by OuyangPeng on 2017/1/6.
 */
public class AreaCodeUtils {
    private static final String TAG = "AreaCodeUtils";
    // 区号前缀 加号
    public static final String AREA_CODE_PLUS = "+";

    /**
     * 根据国家Code返回国家Name
     *
     * @param countryCode
     * @return
     */
    public static String getCountryNameByCountryCode(Context context, String countryCode) {
        //获得对于的Key值  在R文件中的id
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(countryCode.toUpperCase(), "string", context.getApplicationContext().getPackageName());
        //根据id来获得key对应的value值
        if (0 == resourceId) {
            Log.e(TAG,"countryCode " + countryCode + " is NULL!");
            return "";
        } else {
            return resources.getString(resourceId);
        }
    }
}
