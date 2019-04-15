package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;
import am.jobspace.web.component.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

  @Value("${server.IP}")
  private String hostName;

  @Autowired
  private ApiUtil apiUtil;

  @GetMapping("get/all")
  public String getAll() {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "get/all";
    ResponseEntity<List<Category>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Category>>() {
        });
    if (response.getStatusCode().equals(ResponseEntity.notFound())) {
      // add condition
      return "";
    }
    List<Category> categories = response.getBody();
    return "";
  }

  @GetMapping("get")
  public String getAll(@RequestParam("id") int id) {
    RestTemplate restTemplate = new RestTemplate();

    String url = hostName + "get/"+ id;

    ResponseEntity<Category> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Category>() {
        });
    if (response.getStatusCode().equals(ResponseEntity.notFound())) {
      // add condition
      return "";
    }
    Category category = response.getBody();
    return "";
  }

  @GetMapping("update")
  public String update(@ModelAttribute("category") Category category, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "category/update";
    JwtAuthResponseDto jwt = (JwtAuthResponseDto) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpHeaders headers = new HttpHeaders();
    apiUtil.setHeader(jwt.getToken(), headers);
    HttpEntity<Category> reqt = new HttpEntity<>(category, headers);
    ResponseEntity<Category> response = restTemplate.exchange(
        url,
        HttpMethod.PUT,
        reqt,
        new ParameterizedTypeReference<Category>() {
        });
     category = response.getBody();
    return "redirect:/";
  }

  @GetMapping("delete")
  public String deleteById(@RequestParam("id") int id,HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "category/delete/" + id;
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
