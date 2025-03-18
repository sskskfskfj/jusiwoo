package semyungai.web.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import semyungai.web.dto.CustomUserDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // 1️⃣ OAuth 로그인 여부 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = oauthToken.getPrincipal();

            String username = oAuth2User.getAttribute("email");
            String role = "STUDENT"; // 기본 ROLE_USER 부여

            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

            UserDetails userDetails = CustomUserDetails.builder()
                    .username(username)
                    .password("") // OAuth 로그인은 비밀번호 없음
                    .authorities(authorities)
                    .build();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ 일반 JWT 인증 처리
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        if (jwt.validateToken(token)) {
            String username = jwt.extractUsername(token);
            String role = jwt.extractRole(token);
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

            UserDetails userDetails = CustomUserDetails.builder()
                    .username(username)
                    .password("") // JWT 기반 인증이므로 비밀번호 필요 없음
                    .authorities(authorities)
                    .build();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
