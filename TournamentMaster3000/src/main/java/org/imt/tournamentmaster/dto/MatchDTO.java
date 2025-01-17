package org.imt.tournamentmaster.dto;

import org.imt.tournamentmaster.model.match.Match.Status;

import java.util.List;

public class MatchDTO {

    private Status status;
    private Long equipeAId;
    private Long equipeBId;
    private List<Long> roundsIds;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getEquipeAId() {
        return equipeAId;
    }

    public void setEquipeAId(Long equipeAId) {
        this.equipeAId = equipeAId;
    }

    public Long getEquipeBId() {
        return equipeBId;
    }

    public void setEquipeBId(Long equipeBId) {
        this.equipeBId = equipeBId;
    }

    public List<Long> getRoundsIds() {
        return roundsIds;
    }

    public void setRoundsIds(List<Long> roundsIds) {
        this.roundsIds = roundsIds;
    }
}
