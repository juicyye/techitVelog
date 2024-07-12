package techit.velog.domain.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("임시로 보내봅니다");
        message.setTo(email);
        message.setText("테스트용");
        javaMailSender.send(message);
    }


}


