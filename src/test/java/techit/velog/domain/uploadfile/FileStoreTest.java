package techit.velog.domain.uploadfile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileStoreTest {

    @Autowired
    private FileStore fileStore;


    @Test
    void s3_test() throws Exception {
        String temp = "https://jyvelogbucket.s3.ap-northeast-2.amazonaws.com/post-images/896d977d-e2a4-4cd4-af82-0a1fe92fb64e.png";
        int i = temp.indexOf("/", 10);
        System.out.println("i = " + i);
        String substring = temp.substring(i+1);
        System.out.println("substring = " + substring);
        // given


        // when

        // then
    }

}