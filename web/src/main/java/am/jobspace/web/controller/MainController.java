package am.jobspace.web.controller;

import am.jobspace.common.model.UserType;
import am.jobspace.common.security.SpringUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal
        SpringUser springUser,HttpServletRequest request) {
        request.getSession().setAttribute("user",springUser);
        if (springUser.getUser().getUserType() == UserType.EMPLOYER) {
            return "redirect:/employer";
        }

        return "redirect:/";

    }
}
