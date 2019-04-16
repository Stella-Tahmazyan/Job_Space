package am.jobspace.web.controller;

import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/post")
public class PostController {

  @Value("${server.IP}")
  private String hostName;


  @GetMapping("get")
  public String getById(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/" + id;
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Post>() {
        });
    Post post = response.getBody();

    return "redirect:/";
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

  @GetMapping("getByCategory")
  public String get(@RequestParam("id") int id) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/category/" + id;

    ResponseEntity<List<Post>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Post>>() {
        });
    List<Post> posts = response.getBody();

    return "redirect:/";
  }

  @GetMapping("update")
  public String update(@ModelAttribute("post") Post post) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/update";

    HttpEntity<Post> reqt = new HttpEntity<>(post);
    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.PUT,
        reqt,
        new ParameterizedTypeReference<Post>() {
        });
    post = response.getBody();
    return "redirect:/";
  }

  @GetMapping("delete")
  public String deleteById(@RequestParam("id") int id) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/delete/" + id;

    ResponseEntity response = restTemplate.exchange(
        url,
        HttpMethod.DELETE,
        null,
        String.class
    );
    response.getStatusCode();
    return "";
  }
}


