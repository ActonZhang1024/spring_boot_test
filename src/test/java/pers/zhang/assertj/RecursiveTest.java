package pers.zhang.assertj;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assumptions.*;
import static org.assertj.core.api.BDDAssertions.*;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/24 11:53 下午
 * @Version 1.0
 */
public class RecursiveTest {

    //递归基本用法

    class Person {
        String name;
        double htight;
        Home home = new Home();

        public Person(String name, double htight) {
            this.name = name;
            this.htight = htight;
        }
    }

    class Home {
        Address address = new Address();
        Date ownedSince;
    }

    static class Address {
        int number;
        String street;
    }

    @Test
    public void test1() {
        Person sherlock = new Person("Sherlock", 1.80);
        sherlock.home.ownedSince = new Date(123);
        sherlock.home.address.street = "Baker Street";
        sherlock.home.address.number = 221;

        Person sherlock2 = new Person("Sherlock", 1.80);
        sherlock2.home.ownedSince = new Date(123);
        sherlock2.home.address.street = "Baker Street";
        sherlock2.home.address.number = 221;

        //断言成功，逐字段递归比较
        assertThat(sherlock).usingRecursiveComparison()
                .isEqualTo(sherlock2);

        //断言失败，因为Person只比较引用
        assertThat(sherlock).isEqualTo(sherlock2);
    }


    //递归断言
    class Author {
        String name;
        String email;
        List<Book> books = new ArrayList<>();

        Author(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    class Book {
        String title;
        Author[] authors;

        Book(String title, Author[] authors) {
            this.title = title;
            this.authors = authors;
        }
    }

    @Test
    public void test2() {
        Author pramodSadalage = new Author("Pramod Sadalage", "p.sadalage@recursive.test");
        Author martinFowler = new Author("Martin Fowler", "m.fowler@recursive.test");
        Author kentBeck = new Author("Kent Beck", "k.beck@recursive.test");

        Book noSqlDistilled = new Book("NoSql Distilled", new Author[] {pramodSadalage, martinFowler});
        pramodSadalage.books.add(noSqlDistilled);
        martinFowler.books.add(noSqlDistilled);

        Book refactoring = new Book("Refactoring", new Author[] {martinFowler, kentBeck});
        martinFowler.books.add(refactoring);
        kentBeck.books.add(refactoring);


    }

    @Test
    public void softAssertTest() {
        //构建一个SoftAssertions实例来记录所有断言错误
        SoftAssertions softly = new SoftAssertions();

        //使用softly.assertThat而不是通常的assertThat方法
        softly.assertThat("George Martin").as("great authors").isEqualTo("JRR Tolkien");
        softly.assertThat(42).as("response to Everything").isGreaterThan(100);
        softly.assertThat("Gandalf").isEqualTo("Sauron");

        //不要忘记调用assertAll()，否则不会报告断言错误!
        softly.assertAll();
    }

    @Test
    public void assumptionTest1() {
        //假设的条件
        assumeThat("hello").isLowerCase();
        //只有假设条件成立，才继续执行
        assertThat(1).isLessThan(10);
    }
}
