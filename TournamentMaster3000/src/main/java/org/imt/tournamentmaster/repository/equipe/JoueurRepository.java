package org.imt.tournamentmaster.repository.equipe;

import org.imt.tournamentmaster.model.equipe.Joueur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoueurRepository extends CrudRepository<Joueur, Long> {
	List<Joueur> findAllById(Iterable<Long> ids);
}
