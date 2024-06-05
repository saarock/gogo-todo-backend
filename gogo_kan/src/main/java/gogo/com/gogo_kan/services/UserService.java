package gogo.com.gogo_kan.services;

import gogo.com.gogo_kan.models.User;
import gogo.com.gogo_kan.dto.request.LoginRequest;

public interface UserService {
    public User registerUser(User user) throws Exception;
    public User login(LoginRequest loginRequest);
    public User isEmailExistIfExitGetData(String email) throws Exception;
    public User updateUserEmail(long id, String newEmail);
    public User updateUserFullName(long id, String newFullName);
    public User updateUserPassword(String email, String newPassword);
}
