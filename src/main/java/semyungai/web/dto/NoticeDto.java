package semyungai.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import semyungai.web.entity.PostEntity;
import semyungai.web.entity.UserEntity;

public class NoticeDto {

    @Getter
    @Setter
    @Builder
    public static class NoticeReqDto{
        private String title;
        private String content;
        private UserEntity user;

        public PostEntity toEntity(){
            return PostEntity.builder()
                    .title(title)
                    .content(content)
                    .user(null)
                    .build();
        }
    }
}
