package NinetyMin.api.match;

import NinetyMin.core.FootBallMatch.FootBallMatch;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import javax.validation.constraints.NotNull;

/**
 * Created by orlavy on 1/31/17.
 */
@JsonSnakeCase
public class MatchApi {
    @NotNull
    String homeTeam;
    @NotNull
    String awayTeam;
    Integer homeScore;
    Integer awayScore;
    @NotNull
    String status;
    @NotNull
    String tournament;
    @NotNull
    String startTime;

    public static MatchApi fromFootBallMatch(FootBallMatch footBallMatch){
        MatchApi api = new MatchApi();
        api.setHomeTeam(footBallMatch.getHomeTeam());
        api.setAwayTeam(footBallMatch.getAwayTeam());
        api.setHomeScore(footBallMatch.getHomeScore());
        api.setAwayScore(footBallMatch.getAwayScore());
        api.setStatus(footBallMatch.getStatus().toString());
        api.setTournament(footBallMatch.getTournament().leagueName);
        api.setStartTime(footBallMatch.getStartTime());
        return api;
    }

    @JsonProperty
    public String getHomeTeam() {
        return homeTeam;
    }

    @JsonProperty
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    @JsonProperty
    public String getAwayTeam() {
        return awayTeam;
    }

    @JsonProperty
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getAwayScore() {
        return awayScore;
    }

    @JsonProperty
    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    @JsonProperty
    public String getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty
    public String getTournament() {
        return tournament;
    }

    @JsonProperty
    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    @JsonProperty
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
