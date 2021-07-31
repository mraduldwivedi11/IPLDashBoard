package com.mradul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mradul.data.Match;
import com.mradul.data.TeamFromMatch;

public interface MatchRepository extends CrudRepository<Match, Long>{
		// using spring projection to cast query output to a pojo
		@Query(value= " Select m.team1 AS teamName , count(*) as totalMatch from match m group by m.team1"
				+" union all Select m.team2 AS teamName , count(*) as totalMatch from match m group by m.team2 ",nativeQuery = true)
		List<TeamFromMatch> getAllTeamsData();
		
		//		@Query(value="Select new com.mradul.data.Team(m.matchWinner , count(*)) from Match m GROUP BY m.matchWinner ")
		//		List<Team> getTeamsWithTheirWin();
		//  didn't use cause constructor param name
		
		@Query(value="Select m.match_winner , count(*)  from match m group by m.match_winner ",nativeQuery = true)
		List<Object[]> getTeamsWithTheirWin();
}
