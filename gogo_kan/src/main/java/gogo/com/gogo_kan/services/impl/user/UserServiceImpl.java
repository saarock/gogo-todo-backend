package gogo.com.gogo_kan.services.impl.user;


import gogo.com.gogo_kan.dto.request.LoginRequest;
import gogo.com.gogo_kan.models.User;
import gogo.com.gogo_kan.repo.UserRepository;
import gogo.com.gogo_kan.security.SecurityConfig;
import gogo.com.gogo_kan.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @Transactional
    @Override
    public User registerUser(User user) throws Exception {
        try {
            user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }

    }

    @Override
    public User login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public User isEmailExistIfExitGetData(String email){
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Error while getting the user when from email");
            return null;
        }
    }

    @Override
    public User updateUserEmail(long id, String newEmail) {
        return userRepository.updateUserEmail(id, newEmail);
    }

    @Override
    public User updateUserFullName(long id, String newFulName) {

        return null;
    }

    @Override
    public User updateUserPassword(String email, String newPassword) {
        return null;
    }
}
