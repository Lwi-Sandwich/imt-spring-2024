package org.imt.tournamentmaster.service.equipe;

import org.imt.tournamentmaster.model.equipe.Joueur;
import org.imt.tournamentmaster.repository.equipe.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class JoueurService {

    private final JoueurRepository joueurRepository;

    @Autowired
    public JoueurService(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Joueur> getById(long id) {
        return joueurRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Joueur> getAll() {
        return StreamSupport.stream(joueurRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional
    public Joueur create(Joueur joueur) {
        if (joueur.getId() != 0 && joueurRepository.existsById(joueur.getId())) {
            throw new IllegalArgumentException("A joueur with this ID already exists: " + joueur.getId());
        }
        return joueurRepository.save(joueur);
    }

    @Transactional
    public Optional<Joueur> update(long id, Joueur updatedJoueur) {
        return joueurRepository.findById(id)
            .map(existingJoueur -> {
                existingJoueur.setNom(updatedJoueur.getNom());
                existingJoueur.setPrenom(updatedJoueur.getPrenom());
                existingJoueur.setNumero(updatedJoueur.getNumero());
                return joueurRepository.save(existingJoueur);
            });
    }

    @Transactional
    public Optional<Joueur> delete(long id) {
        Optional<Joueur> joueur = getById(id);
        joueur.ifPresent(joueurRepository::delete);
        return joueur;
    }
}
