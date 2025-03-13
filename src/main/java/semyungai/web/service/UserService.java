package semyungai.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import semyungai.web.dto.AuthDto;
import semyungai.web.entity.UserEntity;
import semyungai.web.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> signUp(UserEntity user){
        System.out.println(user.getRole());
        Optional<UserEntity> existingUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        if(existingUser.isPresent()){
            return ResponseEntity.status(400).body("동일한 유저가 존재합니다");
        }
        userRepository.save(user);

        return ResponseEntity.ok(new AuthDto.resDto(user.getUsername(), user.getEmail()));
    }
}
