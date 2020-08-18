package ua.polishchuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ResourceBundle;

@Controller
public class IndexPageController {

    @GetMapping("/")
    public String showIndexPage(){
        ResourceBundle bundle = ResourceBundle.getBundle("pages");

        return bundle.getString("page.index");
    }
}
