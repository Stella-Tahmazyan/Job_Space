package am.jobspace.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class BaseController {

  @ModelAttribute("user")
  public Object login(HttpServletRequest request) {
    return request.getSession().getAttribute("user");
  }
}
