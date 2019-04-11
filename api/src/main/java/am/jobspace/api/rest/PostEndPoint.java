package am.jobspace.api.rest;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import am.jobspace.common.repository.PostRepository;
import am.jobspace.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostEndPoint {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CategoryRepositroy categoryRepositroy;


  @GetMapping("get/{id}")
  public ResponseEntity getAllCategory(@PathVariable("id") int id) {
    Optional<Post> allCategory = postRepository.findById(id);
    if (allCategory.isPresent()) {
      return ResponseEntity
          .ok(allCategory.get());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("get/all")
  public ResponseEntity getAllPost() {
    List<Post> allCategory = postRepository.findAll();
    return ResponseEntity
        .ok(allCategory);
  }

  @PutMapping("update")
  public ResponseEntity update(@RequestBody Post post) {
    if (postRepository.findById(post.getId()).isPresent()) {
      postRepository.save(post);
      return ResponseEntity
          .ok(post);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity deleteById(@PathVariable("id") int id) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      postRepository.deleteById(id);
      return ResponseEntity
          .ok()
          .build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("get/category/{id}")
  public ResponseEntity getByCategory(@PathVariable("id") int id) {
    Optional<Category> category = categoryRepositroy.findById(id);
    if (category.isPresent()) {
      List<Post> allCategory = postRepository.findAllByCategory(category.get());
      return ResponseEntity.ok(allCategory);
    }
    return ResponseEntity
        .notFound().build();
  }
}
