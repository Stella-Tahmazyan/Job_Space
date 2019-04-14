package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.repository.CategoryRepositroy;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


@Controller
public class CategoryController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @Autowired
    private CategoryRepositroy categoryRepositroy;

    @GetMapping("/addCategory")
    public String registerForm (ModelMap map){
        //List<Category> allCat =  categoryRepositroy.findAll();
       // map.addAttribute("categories",allCat);
        return "category";
    }

    @PostMapping("/addCategory")
    public String register(Locale locale,RedirectAttributes redirectAttributes, @ModelAttribute Category cat , @RequestParam("picture") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        file.transferTo(picture);
        cat.setPicName(fileName);
        cat.setLocale(locale);
        categoryRepositroy.save(cat);
        redirectAttributes.addFlashAttribute("message", "Added successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/addCategory";
    }
    @GetMapping("/getImages")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

}
