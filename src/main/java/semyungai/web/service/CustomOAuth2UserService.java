package semyungai.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import semyungai.web.dto.AuthDto;
import semyungai.web.dto.CustomOAuth2User;
import semyungai.web.dto.OAuth2Response;
import semyungai.web.entity.UserEntity;
import semyungai.web.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(String.valueOf(oAuth2User));

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("google")){
            oAuth2Response = new AuthDto.GoogleResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        UserEntity existUser = userRepository.findByUsername(username);

        if(existUser == null){
            UserEntity user = UserEntity.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .password("")
                    .build();

            userRepository.save(user);

            AuthDto.UserDto userDto = new AuthDto.UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole("ROLE_STUDENT");

            return new CustomOAuth2User(userDto);
        }else{
            log.info(existUser.getEmail());
            log.info(existUser.getName());
            log.info(existUser.getUsername());

            existUser.update(oAuth2Response.getName(), oAuth2Response.getEmail());
            userRepository.save(existUser);

            AuthDto.UserDto userDto = new AuthDto.UserDto();
            userDto.setUsername(existUser.getUsername());
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(String.valueOf(existUser.getRole()));

            return new CustomOAuth2User(userDto);
        }

    }
}
