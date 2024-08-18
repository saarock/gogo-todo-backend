package gogo.com.gogo_kan;


import gogo.com.gogo_kan.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailTesting {


    @Autowired
    private EmailService emailService;

    @Test
    public void testEmail() {
        this.emailService.sendEmail("rathorprashanna@gmail.com", "test", "Test pass");
    }

}
