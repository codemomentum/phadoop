package org.codemomentum.phadoop.core.utils;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Halit
 */
public class MRRegistry {

    private static Map<String, Class<? extends Mapper>> map = new HashMap<String, Class<? extends Mapper>>();

    public static void registerMapper(String key, Class<? extends Mapper> mapper) {
        map.put(key, mapper);
    }

    public static Class<? extends Mapper> getRegisteredMapper(String key) {
        return map.get(key);
    }

    private static Map<String, Class<? extends Reducer>> reduce = new HashMap<String, Class<? extends Reducer>>();

    public static void registerReducer(String key, Class<? extends Reducer> reducer) {
        reduce.put(key, reducer);
    }

    public static Class<? extends Reducer> getRegisteredReducer(String key) {
        return reduce.get(key);
    }

    public static void flushRegistry() {
        map.clear();
        reduce.clear();
    }
}
