package com.mradul.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String teamName;
	private long totalMatch;
	private long totalWin;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public long getTotalMatch() {
		return totalMatch;
	}
	public void setTotalMatch(long totalMatch) {
		this.totalMatch = totalMatch;
	}
	public long getTotalWin() {
		return totalWin;
	}
	public void setTotalWin(long totalWin) {
		this.totalWin = totalWin;
	}
	public Team(String name,long totalMatch) {
		this.teamName =name;
		this.totalMatch=totalMatch;
	}


}