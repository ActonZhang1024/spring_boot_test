package pers.zhang.assertj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.configuration.Configuration;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.internal.matchers.Not;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.*;

public class DemoTest {


    @Test
    public void test() {
        Person p1 = new Person("tom", 18);
        Person p2 = new Person("jerry", 22);

        //基础断言
        assertThat(p1.getName()).isEqualTo("tom");
        assertThat(p1).isNotEqualTo(p2);

        //字符串断言
        assertThat("hello")
                .startsWith("he")
                .endsWith("lo")
                .isEqualToIgnoringCase("HELLO");

        //集合断言
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        assertThat(set).hasSize(3)
                .contains("b", "c")
                .doesNotContain("z");

        //断言描述
        assertThat(p1.getAge())
                .as("check %d's age", p1.getAge())
                .isEqualTo(18);

        //异常断言，标准风格
        assertThatThrownBy(() -> {
            throw new Exception("boom!");
        }).hasMessage("boom!");

        //异常断言，BDD风格
        Throwable thrown = catchThrowable(() -> {
            throw new Exception("boom!");
        });
        assertThat(thrown).hasMessageContaining("boom");

        //提取多个值为元组并断言
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        assertThat(list).extracting("name", "age")
                .contains(tuple("tom", 18), tuple("jerry", 22));

        //断言之前过滤集合
        assertThat(list).filteredOn(p -> p.getAge() > 18)
                .containsOnly(p2);

        //过滤和提取组合并断言
        assertThat(list).filteredOn(p -> p.getAge() > 18)
                .containsOnly(p2)
                .extracting("name", "age")
                .contains(tuple("jerry", 22));
    }

    class Person {
        String name;
        Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }


    @Test
    public void asTest() {
        Assertions.setPrintAssertionsDescription(true);
        //注意，要在断言之前调用as方法
        assertThat("hello")
                .as("描述")
                .isEqualTo("hello");

        assertThat(1)
                .as("check %s num", 1)
                .isGreaterThan(18);
    }

    @Test
    public void errMessageTest() {
        assertThat(1)
                .withFailMessage("check %s num", 1)
                .isGreaterThan(18);

        assertThat(1)
                .overridingErrorMessage("check %s num", 1)
                .isGreaterThan(18);
    }

    @Test
    public void errMessageTest2() {
        assertThat(1)
                .withFailMessage(() -> "check num")
                .isGreaterThan(18);

        assertThat(1)
                .overridingErrorMessage(() -> "check num")
                .isGreaterThan(18);
    }

    @Test
    public void configTest1() {
        //是否允许使用私有字段进行比较
        Assertions.setAllowComparingPrivateFields(true);
        //是否允许提取私有字段
        Assertions.setAllowExtractingPrivateFields(false);
        //设置提取器是否考虑裸命名的属性方法
        Assertions.setExtractBareNamePropertyMethods(false);
        //是否使用宽松的日期解析
        Assertions.setLenientDateParsing(true);
        //为可迭代对象、数组和map设置在错误消息中显示的最大元素数
        Assertions.setMaxElementsForPrinting(100);
        //设置单行描述的最大长度
        Assertions.setMaxLengthForSingleLineDescription(250);
        //设置是否从断言错误堆栈跟踪中删除与 AssertJ 相关的元素
        Assertions.setRemoveAssertJRelatedElementsFromStackTrace(true);
        //注册一个Representation来控制 AssertJ 格式化断言错误消息中显示的不同类型的方式
        //默认为StandardRepresentation
        Assertions.useRepresentation(new StandardRepresentation());
        //注册一些 AssertJ 将在日期断言中使用的自定义格式
        Assertions.registerCustomDateFormat(new SimpleDateFormat());
        //启用/禁用向控制台打印断言描述
        Assertions.setPrintAssertionsDescription(true);
    }

