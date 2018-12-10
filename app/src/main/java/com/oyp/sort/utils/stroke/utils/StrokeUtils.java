package com.oyp.sort.utils.stroke.utils;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import android.util.Log;

import com.oyp.sort.utils.JSONUtil;
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

    public static HashMap<String, Stroke> getMapper() {
        return mapper;
    }

    private static HashMap<String, Stroke> mapper;

    public static StrokeUtils newInstance(Context context) {
        if (mapper == null) {
            mapper = new HashMap<String, Stroke>();
            //读取数据库
            StrokeDataBaseHelper myDbHelper = new StrokeDataBaseHelper(context);
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                myDbHelper.createDataBase();
                myDbHelper.openDataBase();
                db = myDbHelper.getWritableDatabase();
                //查出所有的数据
                cursor = db.query(StrokeDataBaseHelper.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(StrokeDataBaseHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(StrokeDataBaseHelper.COLUMN_CHINESE));
                    String strokeSum = cursor.getString(cursor.getColumnIndex(StrokeDataBaseHelper.COLUMN_SUM));
                    String codePointAt = cursor.getString(cursor.getColumnIndex(StrokeDataBaseHelper.COLUMN_CODEPOINTAT));
                    Log.d(TAG, "id：" + id + " " + "CHINESE：" + name + " " + "SUM：" + strokeSum + " codePointAt：" + codePointAt);

                    Stroke stroke = new Stroke();
                    stroke.setCode(codePointAt);
                    stroke.setName(name);
                    stroke.setOrder(id);
                    stroke.setStrokeSum(strokeSum);
                    mapper.put(codePointAt, stroke);
                }
            } catch (Exception e) {
                Log.e(TAG, "ios = " + Log.getStackTraceString(e));
            } finally {
                //关闭cursor
                if (cursor != null) {
                    cursor.close();
                }
                //关闭数据库
                if (db != null) {
                    db.close();
                }
            }
        }

        try {
            File file = new File(Environment.getExternalStorageDirectory(), "stroke.json");
            Log.d(TAG, "file.exists():" + file.exists() + " file.getAbsolutePath():" + file.getAbsolutePath());
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            //mapper 转 Json
            String mapperJson = JSONUtil.toJSON(mapper);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(mapperJson);
            write.flush();
            write.close();
        } catch (Exception e) {
            Log.e(TAG, "ios = " + Log.getStackTraceString(e));
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

