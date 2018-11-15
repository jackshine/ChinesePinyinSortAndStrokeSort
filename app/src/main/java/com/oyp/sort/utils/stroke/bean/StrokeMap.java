package com.oyp.sort.utils.stroke.bean;

import java.util.HashMap;

public class StrokeMap extends HashMap<String, Stroke> {
    /**
     * 根据编码来存放对应的汉字笔画封装bean
     *
     * @param stroke 汉字笔画封装bean
     */
    public void put(Stroke stroke) {
        if (stroke != null) {
            this.put(stroke.getCode(), stroke);
        }
    }
}
