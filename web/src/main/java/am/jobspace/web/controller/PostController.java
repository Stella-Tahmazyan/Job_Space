package am.jobspace.web.controller;

import am.jobspace.common.model.Post;

import am.jobspace.common.model.User;
import am.jobspace.common.repository.PostRepository;
import am.jobspace.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
//@RequestMapping("/post")
public class PostController {

  @Value("${server.IP}")
  private String hostName;

  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("post/add")
  public String add(@ModelAttribute Post post, HttpServletRequest req, BindingResult bindingResult,
      @RequestParam("picture") MultipartFile file) throws IOException {
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
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File picture = new File(imageUploadDir + File.separator + fileName);
    file.transferTo(picture);
    user.setPicUrl(fileName);

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<Post> request = new HttpEntity<>(post);
    restTemplate.exchange(
        url,
        HttpMethod.POST,
        request,
        Post.class);

    return "redirect:/";

  }

  @GetMapping("/getPost")
  public String getById(@RequestParam("id") int id, RedirectAttributes redirectAttributes,
      ModelMap modelMap) {
    Optional<Post> post = postRepository.findById(id);
    if (post.isPresent()) {
      modelMap.addAttribute("post", post.get());
    }

    return "post-detail";
  }
  @GetMapping("/getAllPostByUser")
  public String getPostByUserId(@RequestParam("id") int id,  @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size, ModelMap map) {

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(4);
    Page<Post> posts = postRepository
        .findAllBySavedAndUserId(true, id, PageRequest.of(currentPage - 1, pageSize));

    map.addAttribute("posts", posts);
    int totalPages = posts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      map.addAttribute("pageNumbers", pageNumbers);

    }

    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      map.addAttribute("user", user.get());
    }

    return "userPosts";
  }

  @GetMapping("getAllPostBySaved")
  public String getAllPostBySaved(ModelMap map, @RequestParam("id") int id,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(4);
    Page<Post> posts = postRepository
        .findAllBySavedAndUserId(true, id, PageRequest.of(currentPage - 1, pageSize));

    map.addAttribute("posts", posts);
    int totalPages = posts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      map.addAttribute("pageNumbers", pageNumbers);

    }
    return "postsSaved";
  }


  @GetMapping("getAllPost")
  public String getAll(ModelMap map,@RequestParam("page") Optional<Integer> page,
    @RequestParam("size") Optional<Integer> size) {

      int currentPage = page.orElse(1);
      int pageSize = size.orElse(4);
      Page<Post> posts = postRepository
          .findAll(PageRequest.of(currentPage - 1, pageSize));

      map.addAttribute("posts", posts);
      int totalPages = posts.getTotalPages();
      if (totalPages > 0) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
            .boxed()
            .collect(Collectors.toList());
        map.addAttribute("pageNumbers", pageNumbers);

      }

    return "allAds";
  }


  @GetMapping("getPostByUser")
  public String getAllByUser(@RequestParam("id") int id, ModelMap map,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(4);

    Page<Post> posts = postRepository
        .findAllByUserId(id, PageRequest.of(currentPage - 1, pageSize));
    map.addAttribute("posts", posts);
    int totalPages = posts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      map.addAttribute("pageNumbers", pageNumbers);
    }
    return "postsSaved";
  }

  @GetMapping("/getPostByCategory")
  public String get(@RequestParam("id") int id, ModelMap map,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(4);

    Page<Post> posts = postRepository
        .findAllByCategoryId(id, PageRequest.of(currentPage - 1, pageSize));
    map.addAttribute("posts", posts);
    int totalPages = posts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      map.addAttribute("pageNumbers", pageNumbers);
      map.addAttribute("id", id);
    }
    return "getByCategory";

  }

  @GetMapping("updatePost")
  public String update(@ModelAttribute("post") Post post) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/update";
    HttpEntity<Post> httpEntity = new HttpEntity<>(post);
    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.PUT,
        httpEntity,
        new ParameterizedTypeReference<Post>() {
        });
    post = response.getBody();
    return "redirect:/";
  }

  @GetMapping(value = "updatePost/saved", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Post updateSaved(@RequestParam("id") int id,
      @RequestParam("saved") int saved, ModelAndView modelMap) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/update/saved/" + id + "/" + saved;
    ResponseEntity<Post> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Post>() {
        });
    Post post = response.getBody();
    return post;
  }

  @GetMapping("deletePost")
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


