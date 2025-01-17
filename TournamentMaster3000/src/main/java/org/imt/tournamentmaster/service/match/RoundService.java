package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.dto.RoundDTO;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Round;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.match.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.StreamSupport;

@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final EquipeRepository equipeRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository, EquipeRepository equipeRepository) {
        this.roundRepository = roundRepository;
        this.equipeRepository = equipeRepository;
    }

    public Optional<Round> getById(long id) {
        return roundRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Round> getAll() {
        return StreamSupport.stream(roundRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Round> getByScoreAGreaterThanEqual(int scoreA) {
        return roundRepository.findByScoreAGreaterThanEqual(scoreA);
    }

    @Transactional
    public Round create(RoundDTO roundDTO) {
        Equipe equipeA = Optional.of(equipeRepository.findById(roundDTO.getEquipeAId()).orElseThrow()).orElseThrow();
        Equipe equipeB = Optional.of(equipeRepository.findById(roundDTO.getEquipeBId()).orElseThrow()).orElseThrow();

        Round newRound = new Round();
        newRound.setEquipeA(equipeA);
        newRound.setEquipeB(equipeB);
        newRound.setScoreA(roundDTO.getScoreA());
        newRound.setScoreB(roundDTO.getScoreB());
        newRound.setRoundNumber(roundDTO.getRoundNumber());
        return roundRepository.save(newRound);
    }

    @Transactional
    public Optional<Round> update(long id, RoundDTO roundDTO) {
        return roundRepository.findById(id)
            .map(existingRound -> {
                Equipe equipeA = Optional.of(equipeRepository.findById(roundDTO.getEquipeAId()).orElseThrow()).orElseThrow();
                Equipe equipeB = Optional.of(equipeRepository.findById(roundDTO.getEquipeBId()).orElseThrow()).orElseThrow();
                existingRound.setEquipeA(equipeA);
                existingRound.setEquipeB(equipeB);
                existingRound.setScoreA(roundDTO.getScoreA());
                existingRound.setScoreB(roundDTO.getScoreB());
                existingRound.setRoundNumber(roundDTO.getRoundNumber());
                return roundRepository.save(existingRound);
            });
    }

    @Transactional
    public Optional<Round> delete(long id) {
        Optional<Round> round = getById(id);
        round.ifPresent(roundRepository::delete);
        return round;
    }
}
