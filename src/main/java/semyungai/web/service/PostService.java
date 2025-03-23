package semyungai.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import semyungai.web.dto.PostDto;
import semyungai.web.entity.PostEntity;
import semyungai.web.entity.Role;
import semyungai.web.repository.PostRepository;
import semyungai.web.repository.UserRepository;

import java.util.List;


@Slf4j
@Service
@ResponseBody
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllPost(){
        postRepository.flush();
        List<PostEntity> list = postRepository.findAll();
        // todo: 순환 참조 해결
        return ResponseEntity.ok().body(list.getFirst());
    }

    @Transactional
    public ResponseEntity<?> write(PostDto.PostReqDto dto){

        PostEntity postEntity = dto.toEntity(dto.getUser());
        Role role = dto.getUser().getRole();

        log.info(postEntity.getTitle());
        log.info(postEntity.getUser().getUsername());
        log.info(role.getRoles());

        if(role.getRoles().equals("ROLE_STUDENT")){
            return ResponseEntity.badRequest().body("only teacher");
        }else{
            postRepository.save(postEntity);
            return ResponseEntity.ok().body(postEntity.getId());
        }
    }

    @Transactional
    public ResponseEntity<?> modifyPost(PostDto.PostReqDto dto, Long id){

        PostEntity postEntity = dto.toEntity(dto.getUser());
        Role role = postEntity.getUser().getRole();

        if(role.getRoles().equals("ROLE_STUDENT")){
            return ResponseEntity.badRequest().body("only teacher");
        }else{
            if(postRepository.findById(id).isEmpty()){
                return ResponseEntity.badRequest().body("post not exist");
            }else{
                PostEntity updatePost = postRepository.findById(id).orElseThrow();
                updatePost.updatePost(dto.getTitle(), dto.getContent());

                log.info(dto.getTitle());
                log.info(dto.getContent());

                postRepository.save(updatePost);
                return ResponseEntity.ok().body("200");
            }
        }
    }
}