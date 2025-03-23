package semyungai.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semyungai.web.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByTitle(String title);
}
