package am.jobspace.web.controller;

<<<<<<< Updated upstream
import am.jobspace.common.model.Category;
import am.jobspace.common.model.JwtAuthResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
=======

import am.jobspace.common.model.Category;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import am.jobspace.web.security.SpringUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
public class BaseController {
    private CategoryRepositroy categoryRepositroy;

<<<<<<< Updated upstream
  @Value("${server.IP}")
  private String hostName;

  @ModelAttribute("currentUser")
  public JwtAuthResponseDto login(HttpServletRequest request) {
    return (JwtAuthResponseDto) request.getSession().getAttribute("user");
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
=======
    @ModelAttribute("user")
    public Object login(HttpServletRequest request) {
    return request.getSession().getAttribute("user");
     }


//    @ModelAttribute("userik")
//    public User getUser(@AuthenticationPrincipal
//                                SpringUser springUser) {
//        if (springUser != null && springUser.getUser() != null) {
//            return springUser.getUser();
//        }
//
//        return null;
//    }

//    @ModelAttribute("categories")
//    public List<Category> getCategory() {
//        return categoryRepositroy.findAll();
//    }
>>>>>>> Stashed changes
}
