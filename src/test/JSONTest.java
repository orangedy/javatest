/*
 * @(#) JSONTest.java 2015年6月3日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;

/**
 *
 * @author hzdingyong
 * @version 2015年6月3日
 */
public class JSONTest {

    @Test
    public void testJsonToJava() {
        String json = "[{\"a\":1, \"b\":\"test\", \"c\":[null]}]";
        JSONArray jsonObject = JSONArray.fromObject(json);
        System.out.println(JSONArray.toList(jsonObject, Bean.class));
    }

    @Test
    public void testJsonType() {
        String json = "{\"a\":\"1\", \"b\":1}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        int a = jsonObject.getInt("a");
        System.out.println(a);
        int b = jsonObject.getInt("b");
        String bStr = jsonObject.getString("b");
    }

    @Test
    public void testJavaToJson() {
        JSONObject json = new JSONObject();
        json.put("a", "string");
        json.put("b", "\"{\"key\":\"value\"}\"");
        String jsonStr = json.toString();
        JSONObject json2 = JSONObject.fromObject(jsonStr);
        System.out.println(json2.get("b"));
    }

    @Test
    public void testLF() {
        String json = "{\"a\\nb\":\"b\\ndd\"}";
        System.out.println(JSONObject.fromObject(json));
        Gson gson = new Gson();
        Map result = gson.fromJson(json, Map.class);
        System.out.println(result);
    }

    @Test
    public void testComments() {
        String str = "/*fadasd*";
        JSONObject json = JSONObject.fromObject(str);
        System.out.println(json);
    }

    @Test
    public void testFastJson() {
        Map<String, Object> obj = new LinkedHashMap<String, Object>(); // 序列化时，LinkedHashMap会保证顺序
        obj.put("key1", "value1");
        Map<String, String> subMap = new HashMap<String, String>(); // 序列化时，HashMap会自动按字母顺序排序
        subMap.put("b", "b");
        subMap.put("a", "a");
        subMap.put("c", "c");
        subMap.put("d", "d");
        subMap.put("e", "e");
        subMap.put("A", "A");
        obj.put("key2", subMap);
        String str = JSON.toJSONString(obj);
        System.out.println(str);

        // 貌似不起作用
        int parseFeature = Feature.config(JSON.DEFAULT_PARSER_FEATURE, Feature.SortFeidFastMatch, false);
        int serialsFeature = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.SortField, false);
        // JSON.DEFAULT_GENERATE_FEATURE = serialsFeature;
        String str2 = "{\"key2\":{\"b\":\"b\", \"a\":\"a\"}, \"key1\":\"value1\"}";
        LinkedHashMap<String, Object> map = JSON.parseObject(str2, new TypeReference<LinkedHashMap<String, Object>>() {
        }.getType(), parseFeature, new Feature[0]);
        System.out.println(map);
        /*System.out.println(map.get("key2"));
        System.out.println(map.get("key1"));*/
    }
}
