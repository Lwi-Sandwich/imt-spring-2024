package org.imt.tournamentmaster.controller.equipe;

import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.service.equipe.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    @Autowired
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> getById(@PathVariable long id) {
        Optional<Equipe> equipe = equipeService.getById(id);

        return equipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Equipe> getAll() {
        return equipeService.getAll();
    }

    @PostMapping
    public ResponseEntity<Equipe> create(@RequestBody Equipe equipe) {
        return ResponseEntity.ok(equipeService.create(equipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@PathVariable long id, @RequestBody Equipe equipe) {
        Optional<Equipe> updatedEquipe = equipeService.update(id, equipe);

        return updatedEquipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Equipe> delete(@PathVariable long id) {
        Optional<Equipe> equipe = equipeService.delete(id);

        return equipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
