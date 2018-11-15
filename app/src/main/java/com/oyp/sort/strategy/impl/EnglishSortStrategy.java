package com.oyp.sort.strategy.impl;

import android.content.Context;
import android.util.Log;

import com.oyp.sort.bean.CountryOrRegion;
import com.oyp.sort.utils.pinyin.OtherLanguageCharacterParser;


/**
 * 根据英文来排序的策略，实际上排序策略和拼音排序是一样的
 */
public class EnglishSortStrategy extends PinyinSortStrategy {
    private static final String TAG = "EnglishSortStrategy";
    /**
     * 英文排序的时候，返回国家码转换后的国家英文名
     *
     * @param countryOrRegion 国家地区封装类
     * @return 国家码转换后的国家英文名
     */
    @Override
    public String getPinyinOrEnglish(CountryOrRegion countryOrRegion) {
        //获取其他国家的英文名：  国家码转换成国家英文名
        String countryCode = countryOrRegion.getCountryCode();
        String countryEnglishName = OtherLanguageCharacterParser.getInstance().getMapData(countryCode);
        Log.d(TAG,"CountryOrRegion countryCode : " + countryCode + " ,countryEnglishName:" + countryEnglishName);
        return countryEnglishName;
    }

    /**
     * 获取 英文排序需要展示的 英文首字母 ,策略和拼音排序策略一样
     *
     * @param pinyin 英文排序需要的英文
     * @return 需要展示的 英文首字母
     */
    @Override
    public String getSortLetters(String pinyin) {
        return super.getSortLetters(pinyin);
    }

    /**
     * 英文排序的 直接返回-1  ,策略和拼音排序策略一样
     */
    @Override
    public int getStrokeCount(String name, Context context) {
        return super.getStrokeCount(name, context);
    }
}
