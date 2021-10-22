package com.example.Projet1InsaPOO.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class FirstController {

    public String getMappingPage(Model model){

        String valeur = "Bienvenu";
        model.addAttribute("bienvenu", valeur);
        return "welcome";

    }


}