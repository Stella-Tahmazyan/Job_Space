package am.jobspace.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String main() {
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
