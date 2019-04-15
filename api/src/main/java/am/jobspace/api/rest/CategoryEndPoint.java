package am.jobspace.api.rest;

import am.jobspace.common.model.Category;
import am.jobspace.common.repository.CategoryRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryEndPoint {

  @Autowired
  private CategoryRepositroy categoryRepositroy;

  @GetMapping("get/all")
  public ResponseEntity getAllCategory() {
    List<Category> allCategory = categoryRepositroy.findAll();
    return ResponseEntity
        .ok(allCategory);
  }

  @GetMapping("get/{id}")
  public ResponseEntity getPostByUser(@PathVariable("id") int id) {
    Optional<Category> allCategory = categoryRepositroy.findById(id);
    if (allCategory.isPresent()) {
      return ResponseEntity
          .ok(allCategory);
    }
    return ResponseEntity.noContent().build();
  }

  @PutMapping("update")
  public ResponseEntity update(@RequestBody Category category) {
    if (categoryRepositroy.findById(category.getId()).isPresent()) {
      categoryRepositroy.save(category);
      return ResponseEntity
          .ok(category);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity deleteById(@PathVariable("id") int id) {
    Optional<Category> category = categoryRepositroy.findById(id);
    if (category.isPresent()) {
      categoryRepositroy.deleteById(id);
      return ResponseEntity
          .ok()
          .build();
    }
    return ResponseEntity.notFound().build();
  }
}