    @Test
    public void configTest2() {
        //创建配置对象
        Configuration configuration = new Configuration();
        //设置属性
        configuration.setBareNamePropertyExtraction(false);
        configuration.setComparingPrivateFields(false);
        configuration.setExtractingPrivateFields(false);
        configuration.setLenientDateParsing(true);
        configuration.setMaxElementsForPrinting(1001);
        configuration.setMaxLengthForSingleLineDescription(81);
        configuration.setRemoveAssertJRelatedElementsFromStackTrace(false);
        //应用，不要忘记调用apply方法
        configuration.applyAndDisplay();
    }

    /**
     * 常见断言
     */
    @Test
    public void commonAssertionsTest() {
        //设置断言描述
        assertThat(1).describedAs("描述").isLessThan(2);

        //equals比较
        assertThat(Integer.valueOf(1)).isEqualTo(1);
        assertThat(1).isNotEqualTo(2);

        //是否为Null
        String str = null;
        assertThat(str).isNull();
        assertThat(new Object()).isNotNull();

        // ==比较引用
        assertThat(Integer.valueOf(1)).isSameAs(Integer.valueOf(1));
        assertThat(Integer.valueOf(128)).isNotSameAs(Integer.valueOf(128));

        // 是否存在
        List<Integer> list = Arrays.asList(4, 5, 6);
        assertThat(1).isIn(1, 2, 3);
        assertThat(4).isIn(list);
        assertThat(9).isNotIn(1, 2, 3);
        assertThat(9).isNotIn(list);

        //条件断言
        Condition<String> condition = new Condition<String>(s -> s.length() < 6, "a %s word", "short");
        assertThat("hello").is(condition);
        assertThat("hello").has(condition);
        assertThat("hello").satisfies(condition);

        //类型判断
        assertThat(Integer.valueOf(1)).isInstanceOf(Number.class);
        assertThat(Integer.valueOf(1)).isInstanceOfAny(Number.class, Integer.class);
        assertThat(Integer.valueOf(1)).hasSameClassAs(Integer.valueOf(2));
    }

    @Test
    public void objectAssertionsTest() {
        Book b1 = new Book();
        //只有空字段
        assertThat(b1).hasAllNullFieldsOrProperties();
        b1.setName("三国演义");
        assertThat(b1).hasAllNullFieldsOrPropertiesExcept("name");

        //没有空字段
        assertThat(b1).hasNoNullFieldsOrPropertiesExcept("author", "price");
        b1.setAuthor("罗贯中");
        b1.setPrice(22.22);
        assertThat(b1).hasNoNullFieldsOrProperties();

        //判断是否具有指定字段
        assertThat(b1).hasFieldOrProperty("name");
        assertThat(b1).hasFieldOrPropertyWithValue("name", "三国演义");

        //提取值
        assertThat(b1).extracting("name", "author")
                .contains("三国演义", "罗贯中");
    }


