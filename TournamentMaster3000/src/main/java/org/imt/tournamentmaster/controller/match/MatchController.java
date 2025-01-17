package org.imt.tournamentmaster.controller.match;

import org.imt.tournamentmaster.dto.MatchDTO;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable long id) {
        Optional<Match> match = matchService.getById(id);

        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @PostMapping
    public ResponseEntity<Match> create(@RequestBody MatchDTO matchDTO) {
        return ResponseEntity.ok(matchService.create(matchDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> update(@PathVariable long id, @RequestBody MatchDTO matchDTO) {
        Optional<Match> updatedMatch = matchService.update(id, matchDTO);

        return updatedMatch.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Match> delete(@PathVariable long id) {
        Optional<Match> match = matchService.delete(id);

        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/rounds")
    public ResponseEntity<Match> addRounds(@PathVariable long id, @RequestBody List<Long> roundIds) {
        return ResponseEntity.ok(matchService.addRounds(id, roundIds));
    }

    @DeleteMapping("/{id}/rounds")
    public ResponseEntity<Match> removeRounds(@PathVariable long id, @RequestBody List<Long> roundIds) {
        return ResponseEntity.ok(matchService.removeRounds(id, roundIds));
    }
}
