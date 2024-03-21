package pers.zhang.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class BDDAssertionsExamples {

    @Test
    public void test() {
        //方法来自静态导入的BDDAssertions.then()
        then("hello").isEqualTo("hello");
        then(1).isInstanceOf(Number.class);
    }
}
