package NinetyMin.core.FootBallMatch;

/**
 * Created by orlavy on 1/31/17.
 */
public class FootBallMatch {
    String homeTeam;
    String awayTeam;
    Integer homeScore;
    Integer awayScore;
    MatchStatus status;
    FootBallTournament tournament;
    String startTime;

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public FootBallTournament getTournament() {
        return tournament;
    }

    public void setTournament(FootBallTournament tournament) {
        this.tournament = tournament;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
