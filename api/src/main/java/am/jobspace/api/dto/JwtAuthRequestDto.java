package am.jobspace.api.dto;

import lombok.Data;

@Data
public class JwtAuthRequestDto {

  private String email;
  private String password;

}
