package gogo.com.gogo_kan.controller;

import gogo.com.gogo_kan.dto.request.EmailRequest;
import gogo.com.gogo_kan.dto.response.EmailResponse;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.exception.UserNotFoundException;
import gogo.com.gogo_kan.exception.UserServiceException;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.security.SecurityConfig;
import gogo.com.gogo_kan.service.UserService;
import gogo.com.gogo_kan.service.impl.email.EmailServiceImpl;
import gogo.com.gogo_kan.util.OTPUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class MailController {
    private final SecurityConfig securityConfig;
    private EmailServiceImpl emailService;
    private OTPUtility otp;
    private UserService userService;

    @PostMapping("/send-mail")
    public Object sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            if (emailRequest == null) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "email require JSON format");
            }
            String userEmail = emailRequest.getEmail();
            if (userEmail.isEmpty()) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "email is required");
            }

            User isUserExist = userService.isEmailExistIfExitGetData(userEmail);
            if (isUserExist != null) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Email already exist");
            }

            long newOtp = otp.generateOTP();
            boolean isOtpStored = otp.storeOTP(userEmail, newOtp);
            if (isOtpStored) {
                boolean isEmailSent = emailService.sendEmail(userEmail, "GOGO OTP", "This is your OTP: " + newOtp + " Pleased get your OTP within 1 minute other wise OTP will get expired.");
                if (!isEmailSent) {
                    otp.removeOTP(userEmail);
                    return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "email is invalid");
                }
            }
            // if all right send the success message
            return new EmailResponse(HttpStatus.OK, "success", "OTP send successfully ", userEmail);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "############");
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public Object resetPassword(@RequestBody EmailRequest emailRequest) {
        if (emailRequest == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "email require JSON format");
        }
        String userEmail = emailRequest.getEmail();
        if (userEmail.isEmpty()) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "email is required");
        }

        try {
            User user = this.userService.isEmailExistIfExitGetData(emailRequest.getEmail());
            if (user != null) {
                String newPassword = UUID.randomUUID().toString();
                user.setPassword(newPassword);
                User newSavedUser = userService.registerUser(user);
                System.out.println(newSavedUser + "this is ajkdsfgsadj &&&&&& ");
                if (newSavedUser == null) {
                    throw new UserServiceException("Some thing wrong try again");
                } else {
                    emailService.sendEmail(newSavedUser.getEmail(), "GoGo", "This is your new Password pleased don't share with anyone     " + newPassword + "  Please login and change your password.");
                    return new EmailResponse(HttpStatus.OK, "success", "password send successfully ", user.getEmail());
                }

            }

        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        }
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Some thing wrong try again");

    }


}

