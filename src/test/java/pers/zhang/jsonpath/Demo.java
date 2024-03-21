package pers.zhang.jsonpath;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JsonProvider;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/26 11:29 下午
 * @Version 1.0
 */
public class Demo {

    private String jsonStr = "{\n" +
            "  \"store\": {\n" +
            "    \"book\": [\n" +
            "      {\n" +
            "        \"category\": \"reference\",\n" +
            "        \"author\": \"Nigel Rees\",\n" +
            "        \"title\": \"Sayings of the Century\",\n" +
            "        \"price\": 8.95\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\": \"fiction\",\n" +
            "        \"author\": \"Evelyn Waugh\",\n" +
            "        \"title\": \"Sword of Honour\",\n" +
            "        \"price\": 12.99\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\": \"fiction\",\n" +
            "        \"author\": \"Herman Melville\",\n" +
            "        \"title\": \"Moby Dick\",\n" +
            "        \"isbn\": \"0-553-21311-3\",\n" +
            "        \"price\": 8.99\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\": \"fiction\",\n" +
            "        \"author\": \"J. R. R. Tolkien\",\n" +
            "        \"title\": \"The Lord of the Rings\",\n" +
            "        \"isbn\": \"0-395-19395-8\",\n" +
            "        \"price\": 22.99\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bicycle\": {\n" +
            "      \"color\": \"red\",\n" +
            "      \"price\": 19.95\n" +
            "    }\n" +
            "  },\n" +
            "  \"expensive\": 10\n" +
            "}";

    @Test
    public void test1() {
        List<String> authors = JsonPath.read(jsonStr, "$.store.book[*].author");
        //["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"]
        System.out.println(authors);
    }

    @Test
    public void test2() {
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);

