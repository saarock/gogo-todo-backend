package gogo.com.gogo_kan.controller;

import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.EmailRequest;
import gogo.com.gogo_kan.dto.request.LoginRequest;
import gogo.com.gogo_kan.dto.request.UpdateEmailRequest;
import gogo.com.gogo_kan.dto.request.UserRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.dto.response.UserResponse;
import gogo.com.gogo_kan.exception.*;
import gogo.com.gogo_kan.helper.Tokens;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.security.SecurityConfig;
import gogo.com.gogo_kan.security.jwt.JwtUtils;
import gogo.com.gogo_kan.service.UserService;
import gogo.com.gogo_kan.service.impl.user.CustomeUserDetailsImpl;
import gogo.com.gogo_kan.util.OTPUtility;
import gogo.com.gogo_kan.util.UserDetailsCacheUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OTPUtility otpStore;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;
    @Autowired
    private UserDetailsCacheUtility userDetailsCache;
    @Autowired
    private SecurityConfig securityConfig;


    @PostMapping("/register")
    public Object registerUser(@RequestBody UserRequest userRequest) {
        try {
            if (userRequest == null) {
                throw new UserDetailsRequriedException("User details Required!");
            }

            User newUser = userRequest.getUser();
            long otp = (long) userRequest.getOtp();

            String stringOtp = String.valueOf(otp).trim();

            if (stringOtp.isEmpty()) {
                throw new OtpRequiredException("Otp required");
            }

            int stringOtpLen = stringOtp.length();

            if (stringOtpLen > 6) {
                throw new OtpRequiredException("OTP Required");
            }

            if (newUser == null) {
                throw new PasswordRequiredException("User details Required!");
            }

            if (newUser.getPassword().isEmpty()) {
                throw new PasswordRequiredException("Password Required");
            }

            if (newUser.getEmail().isEmpty()) {
                throw new EmailRequiredException("Email is required");
            }

            if (newUser.getFullName().isEmpty()) {
                throw new FullNameRequiredException("FullName required");
            }

            // validate the otp first
            // if the otp is correct then saved the user details if not the throw the error
            boolean isCorrectOtp = otpStore.isCorrect(stringOtp);
            if (!isCorrectOtp) {
                throw new InvalidOtpException("Invalid OTP");
            }
            Long storedOTP = otpStore.getStoredOTP(newUser.getEmail());
            if (storedOTP == null) {
                throw new OtpExpiredException("Opt Expired");
            }

            if (storedOTP != otp) {
                throw new InvalidOtpException("Incorrect OTP");
            }
            // No sence but also i want to check;
            if (storedOTP == -1) {
                throw new InvalidOtpException("Invalid OTP");
            }

            User isUserExist = userService.isEmailExistIfExitGetData(newUser.getEmail());
            if (isUserExist != null) {
                throw new EmailAlreadyExistException("Email already Exist");
            }

            User user = userService.registerUser(newUser);
            UserDetails userDetails = customeUserDetails.loadUserByUsername(user.getEmail());
            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails);
            Tokens tokens = new Tokens(accessToken, refreshToken);
            gogo.com.gogo_kan.helper.User helperUser = new gogo.com.gogo_kan.helper.User();
            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());

            return new UserResponse("success", HttpStatus.CREATED, "UserRegister successfull", helperUser, tokens);

        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        }

    }

    // Testing for the health
    @GetMapping("/user")
    public String getUser() {
        return "User";
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest == null) {
                throw new NullPointerException("Login details Required");
            }
            String email = loginRequest.getEmail();
            if (email.isEmpty()) {
                throw new IllegalArgumentException("Email required");
            }

            String password = loginRequest.getPassword();

            if (password.isEmpty()) {
                throw new IllegalArgumentException("Email required");
            }

            UserDetails userDetails = customeUserDetails.loadUserByUsername(email);
            if (userDetails == null) {
                throw new CredentialNotFoundException("Credentials doesn't match");
            }

            String encodedPassword = userDetails.getPassword();
            boolean isPasswodMatch = securityConfig.isPasswordMatch(password, encodedPassword);

            if (!isPasswodMatch) {
                throw new CredentialNotFoundException("Credentials doesn't match");
            }

            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails);
            Tokens tokens = new Tokens(accessToken, refreshToken);
            User user = userDetailsCache.getUserFromCache(userDetails.getUsername());
            // initialize the helper user;
            gogo.com.gogo_kan.helper.User helperUser = new gogo.com.gogo_kan.helper.User();
            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());
            return new UserResponse("success", HttpStatus.OK, "UserSign successfully", helperUser, tokens);
        } catch (IllegalArgumentException | CredentialNotFoundException | NullPointerException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", e.getMessage());

        }
    }


    @PutMapping("/change-email/{id}")
    public Object updateUserEmail(@PathVariable long id, @RequestBody UpdateEmailRequest updateEmailRequest) {
        if (updateEmailRequest == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Email should be provide in the proper manner");
        }
        EmailRequest emailRequest = updateEmailRequest.getEmailRequest();
        if (emailRequest == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Email should be provide in the proper manner");
        }
        String newEmail = emailRequest.getEmail();
        // check the email with the regular expression
        if (newEmail.isEmpty()) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "New Email Required");
        }

        long otp = updateEmailRequest.getOtp();
        boolean isOtpCorrect = otpStore.isCorrect(String.valueOf(otp));
        if (!isOtpCorrect) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Invalid OTP");
        }

        long storedOtp = otpStore.getStoredOTP(newEmail);
        if (storedOtp != otp) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Invalid OTP");
        }

        try {
            User user = userService.isEmailExistIfExitGetData(newEmail);
            if (user == null) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Email doesnot exist");
            }

            User newUserDetails = userService.updateUserEmail(id, newEmail);
            gogo.com.gogo_kan.helper.User helperUser = new gogo.com.gogo_kan.helper.User();
            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "UpdateSuccesFull", helperUser);

        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", e.getMessage());
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class FullNameRequest {
        private String fullName;
    }

    @PutMapping("/change-fullname/{id}")
    public Object updateUserFullName(@PathVariable long id, @RequestBody FullNameRequest fullNameRequest) {
        try {
            Long.parseLong(String.valueOf(id));
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Invalid id");
        }

        if (fullNameRequest == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "FullName should be in the proper manner.");
        }


        try {
            String newFullName = fullNameRequest.getFullName();
            if (newFullName.isEmpty()) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "FullName Requried");
            }
            User newUser = userService.updateUserFullName(id, newFullName);
            gogo.com.gogo_kan.helper.User helperUser = new gogo.com.gogo_kan.helper.User();
            helperUser.setId(newUser.getId());
            helperUser.setRole(newUser.getRole());
            helperUser.setEmail(newUser.getEmail());
            helperUser.setCreatedAt(newUser.getCreatedDate());
            helperUser.setLastModifiedDate(newUser.getLastModifiedDate());
            helperUser.setFullName(newUser.getFullName());
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "UpdateSuccesFull", helperUser);
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", e.getMessage());
        }
    }


}
