package mk.ukim.finki.wp.lab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {
    @GetMapping("/")
    public String redirect(){
        return "redirect:/courses";
    }
}
