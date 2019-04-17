package am.jobspace.web.controller;

import am.jobspace.common.model.Images;
import am.jobspace.common.model.JwtAuthResponseDto;
import am.jobspace.common.model.Post;

import am.jobspace.common.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/post")
public class PostController {

  @Value("${server.IP}")
  private String hostName;

  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @PostMapping("add")
  public String add(@ModelAttribute Post post, HttpServletRequest req, BindingResult bindingResult,
      @RequestParam("picture") MultipartFile[] files) throws IOException {
//    if (bindingResult.hasErrors()) {
//      return "post-ads";
//    }
    String url = hostName + "post/add";
    User user = (User) req.getSession().getAttribute("user");
    if (user == null) {
      return "redirect:/postAds";
    }
    user = new User().builder().id(user.getId()).build();
    post.setPostDate(new Date());
    post.setUser(user);
    for (MultipartFile uploadedFile : files) {
      if (!StringUtils.isEmpty(uploadedFile.getOriginalFilename())) {
        String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        uploadedFile.transferTo(picture);
        post.getImages()
            .add(new Images().builder().picUrl(fileName).uploadDate(new Date()).build());
      }
    }
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<Post> request = new HttpEntity<>(post);
    restTemplate.exchange(
        url,
        HttpMethod.POST,
        request,
        Post.class);

    return "redirect:/";

  }

  @GetMapping("get")
  public String getById(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/get/" + id;

    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Post>() {
        });
    Post post = response.getBody();
    if (post == null) {
      return "redirect:/";
    }

    return "post-detail";
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

    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.PUT,
        null,
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


