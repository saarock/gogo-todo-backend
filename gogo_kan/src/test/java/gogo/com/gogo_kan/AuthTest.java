package gogo.com.gogo_kan;

import gogo.com.gogo_kan.enums.Role;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.service.UserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AuthTest {

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void register() throws Exception {
        // Create a new user instance
        User user = new User();
        user.setEmail("b@gmail.com");
        user.setFullName("Gogo User");
        user.setPassword("123456");
//        user.setRole(Role.user); // Assuming Role is an enum, set it appropriately

        // Register the user
        userService.registerUser(user);
    }

}
