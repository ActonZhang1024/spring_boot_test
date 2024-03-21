package pers.zhang.assertj;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/25 11:19 下午
 * @Version 1.0
 */
public class JsonAssertTest {

    @Test
    public void lenientTest() throws JSONException {
        String actual = "{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}", actual, JSONCompareMode.LENIENT);

        //宽容模式下，即使实际JSON包含扩展字段，数组顺序不一致也可以通过测试
        String actualJsonStr = "{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"scores\": [88, 77, 99]}", actualJsonStr, JSONCompareMode.LENIENT);
    }

    @Test
    public void strictTest() throws JSONException {
        String actual = "{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}", actual, JSONCompareMode.STRICT);

        //严格模式下，实际JSON包含扩展字段age、scores，测试不通过
        String actualJsonStr = "{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\"}", actualJsonStr, JSONCompareMode.STRICT);

        //严格模式下，实际JSON数组字段顺序不一致，测试不通过
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [99, 88, 77]}", actualJsonStr, JSONCompareMode.STRICT);
    }

    @Test
    public void nonExtensibleTest() throws JSONException {
        String actual = "{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}", actual, JSONCompareMode.NON_EXTENSIBLE);

        //非扩展模式下，数组顺序不一致也可以通过测试
        String actualJsonStr = "{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [99, 88, 77]}", actualJsonStr, JSONCompareMode.NON_EXTENSIBLE);

        //非扩展模式下，实际JSON有扩展字段无法通过测试
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\"}", actualJsonStr, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void strictOrderTest() throws JSONException {
        String actual = "{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"scores\": [77, 88, 99]}", actual, JSONCompareMode.STRICT_ORDER);

        //严格排序模式下，实际JSON有扩展字段也可通过测试
        String actualJsonStr = "{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [77, 88, 99]}";
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\"}", actualJsonStr, JSONCompareMode.STRICT_ORDER);

        //严格排序模式下，数组顺序不一致，无法通过测试
        JSONAssert.assertEquals("{\"id\": 123, \"name\": \"tom\", \"age\": 18, \"scores\": [99, 88, 77]}", actualJsonStr, JSONCompareMode.STRICT_ORDER);
    }

    @Test
    public void logicTest() throws JSONException {
        //无论是否严格，以下两种模式都会通过
        String result = "{\"id\": 1, \"name\": \"tom\"}";
        JSONAssert.assertEquals("{\"name\": \"tom\", \"id\": 1}", result, JSONCompareMode.STRICT);
        JSONAssert.assertEquals("{\"name\": \"tom\", \"id\": 1}", result, JSONCompareMode.LENIENT);

        //可以通过对同一值使用不同的类型来演示逻辑比较的另一个示例
        JSONObject expected = new JSONObject();
        expected.put("id", Integer.valueOf(12345));
        JSONObject actual = new JSONObject();
        actual.put("id", Double.valueOf(12345));
        //无论类型如何，逻辑值值都是12345，所以测试通过
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);

        //即使在嵌套对象的情况下，也是如此
        //language=JSON
        String actualJsonStr = "{\"id\": 1, \"name\": \"tom\", \"adress\": {\"city\": \"Hollywood\",\"state\": \"LA\"}}";
        String expectedJsonStr = "{\"id\": 1, \"name\": \"tom\", \"adress\": {\"city\": \"Hollywood\",\"state\": \"LA\"}}";
        JSONAssert.assertEquals(expectedJsonStr, actualJsonStr, JSONCompareMode.LENIENT);
    }

    @Test
    public void messageTest() throws JSONException {
        //language=JSON
        String actual = "{\"id\": 123, \"name\": \"tom\"}";
        String failureMessage = "Only one field is expected: name";
        try {
            JSONAssert.assertEquals(failureMessage, "{\"name\":  \"tom\"}", actual, JSONCompareMode.STRICT);
        } catch (AssertionError e) {
            Assertions.assertThat(e.getMessage()).containsIgnoringCase(failureMessage);
        }
    }

    @Test
    public void arrayTest1() throws JSONException {
        String result = "[\"Alex\", \"Barbera\", \"Charlie\", \"Xavier\"]";
        JSONAssert.assertEquals("[\"Charlie\", \"Alex\", \"Xavier\", \"Barbera\"]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertEquals("[\"Alex\", \"Barbera\", \"Charlie\", \"Xavier\"]", result, JSONCompareMode.STRICT);
        JSONAssert.assertNotEquals("[\"Charlie\", \"Alex\", \"Xavier\", \"Barbera\"]", result, JSONCompareMode.STRICT);
    }

    @Test
    public void arrayTest2() throws JSONException {
        String result ="[1,2,3,4,5]";
        //即使使用LENIENT比较模式，期望数组中的项目也必须与实际数组中的项目完全匹配。 添加或删除甚至单个元素都将导致失败
        JSONAssert.assertEquals("[1,2,3,4,5]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3,4,5,6]", result, JSONCompareMode.LENIENT);
    }

    @Test
    public void arrayTest3() throws JSONException {
        String names = "{\"names\":[\"Alex\", \"Barbera\", \"Charlie\", \"Xavier\"]}";
        //{names:[4]}指定预期数组大小为4
        JSONAssert.assertEquals(
                "{names:[4]}",
                names,
                new ArraySizeComparator(JSONCompareMode.LENIENT));
        //{names:[3,5]}指定预期数组大小为3和5之间
        JSONAssert.assertEquals(
                "{names:[3,5]}",
                names,
                new ArraySizeComparator(JSONCompareMode.LENIENT));
    }

    @Test
    public void regularTest() throws JSONException {
        JSONAssert.assertEquals("{\"entry\":{\"id\":x}}","{\"entry\":{\"id\":1, \"id\":2}}",
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("entry.id",
                                new RegularExpressionValueMatcher<Object>("\\d"))));

        JSONAssert.assertNotEquals("{\"entry\":{\"id\":x}}","{\"entry\":{\"id\":1, \"id\":\"as\"}}",
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("entry.id",
                                new RegularExpressionValueMatcher<Object>("\\d"))));
    }
}
