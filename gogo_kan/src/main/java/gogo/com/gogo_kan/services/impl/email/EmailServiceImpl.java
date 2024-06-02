package gogo.com.gogo_kan.services.impl.email;

import gogo.com.gogo_kan.services.EmailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
//    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public boolean sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            simpleMailMessage.setFrom("saarock200@gmail.com");
            mailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
