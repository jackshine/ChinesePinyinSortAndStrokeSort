package com.oyp.sort.strategy;

import android.content.Context;

import com.oyp.sort.adapter.CountryOrRegionAdapter;
import com.oyp.sort.bean.CountryOrRegion;
import com.oyp.sort.utils.pinyin.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 根据拼音来排序的策略
 */
public class PinyinSortStrategy implements ISortStrategy {
    /**
     * 获取排序过后的国家区域列表
     *
     * @param countryOrRegionList 待排序的国家区域列表
     * @return 排序过后的国家区域列表
     */
    @Override
    public List<CountryOrRegion> getSortedCountryOrRegionList(List<CountryOrRegion> countryOrRegionList) {
        // 根据a-z进行排序源数据
        Collections.sort(countryOrRegionList, new Comparator<CountryOrRegion>() {
            @Override
            public int compare(CountryOrRegion o1, CountryOrRegion o2) {
                if (o1.getSortLetters().equals("@")
                        || o2.getSortLetters().equals("#")) {
                    return -1;
                } else if (o1.getSortLetters().equals("#")
                        || o2.getSortLetters().equals("@")) {
                    return 1;
                } else {
//			        return o1.getSortLetters().compareTo(o2.getSortLetters());
                    return o1.getPinyinName().compareTo(o2.getPinyinName());
                }
            }
        });
        return countryOrRegionList;
    }


    @Override
    public String getSortTitle(CountryOrRegion countryOrRegion,Context context) {
        //拼音的 只需要展示 英文首字母即可
        return countryOrRegion.getSortLetters();
    }

    @Override
    public int getSectionForPosition(List<CountryOrRegion> list, int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据拼音排序标题的首字母的Char ascii值获取其 第一次或者是最后一次 出现该首字母的位置
     *
     * @param section 分类的首字母的Char ascii值
     * @param isFirst 是否是第一次
     * @return 出现该首字母的位置
     */
    @Override
    public int getFirstOrLastPositionForSection(List<CountryOrRegion> list, int section, boolean isFirst) {
        int count = list.size();
        //如果查找第一个位置，从0开始遍历
        if (isFirst) {
            for (int i = 0; i < count; i++) {
                if (isSameASCII(list, section, i)) {
                    return i;
                }
            }
        } else { //如果查找的是最后一个位置，从最后一个开始遍历
            for (int i = count - 1; i > 0; i--) {
                if (isSameASCII(list, section, i)) {
                    return i;
                }
            }
        }
        //如果没有找到 返回 -1
        return -1;
    }

    /**
     * 判断 分类的首字母的Char ascii值 是否和 列表中的位置的Char ascii值一样
     *
     * @param section  分类的首字母的Char ascii值
     * @param position 列表中的位置
     * @return 是否相同
     */
    private boolean isSameASCII(List<CountryOrRegion> list, int section, int position) {
        String sortStr = list.get(position).getSortLetters();
        char firstChar = sortStr.toUpperCase().charAt(0);
        return firstChar == section;
    }

    @Override
    public String[] getSideBarSortShowItemArray(List<CountryOrRegion> mSourceDateList,Context context) {
        List<String> list = new ArrayList<>();
        int count = mSourceDateList.size();
        for (int i = 0; i < count; i++) {
            CountryOrRegion countryOrRegion = mSourceDateList.get(i);
            String sortLetters = countryOrRegion.getSortLetters();
            if (!list.contains(sortLetters)) {
                list.add(sortLetters);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 返回 滑动到侧边栏的某个字母，首次出现在listView中的位置
     *
     * @param adapter             适配器
     * @param sideBarSortShowItem 滑动到侧边栏的某个字母
     * @return 滑动到侧边栏的某个字母首次出现在listView中的位置
     */
    @Override
    public int getSideBarSortSectionFirstShowPosition(CountryOrRegionAdapter adapter, String sideBarSortShowItem) {
        return adapter.getPositionForSection(sideBarSortShowItem.charAt(0));
    }

    /**
     * 拼音排序的时候，获取汉字的拼音
     *
     * @param countryOrRegion 国家地区封装类
     * @return 汉字的拼音
     */
    @Override
    public String getPinyinOrEnglish(CountryOrRegion countryOrRegion) {
        return CharacterParser.getInstance().getSplitSelling(countryOrRegion.getName());
    }

    /**
     * 获取 拼音排序需要展示的 英文首字母
     *
     * @param pinyin 拼音排序需要的拼音
     * @return 需要展示的 英文首字母
     */
    @Override
    public String getSortLetters(String pinyin) {
        String sortString = pinyin.substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            return sortString.toUpperCase();
        } else {
            return "#";
        }
    }

    /**
     * 拼音排序不需要这个字段做比较，直接返回-1
     */
    @Override
    public int getStrokeCount(String name, Context context) {
        return -1;
    }
}
