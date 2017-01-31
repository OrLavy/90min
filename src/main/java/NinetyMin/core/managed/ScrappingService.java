package NinetyMin.core.managed;

import NinetyMin.core.FootBallMatch.FootBallLeague;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.configurations.UrlScrappingConfigurations;
import NinetyMin.core.scrapping.concreteScrappers.bbcSports.BbcFootBallScrapper;
import io.dropwizard.lifecycle.Managed;

import java.util.*;

/**
 * Created by orlavy on 1/30/17.
 */
public class ScrappingService implements Managed{
    private final UrlScrappingConfigurations urlScrappingConfigurations;
    Set<FootBallLeague> wantedLeagues;

    public ScrappingService(UrlScrappingConfigurations urlScrappingConfigurations){
        this.urlScrappingConfigurations = urlScrappingConfigurations;
        this.wantedLeagues = EnumSet.of(FootBallLeague.FA_CUP,FootBallLeague.PREMIER_LEAGUE);
    }

    @Override
    public void start() throws Exception {
        System.out.println("In application start up");

        List<FootBallMatch> matches = scrapeAllMatches(this.wantedLeagues);

        System.out.println("done");
    }

    @Override
    public void stop() throws Exception {

    }

    private List<FootBallMatch> scrapeAllMatches(Set<FootBallLeague> forLeagues){
        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        scrappedMatches.addAll(scrapeFixturesMatches(forLeagues));
        scrappedMatches.addAll(scrapePlayedMatches(forLeagues));

        return scrappedMatches;
    }

    private List<FootBallMatch> scrapeFixturesMatches(Set<FootBallLeague> forLeagues){
        BbcFootBallScrapper fixtureMatchesScrapper = new BbcFootBallScrapper(
                this.urlScrappingConfigurations.getBbcFixtures(),
                FootBallLeague.PREMIER_LEAGUE,
                MatchStatus.UpComing);

        return scrapeForGivenLeagues(fixtureMatchesScrapper, forLeagues);
    }

    private List<FootBallMatch> scrapePlayedMatches(Set<FootBallLeague> forLeagues){
        BbcFootBallScrapper playedMatchesScrapper = new BbcFootBallScrapper(
                this.urlScrappingConfigurations.getBbcResults(),
                FootBallLeague.PREMIER_LEAGUE,
                MatchStatus.Played);

        return scrapeForGivenLeagues(playedMatchesScrapper, forLeagues);
    }

    private List<FootBallMatch> scrapeForGivenLeagues(BbcFootBallScrapper bbcFootBallScrapper, Set<FootBallLeague> forLeagues){
        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        for (FootBallLeague league : forLeagues){
            scrappedMatches.addAll(this.scrapeByLeague(bbcFootBallScrapper, league));
        }

        return scrappedMatches;
    }

    private List<FootBallMatch> scrapeByLeague(BbcFootBallScrapper bbcFootBallScrapper, FootBallLeague footBallLeague){
        bbcFootBallScrapper.setFootBallLeague(footBallLeague);
        return bbcFootBallScrapper.reScrapeData();
    }
}
