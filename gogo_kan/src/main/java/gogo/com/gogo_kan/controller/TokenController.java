package gogo.com.gogo_kan.controller;


import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.RefreshTokenRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.dto.response.UserResponse;
import gogo.com.gogo_kan.helper.Tokens;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.security.jwt.JwtUtils;
import gogo.com.gogo_kan.service.UserService;
import gogo.com.gogo_kan.service.impl.user.CustomeUserDetailsImpl;
import gogo.com.gogo_kan.util.UserDetailsCacheUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomeUserDetailsImpl customeUserDetailsImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsCacheUtility userDetailsCache;


    @PostMapping("/generate-new-access-token")
    public Object newAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        if (refreshTokenRequest == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Email already exist");
        }
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        try {
            UserDetails userDetails = customeUserDetailsImpl.loadUserByUsername(username);
            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            return new GlobalSuccessResponse<String>(HttpStatus.OK, "success", "Token created SuccessFull", accessToken);
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", e.getMessage());
        }


    }


    @PostMapping("/is-token-valid")
    public Object isTokenValid() {
        return new GlobalSuccessResponse<>(HttpStatus.ACCEPTED, "success", "Token Valid", null);
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Token {
        private String token;
    }

    @PostMapping("/generate-another-access-token-with-refreshtoken")
    public Object generateNewAccessToken(@RequestBody Token token) {
        if (token == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Token is required");
        }

        String refreshToken = token.getToken();
        if (refreshToken == null) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Token is required");
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        try {
            UserDetails user = customeUserDetailsImpl.loadUserByUsername(username);
            String accessToken = jwtUtils.generateTokenFromUsername(user);
            User userDetailsNew = userDetailsCache.getUserFromCache(username);
//            userDetailsCache.getUserFromCache(username)
            if (userDetailsNew != null) {
                gogo.com.gogo_kan.helper.User helperUser = new gogo.com.gogo_kan.helper.User();
                helperUser.setId(userDetailsNew.getId());
                helperUser.setRole(userDetailsNew.getRole());
                helperUser.setEmail(userDetailsNew.getEmail());
                helperUser.setCreatedAt(userDetailsNew.getCreatedDate());
                helperUser.setLastModifiedDate(userDetailsNew.getLastModifiedDate());
                helperUser.setFullName(userDetailsNew.getFullName());
                Tokens tokens = new Tokens(accessToken, refreshToken);
                return new UserResponse("success", HttpStatus.OK, "Token generated SuccessFull", helperUser, tokens);
            }
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "User should logout");


        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", e.getMessage());
        }


    }


    @PostMapping("get-data-from-access-token")
    public Object sendDataViaAccessToken() {
        return null;
    }
}
