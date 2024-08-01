package gogo.com.gogo_kan.helper;

import gogo.com.gogo_kan.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String fullName;
    private long id;
    private String email;
    private Role role;
    private Instant createdAt;
    private Instant lastModifiedDate;
    private String userGithubUserName;

}