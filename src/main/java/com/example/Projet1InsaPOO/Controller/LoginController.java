package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.DTO.CommandeDTO;
import com.example.Projet1InsaPOO.Model.Borne;
import com.example.Projet1InsaPOO.Model.Cuisine;
import com.example.Projet1InsaPOO.Projet1InsaPooApplication;
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
    public ResponseEntity<CommandeDTO> getKitchenStatus() {

        CommandeDTO commandeDTO = new CommandeDTO();


        for(Cuisine cuisine : Projet1InsaPooApplication.cuisines) {

            if(cuisine.getCommandeEnCours() != null) {
                if (!commandeDTO.getCommandesEnCours().contains(cuisine.getCommandeEnCours())) {
                    commandeDTO.addCommandeEnCours(cuisine.getCommandeEnCours());
                }
            }
        }

        return ResponseEntity.ok(commandeDTO);
    }

}