package com.mradul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mradul.data.Team;
import com.mradul.repository.TeamRepository;

@RestController
public class TeamController {
	
	@Autowired
	TeamRepository teamRepository;
	
	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		return teamRepository.findByTeamName(teamName);
	}
}
