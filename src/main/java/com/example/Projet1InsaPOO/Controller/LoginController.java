package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.Model.Borne;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String getMappingPage(Model model){

        model.addAttribute("borne", Borne.getInstance());

        return "login";

    }

    @GetMapping("/inscription/{nom}/{prenom}")
    public ResponseEntity<String> inscriptionNewId(Model model,
                                                   @PathVariable("nom") String nom,
                                                   @PathVariable("prenom") String prenom) throws IOException {

        return ResponseEntity.ok(Borne.getInstance().inscription(nom, prenom));

    }

    @GetMapping("/login/{id}")
    public ResponseEntity<String> inscriptionNewId(Model model,
                                                   @PathVariable("id") int id) throws IOException {

        return ResponseEntity.ok(Borne.getInstance().login(id));

    }
}