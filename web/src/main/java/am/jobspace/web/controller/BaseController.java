package am.jobspace.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by stella on 4/2/19.
 */
@ControllerAdvice
public class BaseController {

  @ModelAttribute("user")
  public Object login(HttpServletRequest request) {
    return request.getSession().getAttribute("user");
  }
}
