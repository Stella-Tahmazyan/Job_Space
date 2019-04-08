package am.jobspace.common.repository;


import am.jobspace.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositroy extends JpaRepository<Category,Integer> {

}
