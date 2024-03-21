package pers.zhang.assertj;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;


public class WithAssertionsExamples implements WithAssertions {

    @Test
    public void test() {
        //不需要静态导入，assertThat是来自于WithAssertions的方法
        assertThat("hello").isEqualTo("hello");
        assertThat(1).isInstanceOf(Number.class);
    }

}
