package com.oyp.sort.utils.stroke.utils;


import android.content.Context;

import android.os.Environment;

import android.util.Log;

import com.oyp.sort.utils.DeflaterUtils;
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
//            //原始文件   stroke.json
//            String strokeJson = LocalFileUtils.getStringFormAsset(context, "stroke.json");
//            mapper = JSONUtil.toCollection(strokeJson, HashMap.class, String.class, Stroke.class);
//            // 使用 Deflater  加密
//            String deFlaterStrokeJson = DeflaterUtils.zipString(strokeJson);
//            writeFile(deFlaterStrokeJson,"deFlaterStrokeJson.json");

            //使用 Inflater 解密
            String deFlaterStrokeJson = LocalFileUtils.getStringFormAsset(context, "deFlaterStrokeJson.json");
            String strokeJson = DeflaterUtils.unzipString(deFlaterStrokeJson);
            mapper = JSONUtil.toCollection(strokeJson, HashMap.class, String.class, Stroke.class);
        }
        return factory;
    }

    private static void writeFile(String mapperJson, String fileName) {
        Writer write = null;
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
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(mapperJson);
            write.flush();
            write.close();
        } catch (Exception e) {
            Log.e(TAG, "e = " + Log.getStackTraceString(e));
        }finally {
            if (write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    Log.e(TAG, "e = " + Log.getStackTraceString(e));
                }
            }
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
        if (mapper != null){
            return mapper.get(keyPoint);
        } else{
            return null;
        }
    }
}

