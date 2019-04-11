package am.jobspace.api.rest;


import am.jobspace.common.model.JwtAuthRequestDto;
import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.api.util.JwtTokenUtil;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthEndpoint {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/auth")
  public ResponseEntity auth(@RequestBody JwtAuthRequestDto authRequestDto) {
    String email = authRequestDto.getEmail();
    Optional<User> byEmail = userRepository.findByEmail(email);
    if (byEmail.isPresent()) {
      User user = byEmail.get();
      if (passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword()) || authRequestDto.getPassword().equalsIgnoreCase(user.getPassword())) {
        String token = jwtTokenUtil.generateToken(user.getEmail());
        JwtAuthResponseDto response = JwtAuthResponseDto.builder()
          .token(token)
          .user(user)
          .build();
        return ResponseEntity.ok(response);
      }
    }
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .build();
  }


}
