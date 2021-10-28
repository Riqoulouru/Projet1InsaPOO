package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.Model.Borne;
import com.example.Projet1InsaPOO.Model.Commande;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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

    @GetMapping("/plat/{i}")
    public ResponseEntity<String> addPlatToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addPlatToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getPlatList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getPlatMap().get(i));
    }


    @GetMapping("/accompagnement/{i}")
    public ResponseEntity<String> addAccompagnementToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addAccompagnementToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getAccompagnementList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getAccompagnementMap().get(i));
    }


    @GetMapping("/boisson/{i}")
    public ResponseEntity<String> addBoissonToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addBoissonToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getBoissonList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getBoissonMap().get(i));
    }

    @GetMapping("/menu/{plat}/{accompagnement}/{boisson}")
    public ResponseEntity<String> addMenuToOrder(@PathVariable("plat") int plat,
                                                    @PathVariable("accompagnement") int accompagnement,
                                                    @PathVariable("boisson") int boisson) throws IOException, ClassNotFoundException {

        Borne.getInstance().addMenuToOrder(plat, accompagnement, boisson);
        System.out.println(Borne.getInstance().getCommande().getMenuList().get(0).getPlat().toString());
        return ResponseEntity.ok(Borne.getInstance().getBoissonMap().get(0));
    }

    @GetMapping("/commande")
    public ResponseEntity<Commande> getCommande(Model model) {

        return ResponseEntity.ok(Borne.getInstance().getCommande());
    }


}
