package semyungai.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import semyungai.web.config.err.BindingResultErr;
import semyungai.web.dto.NoticeDto;
import semyungai.web.entity.UserEntity;
import semyungai.web.repository.UserRepository;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final UserRepository userRepository;

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<?> writeNotice(@Valid @RequestBody NoticeDto.NoticeReqDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultErr.getBindingResultErr(bindingResult);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        dto.setUser(userRepository.findByUsername(username));

        dto.toEntity();


        return null;
    }


}
