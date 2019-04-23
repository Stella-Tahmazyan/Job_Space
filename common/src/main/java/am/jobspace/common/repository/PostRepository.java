package am.jobspace.common.repository;

import am.jobspace.common.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public interface PostRepository extends JpaRepository<Post, Integer> {

  Page<Post> findAllByUserId(Integer id,Pageable pageable);


  List<Post> findAllByCategoryId(Integer id);

  List<Post> findTop15ByOrderByIdDesc();

  Page<Post> findAllByCategoryId(int id, Pageable pageable);
  Page<Post> findAllBySavedAndUserId(boolean saved, int id,Pageable pageable);

  List<Post> findTop10ByOrderByViewDesc();


  @Modifying
  @Transactional
  @Query(value = "update post set saved =:saved where id =:id", nativeQuery = true)
  void updateSaved(@Param("id") int id, @Param("saved") boolean saved);


}
