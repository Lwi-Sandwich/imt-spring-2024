package org.imt.tournamentmaster.dto;

import java.util.List;

public class EquipeDTO {

	private String nom;
	private List<Long> joueurIds;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Long> getJoueurIds() {
		return joueurIds;
	}

	public void setJoueurIds(List<Long> joueurIds) {
		this.joueurIds = joueurIds;
	}
}
