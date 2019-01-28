package com.oyp.sort.utils.pinyin;


import android.util.Log;

import com.oyp.sort.MyApplication;
import com.oyp.sort.utils.AreaCodeUtils;

/**
 * 除了中国之外的国家列表按英文单词排序.
 */
public class OtherLanguageCharacterParser {

    private static OtherLanguageCharacterParser hkCharacterParser = new OtherLanguageCharacterParser();

    public static OtherLanguageCharacterParser getInstance() {
        return hkCharacterParser;
    }

    public String getEnglishName(String strCode) {
        String strResult = "";
        // 将国家代码转换一下， 比如 CN  转为 SORT_CN
        strCode = "SORT_" + strCode;
        //获取到要显示的国家或地区的名称
        strResult = AreaCodeUtils.getCountryNameByCountryCode(MyApplication.getContext(), strCode);
        Log.d("OtherCharacterParser", "strCode = " + strCode + " ,strResult:" + strResult);
        return strResult;
    }

}
