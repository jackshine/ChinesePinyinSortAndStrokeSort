package com.oyp.sort.strategy;

import android.content.Context;

import com.oyp.sort.adapter.CountryOrRegionAdapter;
import com.oyp.sort.bean.CountryOrRegion;

import java.util.List;

/**
 * 排序的策略
 */
public interface ISortStrategy {
    /**
     * 获取排序过后的国家区域列表
     *
     * @param countryOrRegionList 待排序的国家区域列表
     * @return 排序过后的国家区域列表
     */
    List<CountryOrRegion> getSortedCountryOrRegionList(List<CountryOrRegion> countryOrRegionList);

    /**
     * 获取要展示的排序的title   拼音排序显示 首字母，笔画排序显示 几划
     *
     * @param countryOrRegion 封装的CountryOrRegion
     * @return 要展示的排序的title
     */
    String getSortTitle(CountryOrRegion countryOrRegion,Context context);

    /**
     * 根据ListView的当前位置获取排序标题 是否需要显示的对比值：
     * - 拼音排序返回首字母的Char ascii值
     * - 笔画排序返回首字母的汉字笔画数量
     *
     * @param list     数据List集合
     * @param position 位置
     * @return 是否需要显示的对比值
     * - 拼音排序返回首字母的Char ascii值
     * - 笔画排序返回首字母的汉字笔画数量
     */
    int getSectionForPosition(List<CountryOrRegion> list, int position);

    /**
     * 获取第一次或者是最后一次 出现该排序标题的位置
     *
     * @param list    排序列表
     * @param section 排序标题的值：拼音排序首字母的Char ascii值，笔画排序出现的笔画数
     * @param isFirst 是否是第一次
     * @return 第一次或者是最后一次 出现该排序标题的位置
     */
    int getFirstOrLastPositionForSection(List<CountryOrRegion> list, int section, boolean isFirst);

    /**
     * 生成不同排序规则，侧边栏需要展示的列表
     *
     * @param mSourceDateList 数据源
     * @return SideBar需要展示的列表
     */
    String[] getSideBarSortShowItemArray(List<CountryOrRegion> mSourceDateList, Context context);

    /**
     * 返回 滑动到侧边栏的某一项时候，需要去拿这一项首次出现在listView中的位置
     *
     * @param adapter             适配器
     * @param sideBarSortShowItem 滑动到侧边栏的某一项
     * @return 这一项首次出现在listView中的位置
     */
    int getSideBarSortSectionFirstShowPosition(CountryOrRegionAdapter adapter, String sideBarSortShowItem);

    /**
     * 获取拼音排序需要的拼音 或者 英文排序需要的英文，中文环境的返回拼音字母，其他环境的返回  国家码转换后的国家英文名
     *
     * @param countryOrRegion 国家地区封装类
     * @return 拼音排序需要的拼音 或者 英文排序需要的英文
     */
    String getPinyinOrEnglish(CountryOrRegion countryOrRegion);

    /**
     * 获取 拼音排序或者英文排序需要展示的 英文首字母
     *
     * @param pinyin 拼音排序需要的拼音 或者 英文排序需要的英文
     * @return 需要展示的 英文首字母
     */
    String getSortLetters(String pinyin);
    /**
     * 获取 笔画排序所需要的汉字笔画数量 中文环境的返回汉字笔画数量，其他环境的返回 -1
     *
     * @param name    国家名
     * @param context 上下文
     * @return 笔画排序所需要的汉字笔画数量
     */
    int getStrokeCount(String name, Context context);
}
