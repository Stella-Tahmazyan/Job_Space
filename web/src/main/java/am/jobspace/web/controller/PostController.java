package am.jobspace.web.controller;

import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/post")
public class PostController {

  @Value("${server.IP}")
  private String hostName;

  @GetMapping("getByCategory")
  public String get(@RequestParam("id") int id , HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/category/" + id;
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt== null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + jwt.getToken());
    HttpEntity<String> reqt = new HttpEntity<String>(headers);
    headers.add("Baeldung-Example-Filter-Header", "Value-Filter");

    ResponseEntity<Post> response = restTemplate.exchange(

        url,
        HttpMethod.GET,
        reqt,
        new ParameterizedTypeReference<Post>() {
        });


    if (response.getStatusCode().equals(ResponseEntity.notFound())) {
      // add condition
      return "";
    }
    Post posts = response.getBody();
    return "";
  }

  @GetMapping("get/all")
  public String getAll() {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "get/category";

    ResponseEntity<List<Post>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Post>>() {
        });
    if (response.getStatusCode().equals(ResponseEntity.notFound())) {
      // add condition
      return "";
    }
    List<Post> posts = response.getBody();
    return "";
  }

}


