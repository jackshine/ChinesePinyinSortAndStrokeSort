package com.oyp.sort.bean;


/**
 * 国家或地区
 * <p/>
 * created by OuyangPeng on 2016/11/27.
 */
public class CountryOrRegion {

    private Integer id;

    /**
     * 国家或地区 代号码，比如 CN、TW、HK、US
     */
    private String countryCode;

    /**
     * 国家或地区 区号
     */
    private Integer areaCode; //区号

    // 拼音排序后需要的两个字段
    /**
     * 排序首字母
     */
    private String sortLetters;
    /**
     * 转换后的拼音名称
     */
    private String pinyinName;

    // 笔画排序后需要的两个字段

    /**
     * 国家或地区的名称，根据countryCode从资源文件获取到的不同的名称，可能是英文、简体中文、繁体中文、印尼文等
     */
    private String name;

    /**
     * 国家或地区的名称的笔画数量
     */
    private int strokeCount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }

    @Override
    public String toString() {
        return "CountryOrRegion{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", areaCode=" + areaCode +
                ", sortLetters='" + sortLetters + '\'' +
                ", pinyinName='" + pinyinName + '\'' +
                ", name='" + name + '\'' +
                ", strokeCount='" + strokeCount + '\'' +
                '}';
    }
}
