package am.jobspace.admin.controller;
import am.jobspace.common.model.Post;
import am.jobspace.common.model.User;
import am.jobspace.common.model.UserType;
import am.jobspace.admin.security.SpringUser;
import am.jobspace.common.repository.PostRepository;
import am.jobspace.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {

    @Value("${server.IP}")
    private String hostName;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
        public String main(@RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,ModelMap map) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Post> all = postRepository.findAll( PageRequest.of(currentPage - 1, pageSize));
        List<User> allusers = userRepository.findAll();
        map.addAttribute("postPage", all);
        map.addAttribute("userPage", allusers);
        int totalPages = all.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);

        }


        return "admin";
    }



    @GetMapping("/login")
    public String adminPageLogin() {
        return "login";
    }


    @GetMapping("/loginSuccess")
    public String loginSuccessPage(@AuthenticationPrincipal SpringUser springUser, @ModelAttribute User user) {

        if (springUser.getUser().getUserType() == UserType.ADMIN) {
            return "redirect:/admin";
        } else {
            return "redirect:/login";
        }

    }


    @CrossOrigin
    @GetMapping("/postApproved")
    public String postApproved(@RequestParam("id") int id) {
        postRepository.findAllPostsByApproved(id);
        return "redirect:/admin";
    }

    //@CrossOrigin
//    @GetMapping("/orderBy")
//    public String orderBy(@RequestParam(value = "sort",defaultValue = "ASC")  String sort,@RequestParam("page") Optional<Integer> page,
//                          @RequestParam("size") Optional<Integer> size,ModelMap map) {
//       // postRepository.findAll(Sort.by(Sort.Direction.fromString(orderBy), "title"));
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//        Page<Post> all = postRepository.findAllPostsByOrder(sort, PageRequest.of(currentPage - 1, pageSize));
//        if(all!=null){
//            map.addAttribute("postPage1", all);
//        }
//
//        int totalPages = all.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            map.addAttribute("pageNumbers", pageNumbers);
//        }
//        return "redirect:/admin";
//    }



}