        String author0 = JsonPath.read(document, "$.store.book[0].author");
        String author1 = JsonPath.read(document, "$.store.book[1].author");
        //Nigel Rees
        System.out.println(author0);
        //Evelyn Waugh
        System.out.println(author1);
    }

    @Test
    public void test3() {
        DocumentContext ctx = JsonPath.parse(jsonStr);
        List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");
        //["Herman Melville","J. R. R. Tolkien"]
        System.out.println(authorsOfBooksWithISBN);

        //[{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99},
        // {"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]
        List<Map<String, Object>> expensiveBooks = JsonPath.using(Configuration.defaultConfiguration())
                .parse(jsonStr)
                .read("$.store.book[?(@.price > 10)]", List.class);
        System.out.println(expensiveBooks);
    }

    @Test
    public void test4() {
        //Nigel Rees
        String author = JsonPath.parse(jsonStr).read("$.store.book[0].author");

        //java.lang.ClassCastException: java.lang.String cannot be cast to java.util.List
        List<String> list = JsonPath.parse(jsonStr).read("$.store.book[0].author");
    }

    @Test
    public void test5() {
        String json = "{\"date_as_long\":  1411455611975}";
        Date date = JsonPath.parse(json).read("$['date_as_long']", Date.class);
        //Tue Sep 23 15:00:11 CST 2014
        System.out.println(date);
    }

    @Test
    public void test6() {
        Book book = JsonPath.parse(jsonStr).read("$.store.book[0]", Book.class);
        //Book{category='reference', title='Sayings of the Century', author='Nigel Rees', price=8.95, isbn='null'}
        System.out.println(book);
    }

    @Test
    public void test7() {
        //注意，默认的Json-smart provider不支持TypeRef，可以使用Jackson或Gson Provider
        TypeRef<List<String>> typeRef = new TypeRef<List<String>>() {
        };

        List<String> titles = JsonPath.parse(jsonStr).read("$.store.book[*].title", typeRef);
        System.out.println(titles);
    }


    @Test
    public void test8() {
        //返回路径
        Configuration configuration = Configuration
                .builder()
                .options(Option.AS_PATH_LIST)
                .build();

        List<String> pathList = JsonPath.using(configuration).parse(jsonStr).read("$..author");
        System.out.println(pathList);
        //["$['store']['book'][0]['author']",
        // "$['store']['book'][1]['author']",
        // "$['store']['book'][2]['author']","
        // $['store']['book'][3]['author']"]
    }

    @Test
    public void test9() {
        //language=JSON
        String str = "{\"name\": \"tom\"}";

        //设置值
        String newStr1 = JsonPath.parse(str)
                .set("$.name", "jerry")
                .jsonString();

        //添加
        String newStr2 = JsonPath.parse(newStr1)
                .put("$", "age", 17)
                .jsonString();

        //{"name":"jerry","age":17}
        System.out.println(newStr2);
    }

    @Test
    public void configTest1() {
        //language=JSON
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"name\" : \"john\",\n" +
                "    \"gender\" : \"male\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\" : \"ben\"\n" +
                "  }\n" +
                "]";

        //默认情况抛出异常
        //com.jayway.jsonpath.PathNotFoundException: No results for path: $[1]['gender']
        String gender1 = JsonPath.parse(jsonStr).read("$[1].gender");

        //使用DEFAULT_PATH_LEAF_TO_NULL，会返回Null
        Configuration configuration = Configuration.builder()
                .options(Option.DEFAULT_PATH_LEAF_TO_NULL)
                .build();
        String gender2 = JsonPath.using(configuration).parse(jsonStr).read("$[1].gender");
        System.out.println(gender2);//null
    }


    @Test
    public void configTest2() {
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"name\" : \"john\",\n" +
                "    \"gender\" : \"male\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\" : \"ben\"\n" +
                "  }\n" +
                "]";
        //默认情况下抛出异常
        //java.lang.ClassCastException: java.lang.String cannot be cast to java.util.List
        List<String> genders1 = JsonPath.parse(jsonStr).read("$[0].gender");

        //使用ALWAYS_RETURN_LIST，返回一个列表
        Configuration configuration = Configuration.builder()
                .options(Option.ALWAYS_RETURN_LIST)
                .build();
        List<String> genders2 = JsonPath.using(configuration).parse(jsonStr).read("$[0].gender");
        System.out.println(genders2);//["male"]
    }

    @Test
    public void configTest3() {
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"name\" : \"john\",\n" +
                "    \"gender\" : \"male\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\" : \"ben\"\n" +
                "  }\n" +
                "]";

        //存在 ALWAYS_RETURN_LIST选项，则返回空列表
        Configuration configuration1 = Configuration.builder()
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS)
                .build();
        List<String> genders1 = JsonPath.using(configuration1).parse(jsonStr).read("$[1].gender");
        System.out.println(genders1);//[]

        //不存在 ALWAYS_RETURN_LIST选项，则返回Null
        Configuration configuration2 = Configuration.builder()
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();
        List<String> genders2 = JsonPath.using(configuration2).parse(jsonStr).read("$[1].gender");
        System.out.println(genders2);//null
    }


    @Test
    public void configTest4() {
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"name\" : \"john\",\n" +
                "    \"gender\" : \"male\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\" : \"ben\"\n" +
                "  }\n" +
                "]";

        //存在 REQUIRE_PROPERTIES，则抛出异常
        //com.jayway.jsonpath.PathNotFoundException: No results for path: $[1]['gender']
        Configuration configuration1 = Configuration.builder()
                .options(Option.REQUIRE_PROPERTIES)
                .build();
        // List<String> genders1 = JsonPath.using(configuration1).parse(jsonStr).read("$[*].gender");

        //不存在 REQUIRE_PROPERTIES，返回["male"]
        Configuration configuration2 = Configuration.builder()
                .options()
                .build();
        List<String> genders2 = JsonPath.using(configuration2).parse(jsonStr).read("$[*].gender");
        System.out.println(genders2);//["male"]
    }
}
