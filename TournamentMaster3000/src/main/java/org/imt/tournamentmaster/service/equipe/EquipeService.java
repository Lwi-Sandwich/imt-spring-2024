package org.imt.tournamentmaster.service.equipe;

import org.imt.tournamentmaster.dto.EquipeDTO;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.equipe.Joueur;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.equipe.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;

    @Autowired
    public EquipeService(EquipeRepository equipeRepository, JoueurRepository joueurRepository) {
        this.equipeRepository = equipeRepository;
        this.joueurRepository = joueurRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Equipe> getById(long id) {
        return equipeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Equipe> getAll() {
        return StreamSupport.stream(equipeRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional
    public Equipe create(EquipeDTO equipeDTO) {
        List<Joueur> joueurs = joueurRepository.findAllById(equipeDTO.getJoueurIds());
        if (joueurs.size() != (equipeDTO.getJoueurIds() == null ? 0 : equipeDTO.getJoueurIds().size())) {
            throw new IllegalArgumentException("Some player IDs do not exist.");
        }
        Equipe equipe = new Equipe();
        equipe.setNom(equipeDTO.getNom());
        equipe.setJoueurs(joueurs);

        return equipeRepository.save(equipe);
    }

    @Transactional
    public Optional<Equipe> update(long id, EquipeDTO equipeDTO ) {
        return equipeRepository.findById(id)
            .map(existingEquipe -> {
                List<Joueur> joueurs = joueurRepository.findAllById(equipeDTO.getJoueurIds());
                if (joueurs.size() != (equipeDTO.getJoueurIds() == null ? 0 : equipeDTO.getJoueurIds().size())) {
                    throw new IllegalArgumentException("Some player IDs do not exist.");
                }
                existingEquipe.setNom(equipeDTO.getNom());
                existingEquipe.setJoueurs(joueurs);
                return equipeRepository.save(existingEquipe);
            });
    }

    @Transactional
    public Optional<Equipe> delete(long id) {
        Optional<Equipe> equipe = getById(id);
        equipe.ifPresent(equipeRepository::delete);
        return equipe;
    }

    @Transactional
    public Optional<Equipe> addJoueurs(long id, List<Long> joueursIds) {
        Optional<Equipe> equipe = getById(id);
        if (equipe.isEmpty()) {
            return Optional.empty();
        }
        List<Joueur> joueurs = joueurRepository.findAllById(joueursIds);
        if (joueurs.size() != joueursIds.size()) {
            throw new IllegalArgumentException("Some player IDs do not exist.");
        }
        equipe.get().addJoueurs(joueurs);
        return Optional.of(equipeRepository.save(equipe.get()));
    }

    @Transactional
    public Optional<Equipe> removeJoueurs(long id, List<Long> joueursIds) {
        Optional<Equipe> equipe = getById(id);
        if (equipe.isEmpty()) {
            return Optional.empty();
        }
        List<Joueur> joueurs = joueurRepository.findAllById(joueursIds);
        if (joueurs.size() != joueursIds.size()) {
            throw new IllegalArgumentException("Some player IDs do not exist.");
        }
        equipe.get().removeJoueurs(joueurs);
        return Optional.of(equipeRepository.save(equipe.get()));
    }
}
