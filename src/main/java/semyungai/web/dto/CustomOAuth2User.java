package semyungai.web.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final AuthDto.UserDto userDto;

    public CustomOAuth2User(AuthDto.UserDto dto){
        this.userDto = dto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(userDto.getRole()));

        return collection;
    }

    @Override
    public String getName() {
        return userDto.getName();
    }

    public String getUsername(){
        return userDto.getUsername();
    }
}
