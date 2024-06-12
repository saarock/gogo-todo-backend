package gogo.com.gogo_kan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GogoKanApplication {
    public static void main(String[] args) {
        SpringApplication.run(GogoKanApplication.class, args);
    }

}