    @Test
    public void stringAssertionsTest() {
        //断言null、空、空格
        assertThat("").isNullOrEmpty();
        assertThat("").isEmpty();
        assertThat("a").isNotEmpty();
        assertThat("  ").isBlank();
        assertThat("a").isNotBlank();

        //包含空
        assertThat("a b").containsWhitespaces();
        assertThat("  ").containsOnlyWhitespaces();
        assertThat("ab").doesNotContainAnyWhitespaces();
        assertThat("a b c").doesNotContainOnlyWhitespaces();

        //字符串长度
        assertThat("hello").hasSize(5);
        assertThat("hello").hasSizeLessThan(6);
        assertThat("hello").hasSizeLessThanOrEqualTo(5);
        assertThat("hello").hasSizeGreaterThan(4);
        assertThat("hello").hasSizeLessThanOrEqualTo(5);
        assertThat("hello").hasSizeBetween(4, 6);
        assertThat("hello").hasSameSizeAs("world");

        //字符串行数
        assertThat("hello\nworld").hasLineCount(2);

        //字符串相等
        assertThat("hello").isEqualTo("hello");
        assertThat("hello").isEqualToIgnoringCase("Hello");
        assertThat("hello").isNotEqualToIgnoringCase("Hallo");
        //忽略空格
        assertThat("a b c").isEqualToIgnoringWhitespace("abc");
        assertThat("a b c").isNotEqualToIgnoringWhitespace("aba");

        //只包含数字
        assertThat("1234").containsOnlyDigits();

        //包含子串
        assertThat("hello world").containsOnlyOnce("hel");
        assertThat("hello world").contains("wor", "hel");
        assertThat("hello world").containsSequence("hel", "lo");
        assertThat("hello world").containsIgnoringCase("HEL");
        assertThat("hello world").doesNotContain("bb");
        assertThat("hello world").doesNotContainIgnoringCase("BB");
        assertThat("hello world").doesNotContainPattern(Pattern.compile("^a.*"));

        //开头结尾
        assertThat("hello").startsWith("he");
        assertThat("hello").doesNotStartWith("ha");
        assertThat("hello").endsWith("lo");
        assertThat("hello").doesNotEndWith("la");

        //正则匹配
        assertThat("hello").matches(Pattern.compile("^he.*"));
        assertThat("hello").doesNotMatch(Pattern.compile("^ha.*"));

        //子串
        assertThat("abc").isSubstringOf("abcdef");

        //大小写
        assertThat("hello").isLowerCase();
        assertThat("HELLO").isUpperCase();
    }

    @Test
    public void iterableAssertionsTest() {
        //null或空
        assertThat(new ArrayList<>()).isNullOrEmpty();
        assertThat(new ArrayList<>()).isEmpty();
        assertThat(Arrays.asList(1, 2)).isNotEmpty();

        //容量
        assertThat(Arrays.asList(1, 2)).hasSize(2);
        assertThat(Arrays.asList(1, 2)).hasSizeGreaterThan(1);
        assertThat(Arrays.asList(1, 2)).hasSizeGreaterThanOrEqualTo(2);
        assertThat(Arrays.asList(1, 2)).hasSizeLessThan(3);
        assertThat(Arrays.asList(1, 2)).hasSizeLessThanOrEqualTo(2);
        assertThat(Arrays.asList(1, 2)).hasSameSizeAs(Arrays.asList(3, 4));

        //包含
        assertThat(Arrays.asList(1, 2, 3, 4)).contains(2);
        assertThat(Arrays.asList(1, 2, 3, 4)).containsOnly(2, 1, 3, 4);
        assertThat(Arrays.asList(1, 2, 3, 4)).containsOnlyOnce(2);
        assertThat(Arrays.asList(null, null)).containsOnlyNulls();
        assertThat(Arrays.asList(1, 2, 3)).containsExactly(1, 2, 3);
        assertThat(Arrays.asList(1, 2, 3)).containsExactlyInAnyOrder(2, 1, 3);
        assertThat(Arrays.asList(1, 2, 3)).containsSequence(1, 2);
        assertThat(Arrays.asList(1, 2, 3)).doesNotContainSequence(1, 3);
        assertThat(Arrays.asList(1, 2, 3, null)).containsNull();
        assertThat(Arrays.asList(1, 2, 3)).doesNotContainNull();

        //开头结尾
        assertThat(Arrays.asList(1, 2, 3)).startsWith(1);
        assertThat(Arrays.asList(1, 2, 3)).endsWith(3);

        //条件
        assertThat(Arrays.asList(1, 2, 3)).are(new Condition<Integer>(num -> num > 0, "正数"));
        assertThat(Arrays.asList(1, 2, 3)).have(new Condition<Integer>(num -> num > 0, "正数"));

        //类型
        assertThat(Arrays.asList(1, 2, 3)).hasAtLeastOneElementOfType(Integer.class);
        assertThat(Arrays.asList(1, 2, 3)).hasOnlyElementsOfType(Integer.class);
        assertThat(Arrays.asList(1, 2, 3)).doesNotHaveAnyElementsOfTypes(String.class);
        assertThat(Arrays.asList(1, 2, 3)).hasOnlyElementsOfType(Integer.class);

        //提取
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        assertThat(map).extracting("one", "two")
                .contains(1, 2);

        //过滤
        assertThat(Arrays.asList(1, 3, 5, 7, 9)).filteredOn(e -> e > 5)
                .contains(7, 9);

        //导航
        assertThat(Arrays.asList(1, 2, 3, 4)).first()
                .isEqualTo(1);
        assertThat(Arrays.asList(1, 2, 3, 4)).last()
                .isEqualTo(4);
        assertThat(Arrays.asList(1, 2, 3, 4)).element(2)
                .isEqualTo(3);//下标从0开始

        //谓词匹配
        assertThat(Arrays.asList(1, 2, 3, 4)).allMatch(e -> e > 0);
    }

