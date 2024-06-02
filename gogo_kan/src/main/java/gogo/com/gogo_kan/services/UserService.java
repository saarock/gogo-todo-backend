package gogo.com.gogo_kan.services;

import gogo.com.gogo_kan.models.User;

public interface UserService {
    public User registerUser(User user);
    public User login(LoginWrapper loginWrapper);
    public User isEmailExistIfExitGetData(String email);
    public User updateUserEmail(int id, String newEmail);
    public User updateUserFullName(String newFullName, String email);
    public User updateUserPassword(String email, String newPassword);
}
