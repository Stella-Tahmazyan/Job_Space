package am.jobspace.web.controller;


import am.jobspace.common.model.Gender;
import am.jobspace.common.model.User;
import am.jobspace.common.model.UserType;
import am.jobspace.common.repository.UserRepository;
import am.jobspace.common.service.EmailService;
import am.jobspace.web.security.SpringUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class UserController {


  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

//  @Autowired
//  private EmailService emailService;

  @GetMapping("/loginSuccess")
  public String loginSuccess(@AuthenticationPrincipal
      SpringUser springUser, HttpServletRequest request) {
    String url = "http://localhost:8085/user/add";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<User> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<User>() {
      });

   User user = response.getBody();
   if(user!=null){
     return "/";
   }
    return "redirect:/register";

  }

  @GetMapping("/register")
  public String registerForm(ModelMap map) {
    List<User> all = userRepository.findAll();
    map.addAttribute("users", all);
    return "registration";
  }

  @PostMapping("/register")
  public String register(RedirectAttributes redirectAttributes,@ModelAttribute User user,
      @RequestParam("picture") MultipartFile file,BindingResult buldingResult) throws IOException {
    String url = "http://localhost:8085/user/add";
    RestTemplate restTemplate = new RestTemplate();
    RequestEntity<User> response = restTemplate.postForEntity(user);
        url,
        HttpMethod.POST,
        user,
  });

    User user = response.getBody();
    if(user!=null){
      return "/";
    }
    return "redirect:/register";

//    user.setPassword(passwordEncoder.encode(user.getPassword()));
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File picture = new File(imageUploadDir + File.separator + fileName);
    file.transferTo(picture);
    user.setPicUrl(fileName);
    userRepository.save(user);
    redirectAttributes.addFlashAttribute("message", "You are registered successfully!");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");

    return "redirect:/";
  }

  @GetMapping("/add")
  public String addUserView(ModelMap map) {
    userRepository.findAll();
    return "addUser";
  }


  @GetMapping("/getImage")
  public void getImageAsByteArray(HttpServletResponse response,
      @RequestParam("picUrl") String picUrl) throws IOException {
    InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    IOUtils.copy(in, response.getOutputStream());
  }


}
