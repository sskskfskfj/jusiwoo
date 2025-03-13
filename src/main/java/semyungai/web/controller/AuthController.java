package semyungai.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import semyungai.web.dto.AuthDto;
import semyungai.web.entity.UserEntity;
import semyungai.web.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @ResponseBody
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthDto.signUp dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> error = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(error);
        }

        String encodedPassword = encoder.encode(dto.getPassword());
        System.out.println(encodedPassword);

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .email(dto.getEmail())
                .build();

        ResponseEntity<?> resUser = userService.signUp(user);
        System.out.println(user.getRole());
        return ResponseEntity.ok(resUser);
    }

    public ResponseEntity<?> login(@Valid @RequestBody AuthDto.login dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }

        return null;
    }
}
