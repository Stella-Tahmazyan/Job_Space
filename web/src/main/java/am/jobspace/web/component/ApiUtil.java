package am.jobspace.web.component;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApiUtil {

  public void setHeader(String token, HttpHeaders headers) {
    headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
    headers.set("Authorization", "Bearer " + token);
    headers.setContentType(MediaType.APPLICATION_JSON);
  }
}
