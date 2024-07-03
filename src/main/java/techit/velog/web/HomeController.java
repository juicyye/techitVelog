package techit.velog.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import techit.velog.domain.uploadfile.FileStore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FileStore fileStore;

    @GetMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/posts");
        requestDispatcher.forward(request, response);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            Resource resource = new FileUrlResource(fileStore.getFullPath(filename));
            if(!resource.exists()) {
                return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
            }
            Path filePath = Paths.get(fileStore.getFullPath(filename));
            httpHeaders.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
