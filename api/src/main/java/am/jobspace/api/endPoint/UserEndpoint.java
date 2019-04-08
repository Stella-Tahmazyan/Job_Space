package am.jobspace.api.endPoint;


import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserEndpoint {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @PostMapping("/user/add")
//  @ApiOperation(value = "Create User", response = User.class)
//  @ApiResponses({
//    @ApiResponse(code = 409, message = "User with email already exists"),
//    @ApiResponse(code = 200, message = "User created")
//  })
//  public ResponseEntity add(@RequestBody User user) {
//    if (userRepository.findByEmail(user.getEmail()) != null) {
//      return ResponseEntity
//        .status(HttpStatus.CONFLICT)
//        .build();
//    }
//    userRepository.save(user);
//    return ResponseEntity.ok(user);
//  }
//
//
//  @PutMapping("/user/update")
//  public ResponseEntity update(@RequestBody User user) {
//    if (userRepository.findById(user.getId()).isPresent()) {
//      userRepository.save(user);
//      return ResponseEntity
//        .ok(user);
//    }
//    return ResponseEntity.notFound().build();
//  }
//
//  @GetMapping("/users")
//  public ResponseEntity getAll() {
//    return ResponseEntity.ok(userRepository.findAll());
//  }
//
//  @GetMapping("/users/{id}")
//  public ResponseEntity getById(@PathVariable("id") int id) {
//    Optional<User> byId = userRepository.findById(id);
//    if (byId.isPresent()) {
//      return ResponseEntity.ok(byId.get());
//    }
//    return ResponseEntity.notFound().build();
//  }
//
//  @DeleteMapping("/users/{id}")
//  public ResponseEntity deleteById(@PathVariable("id") int id) {
//    Optional<User> byId = userRepository.findById(id);
//    if (byId.isPresent()) {
//      userRepository.deleteById(id);
//      return ResponseEntity
//        .ok()
//        .build();
//    }
//    return ResponseEntity.notFound().build();
//  }

}
