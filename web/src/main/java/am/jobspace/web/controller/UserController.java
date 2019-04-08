package am.jobspace.web.controller;

import am.jobspace.common.model.User;
import am.jobspace.common.repository.UserRepository;
import am.jobspace.common.service.EmailService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.List;

@Controller
public class UserController {

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;



    @GetMapping("/register")
    public String registerForm (ModelMap map){
        List<User> all =  userRepository.findAll();
        map.addAttribute("users", all);
        return "registration";
    }

    @PostMapping("/register")
    public String register(RedirectAttributes redirectAttributes, @ModelAttribute User user , @RequestParam("picture") MultipartFile file) throws IOException {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + File.separator + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


}
