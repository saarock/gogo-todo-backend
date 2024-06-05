package gogo.com.gogo_kan.controllers;


import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.RefreshTokenRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.security.jwt.JwtUtils;
import gogo.com.gogo_kan.services.impl.user.CustomeUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/generate-new-access-token")
    public Object newAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        if (refreshTokenRequest == null) {
            return new ErrorResponse(400, "error", "Email already exist");
        }
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);

        try {
            UserDetails userDetails = customeUserDetailsImpl.loadUserByUsername(username);
            String accessToken = jwtUtils.generateTokenFromUsername(userDetails);
            return new GlobalSuccessResponse<String>(200, "success", "Token created SuccessFull", accessToken);
        } catch (Exception e) {
            return new ErrorResponse(400, "error", e.getMessage());
        }




    }
}
