package am.jobspace.common.repository;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Integer>{
  List<Post> findAllByUserId(Integer id);
  List<Post> findAllByCategoryId(Integer id);

}
