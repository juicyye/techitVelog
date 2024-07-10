package techit.velog.global.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import techit.velog.global.exception.CustomWebException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(CustomWebException.class)
    public String CustomWebExceptionHandler(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "error/4xx";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        return "error/5xx";
    }*/
}
