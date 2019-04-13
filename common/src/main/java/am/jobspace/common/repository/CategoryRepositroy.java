package am.jobspace.common.repository;


import am.jobspace.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepositroy extends JpaRepository<Category,Integer> {

   // List<Category> findAllByLocale(String locale);
    @Modifying
    @Query(value = "SELECT * FROM `category` WHERE locale=?1",
            nativeQuery = true)
    List<Category> findAllByLocale(@Param("lang") String locale);

}
