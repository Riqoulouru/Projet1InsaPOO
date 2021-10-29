package com.example.Projet1InsaPOO.Controller;

import com.example.Projet1InsaPOO.Model.Borne;
import com.example.Projet1InsaPOO.Model.Commande;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/index")
public class indexController {

    @GetMapping
    public String getMappingPage(Model model) throws IOException, ClassNotFoundException {

        Borne borne = Borne.getInstance();
        borne.init();

        model.addAttribute("borne", borne);
        model.addAttribute("platsMenu", borne.getPlatMenuMap());
        model.addAttribute("plats", borne.getPlatMap());
        model.addAttribute("accompagnements", borne.getAccompagnementMap());
        model.addAttribute("boissons", borne.getBoissonMap());

        return "index";
    }

    @PostMapping("/plat/{i}")
    public ResponseEntity<String> addPlatToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addPlatToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getPlatList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getPlatMap().get(i).getNom());
    }


    @PostMapping("/accompagnement/{i}")
    public ResponseEntity<String> addAccompagnementToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addAccompagnementToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getAccompagnementList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getAccompagnementMap().get(i).getNom());
    }


    @PostMapping("/boisson/{i}")
    public ResponseEntity<String> addBoissonToOrder(@PathVariable("i") int i) throws IOException, ClassNotFoundException {

        Borne.getInstance().addBoissonToOrder(i);
        System.out.println(Borne.getInstance().getCommande().getBoissonList().get(0).toString());
        return ResponseEntity.ok(Borne.getInstance().getBoissonMap().get(i).getNom());
    }

    @PostMapping("/menu/{plat}/{accompagnement}/{boisson}")
    public ResponseEntity<String> addMenuToOrder(@PathVariable("plat") int plat,
                                                    @PathVariable("accompagnement") int accompagnement,
                                                    @PathVariable("boisson") int boisson) throws IOException, ClassNotFoundException {

        Borne.getInstance().addMenuToOrder(plat, accompagnement, boisson);
        System.out.println(Borne.getInstance().getCommande().getMenuList().get(0).getPlat().toString());
        return ResponseEntity.ok(Borne.getInstance().getBoissonMap().get(0).getNom());
    }

    @GetMapping("/commande")
    public ResponseEntity<Commande> getCommande() {

        return ResponseEntity.ok(Borne.getInstance().getCommande());
    }

    @GetMapping("/payer")
    public ResponseEntity<String> payOrder() throws IOException {

        return ResponseEntity.ok(Borne.getInstance().payer());
    }

    @DeleteMapping("/delete/{index}/{type}")
    public ResponseEntity<String> removeElement(@PathVariable("index") int index,
                                @PathVariable("type") String type) {


        return ResponseEntity.ok(Borne.getInstance().removeElement(index, type));
    }

}
