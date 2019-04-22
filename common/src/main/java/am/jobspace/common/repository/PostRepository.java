package am.jobspace.common.repository;

import am.jobspace.common.model.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface PostRepository extends JpaRepository<Post, Integer>   {

  List<Post> findAllByUserId(Integer id);

  List<Post> findAllBySaved(boolean saved);

  List<Post> findAllByCategoryId(Integer id);

  List<Post> findTop15ByOrderByIdDesc();

  Page<Post> findAllByCategoryId(int id, Pageable pageable);


  @Modifying
  @Transactional
  @Query(value = "update post set saved =:saved where id =:id", nativeQuery = true)
  void updateSaved(@Param("id")  int id ,@Param("saved") boolean saved);



}
