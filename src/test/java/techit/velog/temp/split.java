package techit.velog.temp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class split {

    @Test
    void 이름_test() throws Exception {
        // given
        String temp = "adff, adflk, afdslkf, fadf";

        // when
        String[] split = temp.split("\\s*,\\s*|\\s+");
        for (String s : split) {
            System.out.println("s = " + s);
        }

        // then
        Assertions.assertThat(split.length).isEqualTo(4);
    }
}
