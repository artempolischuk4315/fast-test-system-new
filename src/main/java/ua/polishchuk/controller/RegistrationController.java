package ua.polishchuk.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.polishchuk.dto.RegistrationDto;
import ua.polishchuk.exception.UserAlreadyRegisteredException;
import ua.polishchuk.service.RegistrationService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private ResourceBundle bundle = ResourceBundle.getBundle("pages");

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public ModelAndView register(ModelAndView modelAndView, Principal loggedUser) {
       /* if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName(" "/*REDIRECT_HOME_PAGE*//*);
        } else {*/
            //modelAndView.addObject("registrationDto", new RegistrationDto());
            modelAndView.setViewName(bundle.getString("page.registration"));
       // }

        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(@Valid RegistrationDto registrationDto,
                                     BindingResult bindingResult, ModelAndView modelAndView,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingError", "Binding error");
            modelAndView.setViewName(bundle.getString("page.registration"));

            return modelAndView;
        }

        if (!Objects.equals(registrationDto.getPassword(), registrationDto.getConfirmPassword())) {
            modelAndView.addObject("confirmPasswordError", "Confirmation error");
            modelAndView.setViewName(bundle.getString("page.registration"));

            return modelAndView;
        }
        registrationService.saveNewUser(registrationDto);
        redirectAttributes.addFlashAttribute("registrationSuccess", "message.registered");
        modelAndView.setViewName("redirect:/" +bundle.getString("page.login") );

        return modelAndView;
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ModelAndView userAlreadyRegisteredExceptionHandling() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userAlreadyRegisteredError", "User already registered");
        modelAndView.setViewName(bundle.getString("page.registration"));

        return modelAndView;
    }
}
