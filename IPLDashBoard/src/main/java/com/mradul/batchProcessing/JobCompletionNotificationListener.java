package com.mradul.batchProcessing;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mradul.data.Match;
import com.mradul.data.Team;
import com.mradul.data.TeamFromMatch;
import com.mradul.repository.MatchRepository;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{


	  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	 //private final JdbcTemplate jdbcTemplate;
	  private final EntityManager em;

	  @Autowired
	  MatchRepository matchrepository;

	  @Autowired
	  public JobCompletionNotificationListener(EntityManager em) {
	    this.em = em;
	  }

	  @Override
	  @Transactional
	  public void afterJob(JobExecution jobExecution) {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	      log.info("!!! JOB FINISHED! Time to verify the results");
	      Map<String , Team> teamData = new HashMap<>();
	      
	      //getting all different teams and total their matches with custom query
 	      List<TeamFromMatch> teams = matchrepository.getAllTeamsData();
	      
	      //storing that in a Hash Map<TeamName,Team>
	      for(TeamFromMatch t : teams) {
	    	  teamData.merge(t.getTeamName(), 
	    			  new Team(t.getTeamName(),t.getTotalMatch()), 
	    			  (Team t1, Team t2) -> new Team(t1.getTeamName(),t1.getTotalMatch()+t2.getTotalMatch())
	    			  );
	      }
	      
	      //getting teams and their total win
	      List<Object[]> list = matchrepository.getTeamsWithTheirWin();
	      
	      //updating teams data in hashmap
	      list.forEach(t->{
	    	  if(teamData.containsKey((String)t[0])) {
	    	  teamData.get((String)t[0]).setTotalWin(((BigInteger)t[1]).longValue());}
	      });
	      
	      
	      teamData.values()
	      			.forEach(team -> em.persist(team));
	      teamData.values().forEach(t-> System.out.println(t.getTeamName()+" "+t.getTotalMatch()+" "+t.getTotalWin()));
	    }
	  }
}