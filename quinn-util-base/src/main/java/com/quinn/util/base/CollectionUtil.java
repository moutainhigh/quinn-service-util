package com.quinn.util.base;

import com.alibaba.fastjson.JSONArray;
import com.quinn.util.base.api.MethodInvokerNoParam;
import com.quinn.util.base.convertor.BaseConverter;
import com.quinn.util.constant.StringConstant;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author Qunhua.Liao
 * @since 2020-03-30
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 判断元素是否在数组内
     *
     * @param element 元素
     * @param array   数组
     * @return 在里面则为true, 否则为false
     */
    public static final boolean elementInArray(Object element, Object[] array) {
        if (element == null || array == null) {
            return false;
        }
        for (Object obj : array) {
            if (element.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组是否为空
     *
     * @param args 判断数组
     * @return 是否为空
     */
    public static final boolean isEmpty(Object[] args) {
        return args == null || args.length == 0;
    }

    /**
     * 判断列表是否为空
     *
     * @param collection 判断列表
     * @return 是否为空
     */
    public static final boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 判断Map是否为空
     *
     * @param params Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map params) {
        return params == null || params.size() == 0;
    }

    /**
     * 将数组转化为Set
     *
     * @param ts  数组
     * @param <T> 数组泛型
     * @return Set
     */
    public static final <T> HashSet<T> asHashSet(T[] ts) {
        if (isEmpty(ts)) {
            return null;
        }

        HashSet hashSet = new HashSet();
        for (T t : ts) {
            hashSet.add(t);
        }
        return hashSet;
    }

    /**
     * 整合两个Map
     *
     * @param oldMap 旧的Map
     * @param addMap 新增Map
     */
    public static <K, V> void mergeMap(Map<K, V> oldMap, Map<K, V> addMap) {
        if (addMap == null) {
            return;
        }

        for (Map.Entry<K, V> entry : addMap.entrySet()) {
            if (!oldMap.containsKey(entry.getKey())) {
                oldMap.put(entry.getKey(), entry.getValue());
            } else {
                Object oldVal = oldMap.get(entry.getKey());
                if (oldVal instanceof Map && entry.getValue() instanceof Map) {
                    mergeMap((Map) oldVal, (Map) entry.getValue());
                } else {
                    oldMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * 默认分割符拼接列表
     *
     * @param data 数据
     * @return 字符串
     */
    public static String join(Collection data) {
        return join(data, StringConstant.CHAR_COMMA);
    }

    /**
     * 默认分割符拼接数组
     *
     * @param data 数据
     * @return 字符串
     */
    public static String join(Object[] data) {
        return join(data, StringConstant.CHAR_COMMA);
    }

    /**
     * 自定分隔符拼接列表
     *
     * @param data  数据
     * @param split 分割符
     * @return
     */
    public static String join(Collection data, String split) {
        if (isEmpty(data)) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        for (Object o : data) {
            query.append(BaseConverter.staticToString(o)).append(split);
        }
        query.delete(query.length() - split.length(), query.length());
        return query.toString();
    }

    /**
     * 自定分隔符拼接数组
     *
     * @param data  数据
     * @param split 分割符
     * @return
     */
    public static String join(Object[] data, String split) {
        if (isEmpty(data)) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        for (Object o : data) {
            query.append(BaseConverter.staticToString(o)).append(split);
        }
        query.delete(query.length() - split.length(), query.length());
        return query.toString();
    }

    /**
     * Map空值安全添加元素
     *
     * @param map   容器
     * @param key   键
     * @param value 空值生成值的回调函数
     * @param <T>   值泛型
     * @return 值（原值或者新添加的）
     */
    public static <T> T nullSafeGut(Map<String, T> map, String key, MethodInvokerNoParam<T> value) {
        T t = map.get(key);
        if (t == null) {
            t = value.invoke();
            map.put(key, t);
        }
        return t;
    }

    /**
     * 根据下标取值
     *
     * @param object 容器
     * @param index  下标
     * @return 值
     */
    public static Object get(Object object, int index) {
        if (object == null || index < 0) {
            return null;
        }

        if (object instanceof JSONArray) {
            JSONArray array = ((JSONArray) object);
            if (index >= array.size()) {
                return null;
            }
            return array.get(index);
        }

        if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            if (index >= array.length) {
                return null;
            }
            return array[index];
        }

        if (List.class.isAssignableFrom(object.getClass())) {
            List list = (List) object;
            if (index >= list.size()) {
                return null;
            }
            return list.get(index);
        }

        return null;
    }

    /**
     * 平铺添加
     *
     * @param collection 已有集合
     * @param object     新增数据
     */
    public static void plainAdd(Collection collection, Object object) {
        if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            for (Object o : array) {
                collection.add(o);
            }
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            collection.addAll((Collection) object);
        } else {
            collection.add(object);
        }
    }

}
