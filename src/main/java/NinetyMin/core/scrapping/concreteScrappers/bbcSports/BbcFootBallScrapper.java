package NinetyMin.core.scrapping.concreteScrappers.bbcSports;

import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.scrapping.AbstractCacheableWebScrapper;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by orlavy on 1/31/17.
 */
public class BbcFootBallScrapper extends AbstractCacheableWebScrapper<List<FootBallMatch>,FootBallTournament> {

    FootBallTournament footBallTournament;
    MatchStatus matchStatus;

    public BbcFootBallScrapper(URL url, FootBallTournament league, MatchStatus matchStatus) {
        super(url);
        this.footBallTournament = league;
        this.matchStatus = matchStatus;
    }

    public FootBallTournament getFootBallTournament() {
        return footBallTournament;
    }

    public void setFootBallTournament(FootBallTournament footBallTournament) {
        this.footBallTournament = footBallTournament;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Override
    protected List<FootBallMatch> scrapeFromDoc(Jerry doc) {
        final List<FootBallMatch> matches = new ArrayList<>();
        RowToFootBallMatchMapper rowToFootBallMatchMapper = new RowToFootBallMatchMapper();
        doc.$(".table-stats tr.preview,tr.report").each(($this, index) ->{
            matches.add(rowToFootBallMatchMapper.apply($this));
            return true;
        });
        return matches;
    }

    @Override
    public List<FootBallMatch> reScrapeData(FootBallTournament footBallTournament) {
        this.setFootBallTournament(footBallTournament == null ?
                this.getFootBallTournament() : footBallTournament );
        return this.reScrapeData();
    }

    @Override
    protected Jerry crawlToTargetDocument(Jerry doc) {
        getBrowser().sendRequest(this.createFilterByTournamentRequest(doc));
        return Jerry.jerry(this.getBrowser().getPage());
    }

    private HttpRequest createFilterByTournamentRequest(Jerry doc){
        final HttpRequest request = HttpRequest.post(this.getUrl().toString());
        this.setFormParameters(request);
        return request;
    }

    private void setFormParameters(HttpRequest request){
        request.form("filter",this.findTournamentFilterValue(),true);
    }

    private String findTournamentFilterValue(){
        String leagueName = this.footBallTournament.getLeagueName().toLowerCase();
        return this.findTournamentOptionTag(leagueName).attr("value");
    }

    private Jerry findTournamentOptionTag(String leagueName){
        return this.getBaseDocument().$("#filter-nav option").filter(($this, index) ->
                $this.text().trim().toLowerCase().equals(leagueName)).first();
    }

    private class RowToFootBallMatchMapper implements Function<Jerry, FootBallMatch>{
        private final String UNDECIDED_TEAM = "undecided team";


        /**
         * All queries are hard coded for sake of assignment simplicity.
         * IRL will be read from an outside file.
         * @param tr
         * @return
         */
        @Override
        public FootBallMatch apply(Jerry tr) {
            FootBallMatch footBallMatch = new FootBallMatch();

            footBallMatch.setAwayTeam(extractAwayTeam(tr));
            footBallMatch.setHomeTeam(extractHomeTeam(tr));

            if (matchStatus == MatchStatus.PLAYED){
                footBallMatch.setAwayScore(extractAwayScore(tr));
                footBallMatch.setHomeScore(extractHomeScore(tr));
            }

            footBallMatch.setStatus(matchStatus);
            footBallMatch.setTournament(footBallTournament);

            footBallMatch.setStartTime(extractStartTime(tr));

            return footBallMatch;
        }

        private String extractTrimmedString(Jerry tr, String query,String ... alternativeQueries){
            String value = tr.$(query).text().trim();

            if (value.isEmpty()){
                for (String alterNativeQuery : alternativeQueries){
                    value = extractTrimmedString(tr, alterNativeQuery);
                    if (!value.isEmpty()){
                        break;
                    }
                }
            }

            return value;
        }

        private String extractHomeTeam(Jerry tr){
            return extractTrimmedString(tr,".team-home a", ".team-away");
        }

        private Integer extractHomeScore(Jerry tr){
            return extractGameScore(tr)[0];
        }

        private String extractAwayTeam(Jerry tr){
            return  extractTrimmedString(tr, ".team-away a", ".team-away");
        }

        private Integer extractAwayScore(Jerry tr){
            return extractGameScore(tr)[1];
        }

        private String extractStartTime(Jerry tr){
            return extractTrimmedString(tr,".time,.kickoff");
        }

        private int[] extractGameScore(Jerry tr){
            String scoreText = extractTrimmedString(tr,".score abbr");
            Stream<String> scoreStream = Arrays.stream(scoreText.trim().split("-"));
            return scoreStream.mapToInt(Integer::valueOf).toArray();
        }
    }
}


