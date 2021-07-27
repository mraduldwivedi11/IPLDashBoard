package com.mradul.batchProcessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mradul.data.Match;
import com.mradul.data.MatchInputData;
import com.mradul.repository.MatchRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	  @Autowired
	  public JobBuilderFactory jobBuilderFactory;

	  @Autowired
	  public StepBuilderFactory stepBuilderFactory;

	  static final String[] FIELDS = {"id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2"};

	  @Autowired
	  MatchRepository matchRepository;

	  @Bean
	  public FlatFileItemReader<MatchInputData> reader() {
	    return new FlatFileItemReaderBuilder<MatchInputData>()
	      .name("matchItemReader")
	      .resource(new ClassPathResource("match-data.csv"))
	      .delimited()
	      .names(FIELDS)
	      .linesToSkip(1)
	      .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInputData>() {{
	        setTargetType(MatchInputData.class);
	      }})
	      .build();
	  }

	  @Bean
	  public MatchDataProcessor processor() {
	    return new MatchDataProcessor();
	  }

	  @Bean
	  public RepositoryItemWriter<Match> writer(DataSource dataSource) {
		  RepositoryItemWriter<Match> riw = new RepositoryItemWriter<>();
		  riw.setRepository(matchRepository);
		  riw.setMethodName("save");

	    return riw;

	  }

	  @Bean
	  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	    return jobBuilderFactory.get("importUserJob")
	      .incrementer(new RunIdIncrementer())
	      .listener(listener)
	      .flow(step1)
	      .end()
	      .build();
	  }

	  @Bean
	  public Step step1(RepositoryItemWriter<Match> writer) {
	    return stepBuilderFactory.get("step1")
	      .<MatchInputData, Match> chunk(10)
	      .reader(reader())
	      .processor(processor())
	      .writer(writer)
	      .build();
	  }
}
