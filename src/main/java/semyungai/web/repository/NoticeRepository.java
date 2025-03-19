package semyungai.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semyungai.web.entity.PostEntity;

public interface NoticeRepository extends JpaRepository<PostEntity, Long> {
}
