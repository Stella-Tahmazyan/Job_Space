package am.jobspace.web.controller;

import am.jobspace.common.model.Images;
import am.jobspace.common.model.Post;

import am.jobspace.common.model.User;
import javafx.geometry.Pos;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
//@RequestMapping("/post")
public class PostController {

  @Value("${server.IP}")
  private String hostName;

  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @PostMapping("post/add")
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

  @GetMapping("/getPost")
  public String getById(@RequestParam("id") int id, RedirectAttributes redirectAttributes,
      ModelMap modelMap) {
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
    modelMap.addAttribute("post", post);
    return "post-detail";
  }

  @GetMapping("getAllPostBySaved")
  public String getAllPostBySaved(ModelMap modelMap) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/getSaved";

    ResponseEntity<List<Post>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Post>>() {
        });
    List<Post> allAds = response.getBody();

    if (allAds == null) {
      return "redirect:/";
    }
    modelMap.addAttribute("allAds", allAds);
    return "allAds";
  }


  @GetMapping("getPost/all")
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

  @GetMapping("/getPostByCategory")
  public String get(@RequestParam("id") int id, ModelMap map,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size,
      @RequestParam(value = "href", required = false) String href) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(2);
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "post/pagable/" + id + "/" + currentPage + "/" + pageSize;
    ResponseEntity<List<Post>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Post>>() {
        });
    List<Post> posts = response.getBody();
    map.addAttribute("allAds", posts);
  String url1 = hostName + "/post/get/category/" + id;

    ResponseEntity<List<Post>> response1 = restTemplate.exchange(
        url1,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Post>>() {
        });

    int totalPages = response1.getBody().size();
    totalPages =totalPages/pageSize;
    totalPages = totalPages == 0 ? totalPages : totalPages + 1;
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      map.addAttribute("pageNumbers", pageNumbers);
      map.addAttribute("allAds", posts);
    }
    return href;

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
// modelMap.addObject("post",post);

// post = response.getBody();
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


