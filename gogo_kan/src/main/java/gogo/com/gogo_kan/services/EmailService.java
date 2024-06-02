package gogo.com.gogo_kan.services;


import java.io.File;

public interface EmailService {

    public boolean sendEmail(String to, String subject, String message);


}
