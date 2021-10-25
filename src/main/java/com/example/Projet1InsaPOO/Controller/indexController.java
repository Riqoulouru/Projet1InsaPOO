package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.Model.Borne;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class indexController {

    @GetMapping
    public String getMappingPage(Model model) {

        Borne borne = Borne.getInstance();
        borne.init();

        model.addAttribute("borne", borne);
        model.addAttribute("plats", borne.getPlatMap());
        model.addAttribute("accompagnements", borne.getAccompagnementMap());
        model.addAttribute("boissons", borne.getBoissonMap());

        return "index";
    }



}
