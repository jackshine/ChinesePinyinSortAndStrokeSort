package com.oyp.sort.utils.stroke.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stroke {
    /**
     * 序号
     */
    @JsonProperty("o")
    private String order;
    /**
     * 汉字
     */
    @JsonProperty("n")
    private String name;
    /**
     * 编码
     */
    @JsonProperty("c")
    private String code;
    /**
     * 笔画数量
     */
    @JsonProperty("s")
    private String strokeSum;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStrokeSum() {
        return strokeSum;
    }

    public void setStrokeSum(String strokeSum) {
        this.strokeSum = strokeSum;
    }

    @Override
    public String toString() {
        return "Stroke{" +
                "order='" + order + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", strokeSum='" + strokeSum + '\'' +
                '}';
    }
}
