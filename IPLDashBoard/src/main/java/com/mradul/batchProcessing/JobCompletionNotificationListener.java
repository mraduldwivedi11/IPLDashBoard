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
	      Iterable<Match> itr = matchrepository.findAll();
	      itr.forEach(i-> System.out.println(i));
	    }
	  }
}