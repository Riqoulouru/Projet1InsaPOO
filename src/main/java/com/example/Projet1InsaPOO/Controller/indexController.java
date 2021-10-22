package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.Model.Borne;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/index")
public class indexController {

    @GetMapping
    public String getMappingPage(Model model) {

        Borne.getInstance().init();

        model.addAttribute("borne", Borne.getInstance());
        model.addAttribute("plats", Borne.getInstance().getPlatMap());
        model.addAttribute("accompagnement", Borne.getInstance().getAccompagnementMap());
        model.addAttribute("boisson", Borne.getInstance().getBoissonMap());

        return "index";
    }



}
