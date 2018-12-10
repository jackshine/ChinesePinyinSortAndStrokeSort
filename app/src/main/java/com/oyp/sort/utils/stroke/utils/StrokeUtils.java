package com.oyp.sort.utils.stroke.utils;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import android.util.Log;

import com.oyp.sort.utils.JSONUtil;
import com.oyp.sort.utils.LocalFileUtils;
import com.oyp.sort.utils.stroke.bean.Stroke;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;


/**
 * 汉字笔划工具类
 */
public class StrokeUtils {
    private static final String TAG = "StrokeUtils";

    private final static StrokeUtils factory = new StrokeUtils();

    private static HashMap<String, Stroke> mapper;

    public static StrokeUtils newInstance(Context context) {
        if (mapper == null) {
            String strokeJson = LocalFileUtils.getStringFormAsset(context, "stroke.json");
            mapper = JSONUtil.toCollection(strokeJson, HashMap.class, String.class, Stroke.class);
        }
        return factory;
    }

    /**
     * 根据point拿到笔画封装的Stroke
     *
     * @param keyPoint code码
     * @return 笔画封装的Stroke
     */
    public Stroke getStroke(String keyPoint) {
        if (keyPoint == null) {
            return null;
        }
        return mapper.get(keyPoint);
    }

}

