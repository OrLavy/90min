package NinetyMin.core.managed;

import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.caching.MatchesDataCacher;
import NinetyMin.core.configurations.UrlScrappingConfigurations;
import NinetyMin.core.scrapping.concreteScrappers.bbcSports.BbcFootBallScrapper;
import io.dropwizard.lifecycle.Managed;

import java.util.*;

/**
 * Created by orlavy on 1/30/17.
 */
public class ScrappingService implements Managed{
    private final UrlScrappingConfigurations urlScrappingConfigurations;
    Set<FootBallTournament> wantedLeagues;

    public ScrappingService(UrlScrappingConfigurations urlScrappingConfigurations){
        this.urlScrappingConfigurations = urlScrappingConfigurations;
        this.wantedLeagues = EnumSet.of(FootBallTournament.FA_CUP, FootBallTournament.PREMIER_LEAGUE);
    }

    public void scrapeAndCacheData(){
        MatchesDataCacher matchesDataCacher = CachaingService.INSTANCE().getMatchCacher();
        matchesDataCacher.clearMatches();

        List<FootBallMatch> matches = scrapeAllMatches(this.wantedLeagues);
        matchesDataCacher.addMatches(matches);
    }

    @Override
    public void start() throws Exception {
        System.out.println("Starting data scrapping");

        this.scrapeAndCacheData();

        System.out.println("Data scrapping finished");
    }

    @Override
    public void stop() throws Exception {

    }

    private List<FootBallMatch> scrapeAllMatches(Set<FootBallTournament> forLeagues){
        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        scrappedMatches.addAll(scrapeFixturesMatches(forLeagues));
        scrappedMatches.addAll(scrapePlayedMatches(forLeagues));

        return scrappedMatches;
    }

    private List<FootBallMatch> scrapeFixturesMatches(Set<FootBallTournament> forLeagues){
        BbcFootBallScrapper fixtureMatchesScrapper = new BbcFootBallScrapper(
                this.urlScrappingConfigurations.getBbcFixtures(),
                FootBallTournament.PREMIER_LEAGUE,
                MatchStatus.UPCOMING);

        return scrapeForGivenLeagues(fixtureMatchesScrapper, forLeagues);
    }

    private List<FootBallMatch> scrapePlayedMatches(Set<FootBallTournament> forLeagues){
        BbcFootBallScrapper playedMatchesScrapper = new BbcFootBallScrapper(
                this.urlScrappingConfigurations.getBbcResults(),
                FootBallTournament.PREMIER_LEAGUE,
                MatchStatus.PLAYED);

        return scrapeForGivenLeagues(playedMatchesScrapper, forLeagues);
    }

    private List<FootBallMatch> scrapeForGivenLeagues(BbcFootBallScrapper bbcFootBallScrapper, Set<FootBallTournament> forLeagues){
        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        for (FootBallTournament league : forLeagues){
            scrappedMatches.addAll(this.scrapeByLeague(bbcFootBallScrapper, league));
        }

        return scrappedMatches;
    }

    private List<FootBallMatch> scrapeByLeague(BbcFootBallScrapper bbcFootBallScrapper, FootBallTournament footBallTournament){
        bbcFootBallScrapper.setFootBallTournament(footBallTournament);
        return bbcFootBallScrapper.reScrapeData();
    }
}
