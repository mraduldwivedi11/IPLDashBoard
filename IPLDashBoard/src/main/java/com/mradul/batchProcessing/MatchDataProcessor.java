package com.mradul.batchProcessing;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import com.mradul.data.Match;
import com.mradul.data.MatchInputData;

public class MatchDataProcessor implements ItemProcessor<MatchInputData	, Match> {

	@Override
	public Match process(final MatchInputData data) throws Exception {
		Match match = new Match();
		match.setId(Long.parseLong(data.getId()));
		match.setCity(data.getCity());

		//2008-04-18
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");//format to parse String date
		LocalDate date = LocalDate.parse(data.getDate(), dtf);
		match.setDate(date);

		match.setPlayerOfMatch(data.getPlayer_of_match());
		match.setVenue(data.getVenue());
		match.setTeam1(data.getTeam1());
		match.setTeam2(data.getTeam2());
		match.setTossWinner(data.getToss_winner());
		match.setTossDecision(data.getToss_decision());
		match.setMatch_winner(data.getWinner());
		match.setResult(data.getResult());
		match.setResultMargin(data.getResult_margin().equalsIgnoreCase("NA") ? 0 :Integer.parseInt(data.getResult_margin()));
		match.setEliminator(data.getEliminator().equalsIgnoreCase("N") ? false: true );
		match.setMethod(data.getMethod());
		match.setUmpire1(data.getUmpire1());
		match.setUmpire2(data.getUmpire2());

		return match;
	}

}