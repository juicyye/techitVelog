package techit.velog.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import techit.velog.domain.uploadfile.FileStore;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FileStore fileStore;

    @GetMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/posts");
        requestDispatcher.forward(request, response);
    }

}
