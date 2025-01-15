package org.imt.tournamentmaster.service.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.imt.tournamentmaster.model.match.Match;

public interface ReportingService {

    String report(Match match) throws JsonProcessingException;
}
