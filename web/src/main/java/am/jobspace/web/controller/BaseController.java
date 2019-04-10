package am.jobspace.web.controller;

import am.jobspace.common.model.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by stella on 4/2/19.
 */
//@ControllerAdvice
public class BaseController {

//  @ModelAttribute("user")
  public Object login(HttpServletRequest request) {
    return new User("ddddd");
  }
}
