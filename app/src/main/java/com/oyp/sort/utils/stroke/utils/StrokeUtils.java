package com.oyp.sort.utils.stroke.utils;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import android.util.Log;

import com.oyp.sort.utils.GZIP;
import com.oyp.sort.utils.JSONUtil;
import com.oyp.sort.utils.LocalFileUtils;
import com.oyp.sort.utils.stroke.bean.Stroke;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static StrokeUtils newInstance(Context context)  {
        if (mapper == null) {
            String strokeJson = LocalFileUtils.getStringFormAsset(context, "stroke.json");
            try {
                String gzipStrokeJson = GZIP.compress(strokeJson);
                writeFile(gzipStrokeJson, "gzipStroke.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapper = JSONUtil.toCollection(strokeJson, HashMap.class, String.class, Stroke.class);
        }
        return factory;
    }

    private static void writeFile(String mapperJson, String fileName) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            Log.d(TAG, "file.exists():" + file.exists() + " file.getAbsolutePath():" + file.getAbsolutePath());
            // 如果父目录不存在，创建父目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 如果已存在,删除旧文件
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(mapperJson);
            write.flush();
            write.close();
        } catch (Exception e) {
            Log.e(TAG, "ios = " + Log.getStackTraceString(e));
        }
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

