package techit.velog.global.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplitServiceTest {

    @Test
    void 테스트이름() throws Exception {
        // given
        String text = "Hello, world! How's everything going? $100 @ #test";

        // when
        String split = SplitService.split(text);
        System.out.println("split = " + split);

        // then
    }

}