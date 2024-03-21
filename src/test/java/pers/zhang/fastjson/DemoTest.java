package pers.zhang.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.*;
import org.junit.jupiter.api.Test;

/**
 * @Author: acton_zhang
 * @Date: 2024/2/3 5:06 下午
 * @Version 1.0
 */
public class DemoTest {


    @Test
    public void jsonToJsonObject() {
        String jsonStr = "{\"id\": 1, \"name\": \"tom\", \"age\": 18}";
        byte[] jsonBytes = jsonStr.getBytes();

        //将JSON解析为JSONObject，支持各种形式的JSON
        JSONObject jsonObject1 = JSON.parseObject(jsonStr);
        JSONObject jsonObject2 = JSON.parseObject(jsonBytes);
        System.out.println(jsonObject1.get("id"));//1
        System.out.println(jsonObject2.get("name"));//tom
    }

    @Test
    public void jsonToJsonArray() {
        //language=JSON
        String jsonStr = "[\"one\", \"two\", \"three\"]";
        byte[] jsonBytes = jsonStr.getBytes();

        //将JSON解析为JSONArray，支持各种形式的JSON
        JSONArray jsonArray1 = JSON.parseArray(jsonStr);
        JSONArray jsonArray2 = JSON.parseArray(jsonBytes);
        System.out.println(jsonArray1.get(1));//two
        System.out.println(jsonArray2.get(2));//three
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Book {
        private Long id;
        private String name;
        private String author;
        private Double price;
    }

    @Test
    public void jsonToJavaBean() {
        String jsonStr = "{\"id\": 1, \"name\": \"三国演义\", \"author\": \"罗贯中\", \"price\": 24.6}";

        //将JSON解析为Java Bean
        Book book = JSON.parseObject(jsonStr, Book.class);
        //DemoTest.Book(id=1, name=三国演义, author=罗贯中, price=24.6)
        System.out.println(book);
    }

    @Test
    public void javaBeanToJson() {
        Book book = new Book(10l, "水浒传", "施耐庵", 33.33);

        //将JavaBean转换为Json
        String jsonStr = JSON.toJSONString(book);
        byte[] jsonBytes = JSON.toJSONBytes(book);
        //{"author":"施耐庵","id":10,"name":"水浒传","price":33.33}
        System.out.println(jsonStr);
        //{"author":"施耐庵","id":10,"name":"水浒传","price":33.33}
        System.out.println(new String(jsonBytes));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    class Address {
        private String city;
        private String district;
    }



    @Test
    public void jsonObjectTest() {
        String jsonStr = "{\n" +
                "  \"name\": \"tom\",\n" +
                "  \"age\": 18,\n" +
                "  \"address\": {\n" +
                "    \"city\": \"BeiJing\",\n" +
                "    \"district\": \"ChaoYang\"\n" +
                "  },\n" +
                "  \"scores\": [\n" +
                "    88.8,\n" +
                "    77.7,\n" +
                "    99.9\n" +
                "  ]\n" +
                "}\n" +
                "\n";
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        //读取简单属性
        String name = jsonObject.getString("name");
        Integer age = jsonObject.getInteger("age");
        System.out.println(age);//18
        System.out.println(name);//tom

        //读取数组
        JSONArray jsonArray = jsonObject.getJSONArray("scores");
        System.out.println(jsonArray);//[88.8, 77.7, 99.9]

        //读取数据元素
        Double score = jsonArray.getDouble(0);
        System.out.println(score);//88.8

        //读取JavaBean
        Address address = jsonObject.getObject("address", Address.class);

        System.out.println(address);//
    }

    @Test
    public void jsonObjectToJavaBean() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 10L);
        jsonObject.put("name", "水浒传");
        jsonObject.put("author", "施耐庵");
        jsonObject.put("price", 33.33);

        Book book = JSON.to(Book.class, jsonObject);
        Book to = jsonObject.to(Book.class);
        System.out.println(book);
        System.out.println(to);

    }
}
