package org.imt.tournamentmaster.service.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.imt.tournamentmaster.dto.MatchDTO;
import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.match.Round;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.imt.tournamentmaster.repository.match.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final RoundRepository roundRepository;
    private final EquipeRepository equipeRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, RoundRepository roundRepository, EquipeRepository equipeRepository) {
        this.matchRepository = matchRepository;
        this.roundRepository = roundRepository;
        this.equipeRepository = equipeRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Match> getById(long id) {
        return matchRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Match> getAll() {
        return StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional
    public Match create(MatchDTO matchDTO) {
        Match match = new Match();
        match.setStatus(matchDTO.getStatus());
        match.setEquipeA(equipeRepository.findById(matchDTO.getEquipeAId()).orElseThrow());
        match.setEquipeB(equipeRepository.findById(matchDTO.getEquipeBId()).orElseThrow());
        match.setRounds(roundRepository.findAllById(matchDTO.getRoundsIds()));
        return matchRepository.save(match);
    }

    @Transactional
    public Optional<Match> update(long id, MatchDTO matchDTO) {
        return matchRepository.findById(id)
            .map(existingMatch -> {
                Equipe equipeA = equipeRepository.findById(matchDTO.getEquipeAId()).orElseThrow();
                Equipe equipeB = equipeRepository.findById(matchDTO.getEquipeBId()).orElseThrow();
                existingMatch.setStatus(matchDTO.getStatus());
                existingMatch.setEquipeA(equipeA);
                existingMatch.setEquipeB(equipeB);
                existingMatch.setRounds(roundRepository.findAllById(matchDTO.getRoundsIds()));
                return matchRepository.save(existingMatch);
            });
    }

    @Transactional
    public Optional<Match> delete(long id) {
        Optional<Match> match = getById(id);
        match.ifPresent(matchRepository::delete);
        return match;
    }

    @Transactional
    public Match addRounds(long id, List<Long> roundsIds) {
        Match match = matchRepository.findById(id).orElseThrow();
        List<Round> rounds = roundRepository.findAllById(roundsIds);
        match.addRounds(rounds);
        return matchRepository.save(match);
    }

    @Transactional
    public Match removeRounds(long id, List<Long> roundsIds) {
        Match match = matchRepository.findById(id).orElseThrow();
        List<Round> rounds = roundRepository.findAllById(roundsIds);
        match.removeRounds(rounds);
        return matchRepository.save(match);
    }
}
