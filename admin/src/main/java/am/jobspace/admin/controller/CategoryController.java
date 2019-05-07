package am.jobspace.admin.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.model.User;
import am.jobspace.common.repository.CategoryRepositroy;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.util.Locale;

@Controller
public class CategoryController {

  @Value("${image.upload.dir}")
  private String imageUploadDir;

  @Value("${server.IP}")
  private String hostName;


  @Autowired
  private CategoryRepositroy categoryRepositroy;

  @GetMapping("/admin/addCategory")
  public String registerForm(ModelMap map) {
    //List<Category> allCat =  categoryRepositroy.findAll();
    // map.addAttribute("categories",allCat);
    return "category";
  }

  @PostMapping("/admin/addCategory")
  public String register(Locale locale, RedirectAttributes redirectAttributes,
      @ModelAttribute Category cat, @RequestParam("picture") MultipartFile file)
      throws IOException {
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File picture = new File(imageUploadDir + File.separator + fileName);
    file.transferTo(picture);
    cat.setPicName(fileName);
    cat.setLocale(locale);
    categoryRepositroy.save(cat);
    redirectAttributes.addFlashAttribute("message", "Added successfully!");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
    return "redirect:/admin/addCategory";
  }

  @GetMapping("/getImages")
  public void getImageAsByteArray(HttpServletResponse response,
      @RequestParam("picUrl") String picUrl) throws IOException {
    InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    IOUtils.copy(in, response.getOutputStream());
  }


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

    String url = hostName + "get/" + id;

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
    User jwt = (User) request.getSession().getAttribute("user");
    if (jwt == null) {
      return "";
    }
    HttpEntity<Category> reqt = new HttpEntity<>(category);
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
  public String deleteById(@RequestParam("id") int id, HttpServletRequest request) {
    RestTemplate restTemplate = new RestTemplate();
    String url = hostName + "category/delete/" + id;
    User jwt = (User) request.getSession().getAttribute("user");

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
