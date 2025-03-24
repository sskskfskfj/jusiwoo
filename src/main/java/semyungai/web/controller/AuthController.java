package semyungai.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import semyungai.web.config.err.BindingResultErr;
import semyungai.web.config.jwt.JwtTokenProvider;
import semyungai.web.dto.AuthDto;
import semyungai.web.entity.Role;
import semyungai.web.entity.UserEntity;
import semyungai.web.service.UserSignUpService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserSignUpService userSignUpService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtUtil;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthDto.signUp dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultErr.getBindingResultErr(bindingResult);
        }
        String encodedPassword = encoder.encode(dto.getPassword());
        log.info("encoded password {}", encodedPassword);

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .name("")
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(Role.TEACHER)
                .build();

        ResponseEntity<?> resUser = userSignUpService.signUp(user);
        return ResponseEntity.ok(resUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto.login dto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        // authenticationManger -> daoAuthenticationProvider UsernamePasswordAuthentication token 전달
        // daoAuthenticationProvider -> customUserDetailsService loadByUserName 존재하는지 확인
        // authentication 으로 customUserDetails 반환

        log.info(dto.getUsername());

        // securityContextHolder 에 authentication 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info(authentication.getAuthorities().toString());

        String jwt = jwtUtil.generateToken(username, authentication.getAuthorities().toString());
        log.info(jwt);

        return ResponseEntity.ok().body(jwt);
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
