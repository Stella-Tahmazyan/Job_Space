package am.jobspace.common.repository;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findAllByUserId(Integer id);

  List<Post> findAllByCategoryId(Integer id);

  @Modifying
  @Transactional
  @Query(value = "update post set is_saved =:isSaved where id =:id", nativeQuery = true)
  void updateSaved(@Param("id")  int id ,@Param("isSaved") boolean isSaved);



}
