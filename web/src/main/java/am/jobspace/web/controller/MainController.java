package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.Country;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

  @Value("${image.upload.dir}")
  private String imageUploadDir;
  @Autowired
  private CategoryRepositroy categoryRepositroy;

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/")
  public String main() {
    return "index";
  }

  @GetMapping("/register")
  public ModelAndView registerForm(ModelAndView modelAndView) {
    modelAndView.addObject("user", new User());
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  @GetMapping("postDetail")
  public ModelAndView postDetail(@RequestParam("id") int id, ModelAndView modelAndView) {
    modelAndView.setViewName("post-detail");
    return modelAndView;
  }

  @GetMapping("postAds")
  public ModelAndView postAds(ModelAndView modelAndView) {
    modelAndView.setViewName("post-ads");
    return modelAndView;
  }

  @GetMapping("/worker")
  public String workerPage(ModelMap map) {
    List<Category> cat = categoryRepositroy.findAll();
    map.addAttribute("categories", cat);
    return "worker";
  }

  @GetMapping("/employer")
  public String employerPage(ModelMap map) {
    List<Category> cat = categoryRepositroy.findAll();
    map.addAttribute("categories", cat);
    return "employer";
  }
}
