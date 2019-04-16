package am.jobspace.api.rest;

import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import am.jobspace.common.repository.PostRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
  public ResponseEntity getById(@PathVariable("id") int id) {
    Optional<Post> allCategory = postRepository.findById(id);
    if (allCategory.isPresent()) {
      return ResponseEntity
          .ok(allCategory.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("add")
  public ResponseEntity add(@RequestBody Post post) {
    postRepository.save(post);
    return ResponseEntity.ok(post);
  }

  @GetMapping("get/all")
  public ResponseEntity getAllPost() {
    List<Post> allCategory = postRepository.findAll();
    return ResponseEntity
        .ok(allCategory);
  }

  @GetMapping("get/category/{id}")
  public ResponseEntity getByCategory(@PathVariable("id") int id) {
    List<Post> allCategory = postRepository.findAllByCategoryId(id);
    return ResponseEntity.ok(allCategory);
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
}
