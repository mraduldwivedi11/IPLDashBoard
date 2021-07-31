package com.mradul.repository;

import org.springframework.data.repository.CrudRepository;

import com.mradul.data.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{
	
	Team findByTeamName(String teamName);
}
