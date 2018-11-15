package com.oyp.sort.strategy.impl;

import android.content.Context;
import android.util.Log;

import com.oyp.sort.R;
import com.oyp.sort.adapter.CountryOrRegionAdapter;
import com.oyp.sort.bean.CountryOrRegion;
import com.oyp.sort.strategy.ISortStrategy;
import com.oyp.sort.utils.stroke.bean.Stroke;
import com.oyp.sort.utils.stroke.utils.StrokeUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 根据汉字的笔画来排序的策略
 */
public class StrokeSortStrategy implements ISortStrategy {
    private static final String TAG = "StrokeSortStrategy";
    /**
     * 获取排序过后的国家区域列表
     *
     * @param countryOrRegionList 待排序的国家区域列表
     * @return 排序过后的国家区域列表
     */
    @Override
    public List<CountryOrRegion> getSortedCountryOrRegionList(List<CountryOrRegion> countryOrRegionList) {
        //根据比较国家或地区的名称的笔画数量来进行排序
        Collections.sort(countryOrRegionList, new Comparator<CountryOrRegion>() {
            @Override
            public int compare(CountryOrRegion c1, CountryOrRegion c2) {
                int sum1 = c1.getStrokeCount();
                int sum2 = c2.getStrokeCount();
                return sum1 - sum2;
            }
        });
        return countryOrRegionList;
    }

    @Override
    public String getSortTitle(CountryOrRegion countryOrRegion,Context context) {
        //返回笔划排序的笔画数量
        return countryOrRegion.getStrokeCount() + context.getResources().getString(R.string.stroke_title);
    }

    @Override
    public int getSectionForPosition(List<CountryOrRegion> list, int position) {
        return list.get(position).getStrokeCount();
    }

    /**
     * 根据笔画排序笔画数目的值获取其第一次或者是最后一次出现的位置
     *
     * @param section 笔画排序笔画数目
     * @param isFirst 是否是第一次
     * @return 第一次或者是最后一次出现指定笔画数目的值的位置
     */
    @Override
    public int getFirstOrLastPositionForSection(List<CountryOrRegion> list, int section, boolean isFirst) {
        int count = list.size();
        //如果查找第一个位置，从0开始遍历
        if (isFirst) {
            for (int i = 0; i < count; i++) {
                if (isSameStrokeSum(list, section, i)) {
                    return i;
                }
            }
        } else { //如果查找的是最后一个位置，从最后一个开始遍历
            for (int i = count - 1; i > 0; i--) {
                if (isSameStrokeSum(list, section, i)) {
                    return i;
                }
            }
        }
        //如果没有找到 返回 -1
        return -1;
    }

    /**
     * 判断 第一次出现该 笔画数目的位置 是否 和指定的位置一样
     *
     * @param section  笔画数目
     * @param position 列表中的位置
     * @return 是否相同
     */
    private boolean isSameStrokeSum(List<CountryOrRegion> list, int section, int position) {
        //获取指定位置的笔画数
        int strokeCount = list.get(position).getStrokeCount();
        return strokeCount == section;
    }

    @Override
    public String[] getSideBarSortShowItemArray(List<CountryOrRegion> mSourceDateList, Context context) {
        List<String> list = new ArrayList<>();
        int count = mSourceDateList.size();
        for (int i = 0; i < count; i++) {
            CountryOrRegion countryOrRegion = mSourceDateList.get(i);
            String strokeCount = getSortTitle(countryOrRegion,context);
            if (!list.contains(strokeCount)) {
                list.add(strokeCount);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    private static Pattern NUMBER_PATTERN = Pattern.compile("[^0-9]");

    /**
     * 返回 滑动到侧边栏的某个笔画描述，首次出现在listView中的位置
     * 因为侧边栏显示的 类似于 "3划"，因此需要将"划"字取出来，然后对比  "3" 第一次出现的postion
     *
     * @param adapter             适配器
     * @param sideBarSortShowItem 滑动到侧边栏的某个笔画描述
     * @return 滑动到侧边栏的某个笔画描述首次出现在listView中的位置
     */
    @Override
    public int getSideBarSortSectionFirstShowPosition(CountryOrRegionAdapter adapter, String sideBarSortShowItem) {
        try {
            //提取字符串中的数字
            int strokeCount = Integer.parseInt(numberIntercept(sideBarSortShowItem));
            //然后查询出 这个数字首次出现的时候
            return adapter.getPositionForSection(strokeCount);
        } catch (Exception e) {
            Log.e(TAG,Log.getStackTraceString(e));
        }
        return 0;
    }

    /**
     * 提取字符串中的数字
     *
     * @param numberString 包含数字的字符串
     * @return 字符串中的数字
     */
    public String numberIntercept(String numberString) {
        return NUMBER_PATTERN.matcher(numberString).replaceAll("");
    }

    /**
     * 笔画排序的时候，不需要拼音
     *
     * @param countryOrRegion 国家地区封装类
     * @return 空字符串
     */
    @Override
    public String getPinyinOrEnglish(CountryOrRegion countryOrRegion) {
        return "";
    }

    @Override
    public String getSortLetters(String pinyin) {
        return "#";
    }

    /**
     * 获取 笔画排序所需要的汉字笔画数量 中文环境的返回汉字笔画数量，其他环境的返回 -1
     *
     * @param name    国家名
     * @param context 上下文
     * @return 笔画排序所需要的汉字笔画数量
     */
    @Override
    public int getStrokeCount(String name, Context context) {
        //取出首字母上的字符code  //返回指定索引处的字符（Unicode 代码点）。索引引用 char 值（Unicode 代码单元），其范围从 0到 length() - 1。
        int codePoint = name.codePointAt(0);
        //得到笔画的相关信息
        StrokeUtils strokeUtils = StrokeUtils.newInstance(context);
        //获取首个汉字
        Stroke stroke = strokeUtils.getStroke(codePoint + "");
        if (stroke != null) {
            return Integer.parseInt(stroke.getStrokeSum());
        }
        return -1;
    }
}
