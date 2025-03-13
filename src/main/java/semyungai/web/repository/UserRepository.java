package semyungai.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import semyungai.web.entity.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    User findByUsername(String username);
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);
}
