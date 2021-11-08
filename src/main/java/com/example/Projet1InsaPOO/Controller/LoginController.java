package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.DTO.CuisineDTO;
import com.example.Projet1InsaPOO.Model.Boisson;
import com.example.Projet1InsaPOO.Model.Borne;
import com.example.Projet1InsaPOO.Model.Commande;
import com.example.Projet1InsaPOO.Model.Cuisine;
import com.example.Projet1InsaPOO.Projet1InsaPooApplication;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String getMappingPage(Model model){
        Borne.getInstance().resetCommande();
        model.addAttribute("borne", Borne.getInstance());
        return "login";
    }


    @GetMapping("/inscription/{nom}/{prenom}")
    public ResponseEntity<String> inscriptionNewId(@PathVariable("nom") String nom,
                                                   @PathVariable("prenom") String prenom) throws IOException {

        return ResponseEntity.ok(Borne.getInstance().inscription(nom, prenom));
    }


    @GetMapping("/login/{id}")
    public ResponseEntity<String> loginInformations( @PathVariable("id") int id) {

        return ResponseEntity.ok(Borne.getInstance().login(id));
    }


    @GetMapping("/kitchen/status")
    public ResponseEntity<CuisineDTO> getKitchenStatus() {

        CuisineDTO cuisineDTO = new CuisineDTO();

        /*

        for (Cuisine cuisine : Projet1InsaPooApplication.cuisines) {

            if(cuisine.getCommandesEnAttenteDePreparation() != null) {
                System.out.println("oui : " + cuisine.getCommandesEnAttenteDePreparation().size());
                for(Commande commande : cuisine.getCommandesEnAttenteDePreparation()) {
                    cuisineDTO.addCommandeEnAttenteDePreparation(commande);
                }
            }

            if(cuisine.getCommandesEnCoursDePreparation() != null) {
                System.out.println("non : " + cuisine.getCommandesEnCoursDePreparation().size());
                for (Commande commande : cuisine.getCommandesEnCoursDePreparation()) {
                    cuisineDTO.addCommandeEnCoursDePreparation(commande);
                }
            }
            System.out.println("--------------");

        }

         */


        return ResponseEntity.ok(cuisineDTO);
    }

}