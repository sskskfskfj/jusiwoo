package semyungai.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import semyungai.web.entity.PostEntity;
import semyungai.web.entity.UserEntity;

public class PostDto {

    @Getter
    @Setter
    @Builder
    public static class PostReqDto{

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private UserEntity user;

        public PostEntity toEntity(UserEntity user){
            return PostEntity.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

//    public static class PostResDto{
//        private String
//    }
}
