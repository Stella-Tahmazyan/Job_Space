package am.jobspace.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.social.facebook.api.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
