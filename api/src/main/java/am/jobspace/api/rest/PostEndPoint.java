package am.jobspace.api.rest;

import am.jobspace.common.model.Post;
import am.jobspace.common.repository.CategoryRepositroy;
import am.jobspace.common.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostEndPoint {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CategoryRepositroy categoryRepositroy;


  @GetMapping("get/{id}")
  public ResponseEntity getById(@PathVariable("id") int id) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      return ResponseEntity
          .ok(post.get());
    }
    return ResponseEntity.notFound().build();
  }

//  @GetMapping("getSaved")
//  public ResponseEntity getBySaved() {
//    List<Post> posts = postRepository.findAllBySaved(true);
//    return ResponseEntity
//        .ok(posts);
//  }

  @PostMapping("add")
  public ResponseEntity add(@RequestBody Post post) {
    postRepository.save(post);
    return ResponseEntity.ok(post);
  }

  @GetMapping("get/all")
  public ResponseEntity getAllPost() {
    Iterable<Post> allCategory = postRepository.findAll();
    return ResponseEntity
        .ok(allCategory);
  }

  @GetMapping("get/view")
  public ResponseEntity getAllByView() {
    List<Post> allCategory = postRepository.findTop10ByOrderByViewDesc();
    return ResponseEntity
        .ok(allCategory);
  }

  @GetMapping("pagable/{id}/{page}/{size}")
  public ResponseEntity getAllPagable(@PathVariable("id") int id, @PathVariable("page")
      int currentPage, @PathVariable("size") int size) {
    Pageable firstPageWithTwoElements = PageRequest.of(currentPage - 1, size);
    Page<Post> allCategory = postRepository.findAllByCategoryId(id, firstPageWithTwoElements);
    return ResponseEntity
        .ok(allCategory);
  }

  @GetMapping("get/category/{id}")
  public ResponseEntity getByCategory(@PathVariable("id") int id) {
    List<Post> allCategory = postRepository.findAllByCategoryId(id);
    return ResponseEntity.ok(allCategory);
  }
//
//  @GetMapping("get/user/{id}")
//  public ResponseEntity getByUser(@PathVariable("id") int id) {
//    List<Post> allCategory = postRepository.findAllByUserId(id);
//    return ResponseEntity.ok(allCategory);
//  }

  @PutMapping("update")
  public ResponseEntity update(@RequestBody Post post) {
    if (postRepository.findById(post.getId()).isPresent()) {
      postRepository.save(post);
      return ResponseEntity
          .ok(post);
    }
    return ResponseEntity.notFound().build();
  }


  @GetMapping("update/saved/{id}/{isSaved}")
  public ResponseEntity updateSaved(@PathVariable("id") int id,
      @PathVariable("isSaved") boolean saved) {
    try {
      postRepository.updateSaved(id, saved);
      Optional<Post> post = postRepository.findById(id);
      if (post.isPresent()) {
        return ResponseEntity
            .ok(post);
      }
    } catch (Exception e) {
      log.error("Error:" + e);
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
