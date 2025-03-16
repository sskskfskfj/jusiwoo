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
import semyungai.web.entity.UserEntity;
import semyungai.web.service.UserService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtUtil;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthDto.signUp dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BindingResultErr.getBindingResultErr(bindingResult);
        }
        String encodedPassword = encoder.encode(dto.getPassword());
        System.out.println(encodedPassword);

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .email(dto.getEmail())
                .build();

        ResponseEntity<?> resUser = userService.signUp(user);
        return ResponseEntity.ok(resUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto.login dto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        log.info(dto.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken(dto.getUsername(), "STUDENT");
        log.info(jwt);

        return ResponseEntity.ok().body(jwt);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
