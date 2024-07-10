package techit.velog.temp;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class regexp {

    @Test
    void regexp_test() throws Exception {
        // given
        String temp = "$가adfadf125";
        boolean matches = Pattern.matches("(?=.*[!@#$%]).{8,20}", temp);
        System.out.println("matches = " + matches);


        // when

        // then
    }
}
