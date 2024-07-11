package techit.velog.global.exception;

public class CustomWebException extends RuntimeException{
    public CustomWebException(String message) {
        super(message);
    }

}