    @Test
    public void exceptionAssertionsTest() {
        //断言异常消息
        Throwable throwable = new IllegalArgumentException("wrong amount 123");
        assertThat(throwable).hasMessage("wrong amount 123")
                .hasMessage("%s amount %d", "wrong", 123)
                .hasMessageStartingWith("wrong")
                .hasMessageStartingWith("%s a", "wrong")
                .hasMessageContaining("wrong amount")
                .hasMessageContainingAll("wrong", "amount")
                .hasMessageEndingWith("123")
                .hasMessageEndingWith("amount %s", "123")
                .hasMessageMatching("wrong amount.*")
                .hasMessageNotContaining("right")
                .hasMessageNotContainingAny("right", "price");

        //断言异常原因
        NullPointerException cause = new NullPointerException("boom!");
        Throwable t = new Throwable(cause);
        assertThat(t).hasCause(cause)
                .hasCauseInstanceOf(NullPointerException.class)
                .hasCauseInstanceOf(RuntimeException.class)//可匹配父类
                .hasCauseExactlyInstanceOf(NullPointerException.class);//严格匹配，只能相同类型

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    throw new RuntimeException(new IllegalArgumentException("boom!"));
                })
                .havingCause()
                .withMessage("boom!");

        //断言异常根本原因
        NullPointerException rootCause = new NullPointerException("null!");
        Throwable err = new Throwable(new IllegalArgumentException(rootCause));
        assertThat(err).hasRootCause(rootCause)
                .hasRootCauseMessage("null!")
                .hasRootCauseMessage("%s!", "null")
                .hasRootCauseInstanceOf(NullPointerException.class)
                .hasRootCauseInstanceOf(RuntimeException.class)
                .hasRootCauseExactlyInstanceOf(NullPointerException.class);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    throw new RuntimeException(new IllegalArgumentException(new NullPointerException("root error")));
                })
                .havingRootCause()
                .withMessage("root error");

        //BDD风格
        String[] names = {"Pier", "Pol", "Jak"};
        Throwable thrown = catchThrowable(() -> {
            //数组越界
            System.out.println(names[9]);
        });
        then(thrown).isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessageContaining("9");

        //断言抛出者
        assertThatThrownBy(() -> {
            throw new Exception("boom!");
        }).isInstanceOf(Exception.class).hasMessageContaining("boom");

        //断言异常类型
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> {
                    throw new IOException("boom!");
                })
                .withMessage("%s!", "boom")
                .withMessageContaining("boom")
                .withNoCause();

        //断言没有异常
        assertThatNoException().isThrownBy(() -> {
            System.out.println("OK");
        });
        thenNoException().isThrownBy(() -> {
            System.out.println("OK");
        });
        assertThatCode(() -> {
            System.out.println( "OK");
        }).doesNotThrowAnyException();
        thenCode(() -> {
            System.out.println("OK");
        }).doesNotThrowAnyException();
    }


}
