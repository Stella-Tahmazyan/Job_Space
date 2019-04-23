package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import am.jobspace.common.repository.PostRepository;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

  @Autowired
   private PasswordEncoder passwordEncoder;

  @Value("${server.IP}")
  private String hostName;

  @Value("${image.upload.dir}")
  private String imageUploadDir;
  @Autowired
  private CategoryRepositroy categoryRepositroy;
  @Autowired
  private PostRepository postRepository;
  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/")
  public String main(ModelMap map) {
    List<Post> posts=postRepository.findTop15ByOrderByIdDesc();
    map.addAttribute("allAds",posts);
    return "index";
  }

  @GetMapping("/register")
  public ModelAndView registerForm(ModelAndView modelAndView) {
    modelAndView.addObject("user", new User());
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  @GetMapping("postDetail")
  public ModelAndView postDetail(ModelAndView modelAndView) {

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
