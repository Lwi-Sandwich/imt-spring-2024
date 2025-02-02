package org.imt.tournamentmaster.model.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.imt.tournamentmaster.model.equipe.Equipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "`match`")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JsonIgnore
    private Equipe equipeA;

    @OneToOne
    @JsonIgnore
    private Equipe equipeB;

    @OneToMany
    @JsonIgnore
    private List<Round> rounds; // Set est un type de collection, on va éviter les confusions et appeler ça un "round"

    private Status status;

    public Match() {
    }

    public Match(long id, Equipe equipeA, Equipe equipeB, List<Round> rounds, Status status) {
        this.id = id;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.rounds = rounds;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Equipe getEquipeA() {
        return equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public long getEquipeAId() {
        return equipeA.getId();
    }

    public long getEquipeBId() {
        return equipeB.getId();
    }

    public String getEquipeAName() {
        return equipeA.getNom();
    }

    public String getEquipeBName() {
        return equipeB.getNom();
    }

    public long getWinnerId() {
        Optional<Equipe> winner = determineWinner();
        return winner.map(Equipe::getId).orElse(-1L);
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Long> getRoundIds() {
        return rounds.stream().map(Round::getId).toList();
    }

    public Status getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEquipeA(Equipe equipeA) {
        this.equipeA = equipeA;
    }

    public void setEquipeB(Equipe equipeB) {
        this.equipeB = equipeB;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addRounds(List<Round> rounds) {
        this.rounds.addAll(rounds);
    }

    public void removeRounds(List<Round> rounds) {
        this.rounds.removeAll(rounds);
    }


    public Optional<Equipe> determineWinner() {
        int wonByA = 0;
        int wonByB = 0;

        for (Round round : rounds) {
            Optional<Equipe> winner = round.determineWinner();

            if (winner.isPresent() && winner.get().equals(equipeA)) {
                wonByA++;
            } else if (winner.isPresent()) {
                wonByB++;
            }
        }
        if (wonByA == wonByB) {
            return Optional.empty();
        }
        if (wonByA > wonByB) {
            return Optional.of(equipeA);
        }
        return Optional.of(equipeB);
    }

    @Override
    public String toString() {
        return "Match{" +
                "equipeA=" + equipeA +
                ", equipeB=" + equipeB +
                ", rounds=" + rounds +
                ", status=" + status +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id == match.id && Objects.equals(equipeA, match.equipeA) && Objects.equals(equipeB, match.equipeB) && Objects.equals(rounds, match.rounds) && status == match.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equipeA, equipeB, rounds, status);
    }

    public enum Status {
        NOUVEAU, EN_COURS, TERMINE
    }
}
