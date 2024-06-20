package techit.velog.global.util;

public class SplitService {
    public static String split(String name) {
        String regex = "[ !@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\};:'\"\\\\|,.<>\\/?]+";
        return name.replaceAll(regex, "-");
    }
}
