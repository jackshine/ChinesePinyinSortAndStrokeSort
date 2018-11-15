package com.oyp.sort.utils;

import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;

import org.json.JSONObject;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JSON转换工具类
 *
 * @author lhd
 */
public class JSONUtil {

    private static String TAG = JSONUtil.class.getName();

    private static String CHARSET_UTF_8 = "utf-8";

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        //对于为null的字段不进行序列化
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //对于未知属性不进行反序列化
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //无论对象中的值只有不为null的才进行序列化
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * 把对象转化成json字符串
     *
     * @param obj 需要转换为json字符串的对象
     * @return 转换后的json字符串
     */
    public static String toJSON(Object obj) {
        if (obj == null) {
            return null;
        }
        Writer write = new StringWriter();
        try {
            mapper.writeValue(write, obj);
        } catch (Exception e) {
            Log.e(TAG, e.toString() + obj);
        }
        return write.toString();
    }

    /**
     * JSON字符串转成对象
     *
     * @param jsonStr   待解析的json字符串
     * @param classType 从json字符串解析出来的对象的类型
     * @return 解析json字符串后的对象
     */
    public static <T> T fromJSON(String jsonStr, Class<T> classType) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        T t = null;
        try {
            t = mapper.readValue(jsonStr.getBytes(CHARSET_UTF_8), classType);
        } catch (Exception e) {
            Log.e(TAG, e.toString() + ", jsonStr:" + jsonStr + ", classType:" + classType.getName());
        }
        return t;
    }

    /**
     * JSON字符串转化成集合
     *
     * @param jsonStr         待解析的json字符串
     * @param collectionClass 解析json字符串对应的集合类型
     * @param elementClasses  解析json字符串对应的元素类型
     * @return 从json字符串解析出来的集合对象
     */
    public static <T> T toCollection(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        T t = null;
        try {
            t = mapper.readValue(jsonStr.getBytes(CHARSET_UTF_8)
                    , mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses));
        } catch (Exception e) {
            Log.e(TAG, e.toString() + ", jsonStr:" + jsonStr);
        }
        return t;
    }

    public static Object get(String jsonStr, String key) {
        Object obj = null;
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            obj = jsonObj.get(key);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return obj;
    }

    /**
     * 把Jackson json转换的一些缓存清除掉，避免占用过多内存
     */
    public static void flush() {
        DefaultSerializerProvider.Impl defaultSerializerProvider = (DefaultSerializerProvider.Impl) mapper.getSerializerProvider();
        Log.i(TAG, "before flush json cache:" + defaultSerializerProvider.cachedSerializersCount());
        defaultSerializerProvider.flushCachedSerializers();
        Log.i(TAG, "after flush json cache:" + defaultSerializerProvider.cachedSerializersCount());
        try {
            Field _serializerCacheField = defaultSerializerProvider.getClass()
                    .getSuperclass().getSuperclass().getDeclaredField("_serializerCache");
            if (_serializerCacheField != null) {
                _serializerCacheField.setAccessible(true);
                SerializerCache serializerCache = (SerializerCache) _serializerCacheField.get(defaultSerializerProvider);
                if (serializerCache != null) {
                    Field _readOnlyMapField = serializerCache.getClass().getDeclaredField("_readOnlyMap");
                    if (_readOnlyMapField != null) {
                        _readOnlyMapField.setAccessible(true);
                        AtomicReference<ReadOnlyClassToSerializerMap> _readOnlyMap
                                = (AtomicReference<ReadOnlyClassToSerializerMap>) _readOnlyMapField.get(serializerCache);
                        if (_readOnlyMap != null) {
                            _readOnlyMap.set(null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        Log.d(TAG, "flush " + JSONUtil.class.getSimpleName() + " cache finished");
    }
}
