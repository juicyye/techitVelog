package techit.velog.domain.mail.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import techit.velog.domain.mail.service.EmailService;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailEventHandler {
    private final EmailService emailService;

    @TransactionalEventListener
    @Async("customTaskExecutor")
    public void handleMailSendEvent(MailEvent event) throws InterruptedException {
        Thread.sleep(2000);
        emailService.sendSimpleEmail(event.getEmail());
        log.info("event {}",event);
    }
}
