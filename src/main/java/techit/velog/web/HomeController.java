package techit.velog.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.uploadfile.FileStore;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FileStore fileStore;

    @GetMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/posts");
        requestDispatcher.forward(request, response);
    }

    @RequestMapping("favicon.ico")
    public void favicon(HttpServletResponse response) throws IOException {
        response.sendRedirect("/path/to/your/favicon.ico");
    }

}
