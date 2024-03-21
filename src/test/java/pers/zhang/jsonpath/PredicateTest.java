package pers.zhang.jsonpath;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Author: acton_zhang
 * @Date: 2024/1/27 10:56 下午
 * @Version 1.0
 */
public class PredicateTest {

    //language=JSON
    private String jsonStr = "{\n" +
            "  \"store\":{\n" +
            "    \"book\":[\n" +
            "      {\n" +
            "        \"category\":\"reference\",\n" +
            "        \"author\":\"Nigel Rees\",\n" +
            "        \"title\":\"Sayings of the Century\",\n" +
            "        \"price\":8.95\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\":\"fiction\",\n" +
            "        \"author\":\"Evelyn Waugh\",\n" +
            "        \"title\":\"Sword of Honour\",\n" +
            "        \"price\":12.99\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\":\"fiction\",\n" +
            "        \"author\":\"Herman Melville\",\n" +
            "        \"title\":\"Moby Dick\",\n" +
            "        \"isbn\":\"0-553-21311-3\",\n" +
            "        \"price\":8.99\n" +
            "      },\n" +
            "      {\n" +
            "        \"category\":\"fiction\",\n" +
            "        \"author\":\"J. R. R. Tolkien\",\n" +
            "        \"title\":\"The Lord of the Rings\",\n" +
            "        \"isbn\":\"0-395-19395-8\",\n" +
            "        \"price\":22.99\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bicycle\":{\n" +
            "      \"color\":\"red\",\n" +
            "      \"price\":19.95\n" +
            "    },\n" +
            "    \"clothes\":[\n" +
            "      {\n" +
            "        \"name\":\"牛仔裤\",\n" +
            "        \"sizes\":\"S\",\n" +
            "        \"price\":94\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\":\"背心\",\n" +
            "        \"sizes\":\"M\",\n" +
            "        \"price\":48\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\":\"裙子\",\n" +
            "        \"sizes\":[\"S\", \"M\"],\n" +
            "        \"price\":1.24\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\":\"羊毛衫\",\n" +
            "        \"sizes\":[\"XS\", \"XL\"],\n" +
            "        \"price\":78.99\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\":\"Polo衫\",\n" +
            "        \"sizes\":[\"XS\", \"XL\", \"M\"],\n" +
            "        \"price\":18.99\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"expensive\":10\n" +
            "}";


    @Test
    public void test() {
        //使用JacksonJsonProvider
        Configuration configuration = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .build();
        ReadContext ctx = JsonPath.using(configuration)
                .parse(jsonStr);


        //方式一：内联谓词
        TypeRef<List<Clothes>> typeRef = new TypeRef<List<Clothes>>() {};
        final List<Clothes> clothes1 = ctx.read("$.store.clothes[?(@.price > 50 || @.sizes anyof ['M'])]", typeRef);
        System.out.println("************** clothes1 ***************");
        clothes1.forEach(System.out::println);

        //方式二：Filter谓词
        Filter filter = Filter.filter(Criteria.where("price").gt(50))
                .or(Criteria.where("sizes").anyof(Arrays.asList("M")));
        //使用谓词占位符？
        Clothes[] clothes2 = ctx.read("$.store.clothes[?]", Clothes[].class, filter);
        System.out.println("************** clothes2 ***************");
        for (Clothes clothes : clothes2) {
            System.out.println(clothes);
        }

        //方式三：自定义谓词
        Predicate rule = (context) -> {
            final Map map = context.item(Map.class);
            boolean b1 = false;
            Object priceObj = map.getOrDefault("price", null);
            if (priceObj != null) {
                String priceStr = priceObj.toString();
                Double price = 0d;
                try {
                    price = Double.parseDouble(priceStr);
                } catch (Exception e) {

                }
                b1 = price > 50d;
            }

            boolean b2 = false;
            Object sizes = map.getOrDefault("sizes", null);
            if (sizes != null && sizes instanceof List) {
                List<String> sizeList = (List<String>) sizes;
                List<String> targetList = Arrays.asList("M");
                for (String size : sizeList) {
                    if (targetList.contains(size)) {
                        b2 = true;
                        break;
                    }
                }
            }
            return b1 || b2;
        };
        // 使用谓词的占位符?
        Clothes[] clothes3 = ctx.read("$.store.clothes[?]", Clothes[].class, rule);
        System.out.println("-------------- clothes3 ---------------");
        for (Clothes clothes : clothes3) {
            System.out.println(clothes);
        }
    }

    @Test
    public void test1() {
        //language=JSON
        String jsonStr = "{\n" +
                "  \"name\": \"tom\",\n" +
                "  \"age\": 18,\n" +
                "  \"height\": 1.77,\n" +
                "  \"scores\": [1.1, 2.2, 3,3, 4.4, 5.5, 6.6]\n" +
                "}";
        ReadContext ctx = JsonPath.using(Configuration.defaultConfiguration()).parse(jsonStr);

        Double min = ctx.read("$.scores.min()");
        System.out.println(min);//1.1
        Double max = ctx.read("$.scores.max()");
        System.out.println(max);//6.6
        Double avg = ctx.read("$.scores.avg()");
        System.out.println(avg);//3.6857142857142864
        Double stddev = ctx.read("$.scores.stddev()");
        System.out.println(stddev);//1.7779832647682354
        Double sum = ctx.read("$.scores.sum()");
        System.out.println(sum);//25.800000000000004
        Integer length = ctx.read("$.scores.length()");
        System.out.println(length);//7
        Set<String> keys = ctx.read("$.keys()");
        System.out.println(keys);//[name, age, height, scores]


        String concat = ctx.read("$.concat(@.name, \" \", @.age)");
        System.out.println(concat);//tom 18

        List<Double> scores = ctx.read("$.scores.append(99.9)");
        System.out.println(scores);//[1.1,2.2,3,3,4.4,5.5,6.6,99.9]
    }
}
