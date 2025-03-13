package semyungai.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import semyungai.web.entity.UserEntity;

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
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
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
}
