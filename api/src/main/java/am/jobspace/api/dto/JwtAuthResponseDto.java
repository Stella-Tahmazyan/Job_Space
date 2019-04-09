package am.jobspace.api.dto;

import am.jobspace.common.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponseDto {

  private String token;
  private User user;

}
