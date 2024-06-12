package gogo.com.gogo_kan.service.impl.user;


import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.repo.UserRepository;
import gogo.com.gogo_kan.security.SecurityConfig;
import gogo.com.gogo_kan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

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
    public User isEmailExistIfExitGetData(String email){
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Error while getting the user when from email");
            return null;
        }
    }

    @Transactional
    @Override
    public User updateUserEmail(long id, String newEmail) {
        return userRepository.updateUserEmail(id, newEmail);
    }

    @Transactional
    @Override
    public User updateUserFullName(long id, String newFulName) {

        return null;
    }

    @Transactional
    @Override
    public User updateUserPassword(String email, String newPassword) {
        return null;
    }


    @Override
    public User findById(int id) {
        Optional<User> isFound = this.userRepository.findById(id);
        return isFound.get();
    }
}
