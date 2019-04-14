package am.jobspace.web.controller;

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

@Controller
public class AdsController {
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @Autowired
    private AdsRepository adsRepository;

    @GetMapping("/adsByCategory")
    public String getAdsByCategory(ModelMap map, @RequestParam("id") int id){
        map.addAttribute("ads", adsRepository.findAllByCategoryId(id));
        return "adsByCategory";
    }

    @GetMapping("/getPicUrl")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
}
