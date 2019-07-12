package am.jobspace.api.rest;

import am.jobspace.common.model.JwtAuthRequestDto;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserEndpoint {


  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @Autowired
  private UserRepository userRepository;


  @PostMapping("/user/add")
  @ApiOperation(value = "Create User", response = User.class)
  @ApiResponses({
      @ApiResponse(code = 409, message = "User with email already exists"),
      @ApiResponse(code = 200, message = "User created")
  })
  public ResponseEntity add(@RequestBody User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .build();
    }
    userRepository.save(user);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/auth")
  public ResponseEntity auth(@RequestBody JwtAuthRequestDto authRequestDto) {
    String email = authRequestDto.getEmail();
    Optional<User> byEmail = userRepository.findByEmail(email);
    if (byEmail.isPresent()) {
      User user = byEmail.get();
      return ResponseEntity.ok(user);
    }
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .build();
  }

  @PutMapping("/user/update")
  public ResponseEntity update(@RequestBody User user) {
    if (userRepository.findById(user.getId()).isPresent()) {
      userRepository.save(user);
      return ResponseEntity
          .ok(user);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/users")
  public ResponseEntity getAll() {
    return ResponseEntity.ok(userRepository.findAll());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity getById(@PathVariable("id") int id) {
    Optional<User> byId = userRepository.findById(id);
    if (byId.isPresent()) {
      return ResponseEntity.ok(byId.get());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity deleteById(@PathVariable("id") int id) {
    Optional<User> byId = userRepository.findById(id);
    if (byId.isPresent()) {
      userRepository.deleteById(id);
      return ResponseEntity
          .ok()
          .build();
    }
    return ResponseEntity.notFound().build();
  }

}
