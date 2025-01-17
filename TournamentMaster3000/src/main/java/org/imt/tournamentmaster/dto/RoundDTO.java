package org.imt.tournamentmaster.dto;

public class RoundDTO {
    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public long getEquipeAId() {
        return equipeAId;
    }

    public void setEquipeAId(long equipeAId) {
        this.equipeAId = equipeAId;
    }

    public long getEquipeBId() {
        return equipeBId;
    }

    public void setEquipeBId(long equipeBId) {
        this.equipeBId = equipeBId;
    }

    private int scoreA;
    private int scoreB;
    private int roundNumber;
    private long equipeAId;
    private long equipeBId;
}
