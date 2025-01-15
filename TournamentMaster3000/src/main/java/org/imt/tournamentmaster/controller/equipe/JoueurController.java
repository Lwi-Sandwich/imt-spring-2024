package org.imt.tournamentmaster.controller.equipe;

import org.imt.tournamentmaster.model.equipe.Joueur;
import org.imt.tournamentmaster.service.equipe.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/joueur")
public class JoueurController {

    private final JoueurService joueurService;

    @Autowired
    public JoueurController(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joueur> getJoueurById(@PathVariable long id) {
        Optional<Joueur> joueur = joueurService.getById(id);

        return joueur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Joueur> getAllJoueurs() {
        return joueurService.getAll();
    }

    @PostMapping
    public ResponseEntity<Joueur> createJoueur(@RequestBody Joueur joueur) {
        return ResponseEntity.ok(joueurService.create(joueur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joueur> updateJoueur(@PathVariable long id, @RequestBody Joueur joueur) {
        Optional<Joueur> updatedJoueur = joueurService.update(id, joueur);

        return updatedJoueur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Joueur> deleteJoueur(@PathVariable long id) {
        Optional<Joueur> deletedJoueur = joueurService.delete(id);

        return deletedJoueur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
