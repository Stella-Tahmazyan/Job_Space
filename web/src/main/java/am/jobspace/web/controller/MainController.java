package am.jobspace.web.controller;

import am.jobspace.common.model.Category;
import am.jobspace.common.repository.AdsRepository;
import am.jobspace.common.repository.CategoryRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


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
    public String main(ModelMap map) {

            List<Category> cat=categoryRepositroy.findAll();
            map.addAttribute("categories",cat);
                return "index";
    }

    @GetMapping("/worker")
    public String workerPage(ModelMap map) {
        List<Category> cat=categoryRepositroy.findAll();
        map.addAttribute("categories",cat);
        return "worker";
    }

    @GetMapping("/employer")
    public String employerPage(ModelMap map) {
        List<Category> cat=categoryRepositroy.findAll();;
        map.addAttribute("categories",cat);
        return "employer";
    }

}
