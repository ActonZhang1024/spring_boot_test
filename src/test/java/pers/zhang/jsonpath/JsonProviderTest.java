package pers.zhang.jsonpath;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * @Author: acton_zhang
 * @Date: 2024/1/27 4:48 下午
 * @Version 1.0
 */
public class JsonProviderTest {

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
    public void test() {
        //使用JacksonJsonProvider
        Configuration configuration = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .build();

        ReadContext ctx = JsonPath.using(configuration).parse(jsonStr);

        TypeRef<List<Book>> typeRef = new TypeRef<List<Book>>() {};
        List<Book> books = ctx.read("$.store.book[*]", typeRef);

        books.forEach(System.out::println);
    }
}
