package pers.zhang.jsonpath;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/27 9:42 下午
 * @Version 1.0
 */
public class FilterTest {


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
        List<Map<String, Object>> books = JsonPath.parse(jsonStr)
                .read("$.store.book[?(@.price < 10)]");
        books.forEach(System.out::println);
        //{category=reference, author=Nigel Rees, title=Sayings of the Century, price=8.95}
        //{category=fiction, author=Herman Melville, title=Moby Dick, isbn=0-553-21311-3, price=8.99}


        books = JsonPath.parse(jsonStr)
                .read("$.store.book[?(!@.price < 10)]");
        books.forEach(System.out::println);
        //{category=reference, author=Nigel Rees, title=Sayings of the Century, price=8.95}
        //{category=fiction, author=Herman Melville, title=Moby Dick, isbn=0-553-21311-3, price=8.99}
    }

    @Test
    public void test2() {
        Filter cheapFictionFilter = Filter.filter(
                Criteria.where("price").lte(10)
        );
        //注意路径中过滤器的占位符 ?
        //当提供多个过滤器时，它们将按顺序应用，其中占位符的数量必须与提供的过滤器数量相匹配。
        //可以在一个过滤器操作中指定多个谓词占位符[?, ?]，但两个谓词必须匹配。
        List<Map<String, Object>> books = JsonPath.parse(jsonStr)
                .read("$.store.book[?]", cheapFictionFilter);
        books.forEach(System.out::println);
        //{category=reference, author=Nigel Rees, title=Sayings of the Century, price=8.95}
        //{category=fiction, author=Herman Melville, title=Moby Dick, isbn=0-553-21311-3, price=8.99}

        //过滤器还可以与OR和AND组合使用
        Filter f = Filter.filter(Criteria.where("price").lte(10))
                .and(Criteria.where("isbn").exists(true));

        books = JsonPath.parse(jsonStr)
                .read("$.store.book[?]", f);
        books.forEach(System.out::println);
        //{category=fiction, author=Herman Melville, title=Moby Dick, isbn=0-553-21311-3, price=8.99}
    }

    @Test
    public void test3() {
        //自定义谓词
        Predicate booksWithISBN = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                return ctx.item(Map.class).containsKey("isbn");
            }
        };
        List<Map<String, Object>> books = JsonPath.parse(jsonStr)
                .read("$.store.book[?]", booksWithISBN);
        books.forEach(System.out::println);
        //{category=fiction, author=Herman Melville, title=Moby Dick, isbn=0-553-21311-3, price=8.99}
        //{category=fiction, author=J. R. R. Tolkien, title=The Lord of the Rings, isbn=0-395-19395-8, price=22.99}
    }
}
