package semyungai.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semyungai.web.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
