package semyungai.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import semyungai.web.entity.UserEntity;

import java.util.Map;

public class AuthDto {
    @Getter
    @Setter
    @Builder
    public static class login{
        @NotBlank
        private String username;

        @NotBlank
        private String password;

    }
    @Getter
    @Setter
    @Builder
    public static class signUp{
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        @NotBlank
        private String email;

        public UserEntity toEntity(){
            return UserEntity.builder()
                    .email(this.email)
                    .username(this.username)
                    .password(this.password)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class resDto{
        private String username;
        private String email;

        public resDto(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }

    public static class GoogleResponse implements OAuth2Response{

        private final Map<String, Object> attribute;

        public GoogleResponse(Map<String, Object> attribute){
            this.attribute = attribute;
        }

        @Override
        public String getProvider() {
            return "google";
        }

        @Override
        public String getProviderId() {
            return attribute.get("sub").toString();
        }

        @Override
        public String getEmail() {
            return attribute.get("email").toString();
        }

        @Override
        public String getName() {
            return attribute.get("name").toString();
        }
    }

    @Getter
    @Setter
    public static class UserDto{
        private String username;
        private String name;
        private String role;
    }
}
