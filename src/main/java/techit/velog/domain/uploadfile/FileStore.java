package techit.velog.domain.uploadfile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.global.exception.CustomWebException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStore {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;


    public List<UploadFile> storeFiles(List<MultipartFile> files) {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if(!file.isEmpty()) {
                uploadFiles.add(storeFile(file, S3VO.POST_IMAGE_UPLOAD_DIRECTORY));
            }

        }
        return uploadFiles;
    }
    @Transactional
    public UploadFile storeFile(MultipartFile file, String directory) {
        if (file.isEmpty()) {
            return null;
        }
        String storeFileName = storeFileName(file);
        String originalFilename = file.getOriginalFilename();
        String uploadUrl = getUrl(directory);


        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentDisposition(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(uploadUrl, storeFileName, file.getInputStream(), metadata);

            return new UploadFile(getFullPath(directory,originalFilename), getFullPath(directory,storeFileName));
        } catch (Exception e) {
            throw new CustomWebException(e.getMessage());
        }
    }



    private String getUrl(String directory) {
        return bucket + "/" + directory;
    }

    private String getFullPath(String directory, String fileName) {
        return amazonS3.getUrl(bucket, directory+"/"+fileName).toString();
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
    @Transactional
    public void deleteFile(String fileName) {
        String deleteFile = fileName.substring(fileName.indexOf("/", 10)+1);
        String jeans = S3VO.USER_DEFAULT_IMAGE.substring(fileName.indexOf("/", 10) + 1);
        if (deleteFile.equals(jeans)) {
            return;
        }
        try {
            amazonS3.deleteObject(bucket, deleteFile);
        } catch (Exception e) {
            throw new CustomWebException(e.getMessage());
        }
    }
}
