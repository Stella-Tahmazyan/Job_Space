package am.jobspace.common.repository;


import am.jobspace.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepositroy extends JpaRepository<Category,Integer> {



}
