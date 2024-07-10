package techit.velog.domain.uploadfile;

public interface S3VO {
    String POST_IMAGE_UPLOAD_DIRECTORY ="post-images";
    String POST_THUMBNAIL_UPLOAD_DIRECTORY ="post-thumbnail";
    String USER_PROFILE_IMAGE_UPLOAD_DIRECTORY ="user-image";
    String USER_DEFAULT_IMAGE = "https://jyvelogbucket.s3.ap-northeast-2.amazonaws.com/user-image/dev-jeans.png";
}
