package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import am.jobspace.common.repository.CategoryRepositroy;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@ControllerAdvice
public class BaseController {


  private CategoryRepositroy categoryRepositroy;

  private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


  @Value("${server.IP}")
  private String hostName;

  @ModelAttribute("currentUser")
  public User login(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    if (user != null) {

      user.setActiveDate(new Date());
      RestTemplate restTemplate = new RestTemplate();
      String url = hostName + "/user/update";
      HttpEntity<User> reqt = new HttpEntity<>(user);
      ResponseEntity<User> response = restTemplate.exchange(
          url,
          HttpMethod.PUT,
          reqt,
          new ParameterizedTypeReference<User>() {
          });
      user = response.getBody();
    }
    return user;
  }

  @ModelAttribute("categories")
  public List<Category> getCategory() {
    String url = hostName + "category/get/all";
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<Category>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Category>>() {
        });
    List<Category> categories = response.getBody();

    return categories;

  }


}
