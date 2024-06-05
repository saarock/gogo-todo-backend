package gogo.com.gogo_kan.dto.response;


import gogo.com.gogo_kan.helpers.User;
import gogo.com.gogo_kan.utils.Tokens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Stack;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String type;
    private Integer status;
    private String message;
    private User user;
    private Tokens tokens;
}
