package com.haiyishuzi.haiyishuzijcm.db;

import android.os.Bundle;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * dataTypeConverters
 */

public class HaiyishuziTypeConverters {

    private static class Singleton {
        public static final ObjectMapper INSTANCE = new ObjectMapper();
    }

    @TypeConverter
    public static Date longToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Timestamp longToTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long timestampToLong(Timestamp date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static List<String> stringToStringList(String data) {
        try {
            return Singleton.INSTANCE.readValue(data, new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String stringListToString(List<String> stringList) {
        try {
            return Singleton.INSTANCE.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public String[] jsonToStringArray(String json) {
        try {
            return Singleton.INSTANCE.readValue(json, String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public String stringArrayToString(String[] array) {
        try {
            return Singleton.INSTANCE.writeValueAsString(array);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static List<Map<String, String>> stringToStringListMap(String json) {
        try {
            return Singleton.INSTANCE.readValue(json, new TypeReference<List<Map<String, String>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String stringListMapToString(List<Map<String, String>> mapList) {
        try {
            return Singleton.INSTANCE.writeValueAsString(mapList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String bundleToString(Bundle bundle){
        if (bundle == null)
            return null;
        //不能直接用fastjson转化需要遍历bundle转化成map存储
        Map<String,Object> map = new HashMap<>();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet){
            map.put(key,bundle.get(key));
        }
        try {
            return Singleton.INSTANCE.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static Bundle stringToBundle(String json) {
        //不能直接用fastjson转化需要遍历bundle转化成map存储
        try {
            Map<String,Object> map = Singleton.INSTANCE.readValue(json, new TypeReference<Map<String,Object>>() {
            });
            if (null == map)
                return null;
            else{
                //遍历map组转Bundle
                Bundle bundle = new Bundle();
                for (String key :map.keySet()){
                    putValue(bundle,key,map.get(key));
                }
                return bundle;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void putValue(Bundle bundle,String key,Object value){
        if (value instanceof String){
            bundle.putString(key, (String) value);
        }else if(value instanceof Integer){
            bundle.putInt(key, (int) value);
        }else if(value instanceof Float){
            bundle.putFloat(key, (Float) value);
        }else if(value instanceof Boolean){
            bundle.putBoolean(key, (Boolean) value);
        }else{
            //不愿写了，需要什么的时候再自己添加
        }
    }

//
//    @TypeConverter
//    public static TokenInfo stringToTokenInfo(String data) {
//        try {
//            return Singleton.INSTANCE.readValue(data, new TypeReference<TokenInfo>() {
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @TypeConverter
//    public static String tokenInfoToString(TokenInfo tokenInfo) {
//        try {
//            return Singleton.INSTANCE.writeValueAsString(tokenInfo);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
