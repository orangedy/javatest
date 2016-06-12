/*
 * @(#) JSONTest.java 2015年6月3日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
        json.put("a", "\"string\"");
        json.put("b", "\"{\"key\":\"value\"}\"");
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        JSONObject json2 = JSONObject.fromObject(jsonStr);
        System.out.println(json2.get("b"));
    }

    @Test
    public void testMapToJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("string", "{\"test\", \"value\"}");
        String jsonStr = JSONObject.fromObject(map).toString();
        System.out.println(jsonStr);
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

    /**
     * 注意这里的差异，gson在创建JsonObject时，会根据类型自动进行转换，比如put的value是string类型，则在外层包裹双引号
     * 而net.sf.json由于里层是Map，不保存类型信息，所有是根据value的值进行推断json类型，应该是string类型会做特殊处理
     * 比如如果是一串字符串，以{}包裹，并符合json object结构，那就推断为是object，而不会是string
     */
    @Test
    public void testGson() {
        String jsonStr = "";
        JsonObject jObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        System.out.println(jObject.toString());
        JsonObject jsonObject = new JsonObject();
        String value = "\"{\"a\":\"b\"}\"";
        jsonObject.addProperty("string", value);
        System.out.println(jsonObject.toString());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.element("string", value);
        System.out.println(jsonObject2.toString());
        System.out.println(jsonObject2.getString("string"));
    }

    @Test
    public void testJSONObject() {
        String json = "{\"key\":1}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        jsonObject.put("key1", "2");
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.getInt("key"));
    }
}
