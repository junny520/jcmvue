package com.haiyishuzi.haiyishuzijcm.util;

import java.util.HashMap;
import java.util.Map;


public class SearchUtil<T> {

    private Map<String,T> searchMap;
    private static class Singleton {
        private static final SearchUtil INSTANCE = new SearchUtil();

    }

    private SearchUtil() {
        searchMap = new HashMap<>();
    }

    public static<T> SearchUtil<T> instance() {
        return Singleton.INSTANCE;
    }


    public void putSearch(String key, T value){
        searchMap.put(key,value);
    }

    public T getSearch(String key){
        return searchMap.get(key);
    }

    public void removeSearch(String key){
        searchMap.remove(key);
    }

}
