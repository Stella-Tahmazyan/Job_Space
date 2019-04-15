package am.jobspace.web.controller;

import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;
import am.jobspace.web.component.ApiUtil;
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

  @Autowired
  private ApiUtil apiUtil;

  @GetMapping("get")
  public String getById(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/" + id;
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    apiUtil.setHeader(jwt.getToken(), headers);
    HttpEntity<String> reqt = new HttpEntity<>(headers);
    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        reqt,
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
  public String get(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/category/" + id;
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    apiUtil.setHeader(jwt.getToken(), headers);
    HttpEntity<String> reqt = new HttpEntity<>(headers);
    ResponseEntity<List<Post>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        reqt,
        new ParameterizedTypeReference<List<Post>>() {
        });
    List<Post> posts = response.getBody();

    return "redirect:/";
  }

  @GetMapping("update")
  public String update(@ModelAttribute("post") Post post, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/update";
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    apiUtil.setHeader(jwt.getToken(), headers);
    HttpEntity<Post> reqt = new HttpEntity<>(post, headers);
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
  public String deleteById(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/delete/" + id;
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    apiUtil.setHeader(jwt.getToken(), headers);
    HttpEntity<String> req = new HttpEntity<>(headers);
    ResponseEntity response = restTemplate.exchange(
        url,
        HttpMethod.DELETE,
        req,
        String.class
    );
    response.getStatusCode();
    return "";
  }
}


