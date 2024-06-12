package gogo.com.gogo_kan.service;

import gogo.com.gogo_kan.model.User;

public interface UserService {
    public User registerUser(User user) throws Exception;
    public User isEmailExistIfExitGetData(String email) throws Exception;
    public User updateUserEmail(long id, String newEmail);
    public User updateUserFullName(long id, String newFullName);
    public User updateUserPassword(String email, String newPassword);
    public User findById(int id);
}
