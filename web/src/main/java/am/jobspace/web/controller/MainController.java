package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.repository.AdsRepository;
import am.jobspace.common.repository.CategoryRepositroy;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;


@Controller
public class MainController {

    @Value("${image.upload.dir}")
    private String imageUploadDir;
    @Autowired
    private CategoryRepositroy categoryRepositroy;
    @Autowired
    private AdsRepository adsRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String main(ModelMap map,@RequestParam(required=false,defaultValue="en",name="lang") String locale) {

            List<Category> cat=categoryRepositroy.findAllByLocale(locale);
            map.addAttribute("categories",cat);
                return "index";
    }

    @GetMapping("/worker")
    public String workerPage() {
        return "worker";
    }

    @GetMapping("/employer")
    public String employerPage() {
        return "employer";
    }

}
