package com.example.sqsspring.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;




public class MapperUtil {



    public static String toPrettyJSONObjectStr (Object object) throws JsonProcessingException {
        if(object==null || object instanceof String) {
            return (String) object;
        }
        String result=null;
        ObjectMapper mapper = new ObjectMapper();
        result =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        return result;
    }


    public static JSONObject toJSONObject(Object object) throws ParseException, JsonProcessingException {
        if(object instanceof String){
            return parseToJSONObject((String) object);
        }
        String objectStr = serializeToString(object);
        return parseToJSONObject(objectStr);
    }


    private static JSONObject parseToJSONObject(String str) throws ParseException {
        JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
        JSONObject json = null;
        json = (JSONObject)parser.parse(str);
        return json;
    }


    public  static <T> T parseToObject(String str, Class <T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        T responseObject = null;
        responseObject = (T) objectMapper.readValue(str, clazz);
        return responseObject;
    }


    public  static String serializeToString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseObject = null;
        if(object instanceof String){
            return (String)object;
        }
        responseObject = objectMapper.writeValueAsString(object);
        return responseObject;
    }



}
