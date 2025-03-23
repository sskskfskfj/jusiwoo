package semyungai.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import semyungai.web.config.err.BindingResultErr;
import semyungai.web.dto.AuthDto;
import semyungai.web.dto.PostDto;
import semyungai.web.entity.Role;
import semyungai.web.repository.UserRepository;
import semyungai.web.service.PostService;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<?> getNotice(){
        return postService.getAllPost();
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeNotice(@Valid @RequestBody PostDto.PostReqDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultErr.getBindingResultErr(bindingResult);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        dto.setUser(userRepository.findByUsername(username));

        log.info(dto.getUser().getUsername());
        return postService.write(dto);
    }

    @PutMapping("/modify/{postId}")
    public ResponseEntity<?> modifyNotice(@Valid @RequestBody PostDto.PostReqDto dto,
                                          @PathVariable Long postId,
                                          BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return BindingResultErr.getBindingResultErr(bindingResult);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        dto.setUser(userRepository.findByUsername(username));
        return postService.modifyPost(dto, postId);
    }



}
