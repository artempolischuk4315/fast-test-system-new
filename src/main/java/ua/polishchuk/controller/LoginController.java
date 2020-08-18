package ua.polishchuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class LoginController {

    private ResourceBundle bundle = ResourceBundle.getBundle("pages");

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, Principal loggedUser) {
        if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName("redirect:/"+bundle.getString("page.home"));
        } else {
            modelAndView.setViewName(bundle.getString("page.login"));
        }

        return modelAndView;
    }
}
