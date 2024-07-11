package techit.velog.temp;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class regexp {

    @Test
    void regexp_test() throws Exception {
        // given
        String temp = "ekzm!%1536";
        boolean matches = Pattern.matches("(?=.*[!@#$%]).{8,20}", temp);
        System.out.println("matches = " + matches);

        String temp2 = "   ";
        boolean matches1 = Pattern.matches("^(?=.*[ㄱ-ㅎ가-힣a-zA-Z0-9])[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{1,8}[ㄱ-ㅎ가-힣a-zA-Z0-9]$", temp2);
        System.out.println("matches1 = " + matches1);


        String temp3 = "내가 만든 쿠키";
        boolean matches2 = Pattern.matches("^(?=.*[ㄱ-ㅎ가-힣a-zA-Z0-9])[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{1,19}[ㄱ-ㅎ가-힣a-zA-Z0-9]$", temp3);
        System.out.println("matches2 = " + matches2);



        // when

        // then
    }
}
