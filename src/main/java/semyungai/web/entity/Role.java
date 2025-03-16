package semyungai.web.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum Role {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER"),
    ADMIN("ROLE_ADMIN");

    private final String roles;

    public static String getRoles(String role){
        return Role.valueOf(role).getRoles();
    }
}
