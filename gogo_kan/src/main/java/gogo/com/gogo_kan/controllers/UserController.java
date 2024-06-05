package gogo.com.gogo_kan.controllers;

import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.EmailRequest;
import gogo.com.gogo_kan.dto.request.LoginRequest;
import gogo.com.gogo_kan.dto.request.UpdateEmailRequest;
import gogo.com.gogo_kan.dto.request.UserRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.dto.response.UserResponse;
import gogo.com.gogo_kan.models.User;
import gogo.com.gogo_kan.security.jwt.JwtUtils;
import gogo.com.gogo_kan.services.UserService;
import gogo.com.gogo_kan.services.impl.user.CustomeUserDetailsImpl;
import gogo.com.gogo_kan.utils.OTP;
import gogo.com.gogo_kan.utils.Tokens;
import gogo.com.gogo_kan.utils.UserDetailsCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OTP otpStore;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;
    @Autowired
    private UserDetailsCache userDetailsCache;


    @PostMapping("/register")
    public Object registerUser(@RequestBody UserRequest userRequest) {

        if (userRequest == null) {
            return new ErrorResponse(400, "error", "User details need in a manner");
        }

        User newUser = userRequest.getUser();
        long otp = (long) userRequest.getOtp();

        String stringOtp = String.valueOf(otp).trim();

        if (stringOtp.isEmpty()) {
            return new ErrorResponse(400, "error", "Otp required");
        }

        int stringOtpLen = stringOtp.length();

        if (stringOtpLen > 6) {
            return new ErrorResponse(400, "error", "Invalid Otp");
        }

        if (newUser == null) {
            return new ErrorResponse(400, "error", "User details required");

        }


        if (newUser.getPassword().isEmpty()) {
            return new ErrorResponse(400, "error", "password required");
        }

        if (newUser.getEmail().isEmpty()) {
            return new ErrorResponse(400, "error", "Email required");

        }


        if (newUser.getFullName().isEmpty()) {
            return new ErrorResponse(400, "error", "FullName required");

        }

        // validate the otp first
        // if the otp is correct then saved the user details if not the return the error message
        boolean isCorrectOtp = otpStore.isCorrect(stringOtp);
        if (!isCorrectOtp) {
            return new ErrorResponse(400, "error", "Invalid OTP");
        }
        Long storedOTP = otpStore.getStoredOTP(newUser.getEmail());
        if (storedOTP == null) {
            return new ErrorResponse(400, "error", "Otp Not Found");

        }

        if (storedOTP != otp) {
            return new ErrorResponse(400, "error", "Incorrect  OTP");

        }
        // No sence but also i want to check;
        if (storedOTP == -1) {
            return new ErrorResponse(400, "error", "Invalid OTP");
        }

        // check that userEmail exist at the database or not

        try {
            User isUserExist = userService.isEmailExistIfExitGetData(newUser.getEmail());
            if (isUserExist != null) {
                return new ErrorResponse(400, "error", "Email already exist");
            }


            User user = userService.registerUser(newUser);
            String username = newUser.getEmail().trim(); // Assuming email is the username
            String password = newUser.getPassword().trim();

//            try {
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(username, password)
//                );
//            } catch (Exception e) {
//                return new ErrorResponse(400, "error", e.getMessage());
//            }
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            System.out.println("test2 ########");
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println("test3 ########");
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            System.out.println("test4 ########");
            UserDetails userDetails = customeUserDetails.loadUserByUsername(user.getEmail());
            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            System.out.println("test5 ########");
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails);
            Tokens tokens = new Tokens(accessToken, refreshToken);


            // initiallize the helper user;
            gogo.com.gogo_kan.helpers.User helperUser = new gogo.com.gogo_kan.helpers.User();

            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());

            return new UserResponse("success", 200, "UserRegister successfull", helperUser, tokens);

        } catch (Exception e) {
            return new ErrorResponse(500, "error", e.getMessage());
        }

    }

    @GetMapping("/user")
    public String getUser() {
        return "User";
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            return new ErrorResponse(400, "error", "Login details have to in good manner");
        }
        String email = loginRequest.getEmail();
        if (email.isEmpty()) {
            return new ErrorResponse(400, "error", "Email required");
        }

        String password = loginRequest.getPassword();
        if (password.isEmpty()) {
            return new ErrorResponse(400, "error", "Password Required");

        }

        try {
            // 2 time requesting to the database (NOTE: HAVE T0 WRITE MORE OPTIMIZE CODE)
            UserDetails userDetails = customeUserDetails.loadUserByUsername(email);
            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails);
            Tokens tokens = new Tokens(accessToken, refreshToken);
            User user = userDetailsCache.getUserFromCache(userDetails.getUsername());
            // initiallize the helper user;
            gogo.com.gogo_kan.helpers.User helperUser = new gogo.com.gogo_kan.helpers.User();
            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());
            return new UserResponse("success", 200, "UserSigin successfull", helperUser, tokens);
        } catch (Exception e) {
            return new ErrorResponse(500, "error", e.getMessage());
        }
    }


    @PutMapping("/change-email/{id}")
    public Object updateUserEmail(@PathVariable long id, @RequestBody UpdateEmailRequest updateEmailRequest) {
        if (updateEmailRequest == null) {
            return new ErrorResponse(400, "error", "Email should be provide in the proper manner");
        }
        EmailRequest emailRequest = updateEmailRequest.getEmailRequest();
        if (emailRequest == null) {
            return new ErrorResponse(400, "error", "Email should be provide in the proper manner");
        }
        String newEmail = emailRequest.getEmail();
        // check the email with the regular expression
        if (newEmail.isEmpty()) {
            return new ErrorResponse(400, "error", "New Email Required");
        }

        long otp = updateEmailRequest.getOtp();
        boolean isOtpCorrect = otpStore.isCorrect(String.valueOf(otp));
        if (!isOtpCorrect) {
            return new ErrorResponse(400, "error", "Invalid OTP");
        }

        long storedOtp = otpStore.getStoredOTP(newEmail);
        if (storedOtp != otp) {
            return new ErrorResponse(400, "error", "Invalid OTP");
        }

        try {
            User user = userService.isEmailExistIfExitGetData(newEmail);
            if (user == null) {
                return new ErrorResponse(400, "error", "Email doesnot exist");
            }

            User newUserDetails = userService.updateUserEmail(id, newEmail);
            gogo.com.gogo_kan.helpers.User helperUser = new gogo.com.gogo_kan.helpers.User();
            helperUser.setId(user.getId());
            helperUser.setRole(user.getRole());
            helperUser.setEmail(user.getEmail());
            helperUser.setCreatedAt(user.getCreatedDate());
            helperUser.setLastModifiedDate(user.getLastModifiedDate());
            helperUser.setFullName(user.getFullName());
            return new GlobalSuccessResponse<>(200, "success", "UpdateSuccesFull", helperUser);

        } catch (Exception e) {
            return new ErrorResponse(500, "error", e.getMessage());
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
            return new ErrorResponse(400, "error", "Invalid id");
        }

        if (fullNameRequest == null) {
            return new ErrorResponse(400, "error", "FullName should be in the proper manner.");
        }


        try {
            String newFullName = fullNameRequest.getFullName();
            if (newFullName.isEmpty()) {
                return new ErrorResponse(400, "error", "FullName Requried");
            }
            User newUser = userService.updateUserFullName(id, newFullName);
            gogo.com.gogo_kan.helpers.User helperUser = new gogo.com.gogo_kan.helpers.User();
            helperUser.setId(newUser.getId());
            helperUser.setRole(newUser.getRole());
            helperUser.setEmail(newUser.getEmail());
            helperUser.setCreatedAt(newUser.getCreatedDate());
            helperUser.setLastModifiedDate(newUser.getLastModifiedDate());
            helperUser.setFullName(newUser.getFullName());
            return new GlobalSuccessResponse<>(200, "success", "UpdateSuccesFull", helperUser);
        } catch (Exception e) {
            return new ErrorResponse(500, "error", e.getMessage());

        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Token {
        private String token;
    }
    @PostMapping("/is-token-valid")
    public Object isTokenValid(@RequestBody Token token) {
        System.out.println(token + "this is the token");
        return new GlobalSuccessResponse<>();
    }


}
