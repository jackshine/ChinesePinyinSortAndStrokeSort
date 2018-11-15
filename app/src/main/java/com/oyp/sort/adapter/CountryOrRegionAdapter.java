package com.oyp.sort.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.oyp.sort.R;
import com.oyp.sort.bean.CountryOrRegion;
import com.oyp.sort.strategy.ISortStrategy;
import com.oyp.sort.utils.AreaCodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示国家列表的Adapter
 */
public class CountryOrRegionAdapter extends BaseAdapter implements SectionIndexer {
    private List<CountryOrRegion> list = new ArrayList<>();
    private Context mContext;
    private ISortStrategy sortStrategy;

    public CountryOrRegionAdapter(Context mContext, List<CountryOrRegion> list, ISortStrategy sortStrategy) {
        this.mContext = mContext;
        this.list = list;
        this.sortStrategy = sortStrategy;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<CountryOrRegion> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final CountryOrRegion mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sort_listview, null);
            viewHolder.tvSortTitle = view.findViewById(R.id.sortTitle);
            viewHolder.tvCountryRegionName = view.findViewById(R.id.country_region_name);
            viewHolder.tvAreaCode = view.findViewById(R.id.areaCode);
            viewHolder.splitLine = view.findViewById(R.id.area_split_line);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        // 判断是否需要显示 排序标题 : 首次出现的则显示，其他时候出现的不显示
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvSortTitle.setVisibility(View.VISIBLE);
            //标题  拼音排序显示 首字母，笔画排序显示 几划
            viewHolder.tvSortTitle.setText(sortStrategy.getSortTitle(mContent,mContext));
        } else {
            viewHolder.tvSortTitle.setVisibility(View.GONE);
        }

        //判断是否需要显示 分割线  : 最后一次出现该首字母的位置,隐藏分割线 (首次和最后一次有可能相同)
        if (position == getLastPositionForSection(section)) {
            viewHolder.splitLine.setVisibility(View.GONE);
        } else {
            viewHolder.splitLine.setVisibility(View.VISIBLE);
        }

        viewHolder.tvCountryRegionName.setText(
                AreaCodeUtils.getCountryNameByCountryCode(mContext, mContent.getCountryCode()));
        String areaCode = AreaCodeUtils.AREA_CODE_PLUS + mContent.getAreaCode();
        viewHolder.tvAreaCode.setText(areaCode);
        return view;
    }

    /**
     * 根据ListView的当前位置获取排序标题 是否需要显示的对比值：
     * - 拼音排序返回首字母的Char ascii值
     * - 笔画排序返回首字母的汉字笔画数量
     */
    @Override
    public int getSectionForPosition(int position) {
        return sortStrategy.getSectionForPosition(list, position);
    }

    /**
     * 第一次出现该该排序标题的位置
     */
    @Override
    public int getPositionForSection(int section) {
        return sortStrategy.getFirstOrLastPositionForSection(list, section, true);
    }

    /**
     * 最后一次出现该该排序标题的位置的位置
     */
    public int getLastPositionForSection(int section) {
        return sortStrategy.getFirstOrLastPositionForSection(list, section, false);
    }

    final static class ViewHolder {
        TextView tvSortTitle;
        TextView tvCountryRegionName;
        TextView tvAreaCode;
        View splitLine;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}