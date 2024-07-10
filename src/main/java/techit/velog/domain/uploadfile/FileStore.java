package techit.velog.domain.uploadfile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.global.exception.CustomWebException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileStore {
//    private final AmazonS3Client amazonS3Client;

    @Value("${file.dir}")
    private String bucket;

    public String getFullPath(String fileName) {
        return bucket + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> files) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if(!file.isEmpty()) {
                uploadFiles.add(storeFile(file));
            }

        }
        return uploadFiles;
    }

    public UploadFile storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        String storeFile = storeFileName(file);

        String fullPath = getFullPath(storeFile);
        file.transferTo(new File(fullPath));
        return new UploadFile(file.getOriginalFilename(), storeFile);
    }


    /*public UploadFile testFile(MultipartFile file, String directory) {
        String storeFileName = storeFileName(file);
        String originalFilename = file.getOriginalFilename();
        String storeFileUrl = getUrl(directory,storeFileName);
        log.info("fileUrl = {}", storeFileUrl);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentDisposition(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, storeFileName, file.getInputStream(), metadata);
            return new UploadFile(originalFilename, storeFileUrl);
        } catch (Exception e) {
            throw new CustomWebException(e.getMessage());
        }
    }*/

    private String getUrl(String directory, String fileName) {
        return "https://"+bucket+ "/"+directory+ fileName;
    }

    private String storeFileName(MultipartFile file) {
        if (file.isEmpty()) return null;

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);

        List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

        if (!allowedExtensions.contains(ext)) {
            throw new IllegalArgumentException("지원하지 않는 포맷입니다.");
        }
        return uuid + "." + ext;

    }
}
