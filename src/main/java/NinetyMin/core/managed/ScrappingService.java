package NinetyMin.core.managed;

import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.caching.MatchesDataCacher;
import NinetyMin.core.configurations.UrlScrappingConfigurations;
import NinetyMin.core.scrapping.CachableWebScrapper;
import NinetyMin.core.scrapping.concreteScrappers.bbcSports.BbcFootBallScrapper;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 * Created by orlavy on 1/30/17.
 */
public class ScrappingService implements Managed{
    private final static Logger logger = LoggerFactory.getLogger(ScrappingService.class);
    private final UrlScrappingConfigurations urlScrappingConfigurations;
    Set<FootBallTournament> wantedLeagues;

    public ScrappingService(UrlScrappingConfigurations urlScrappingConfigurations){
        this.urlScrappingConfigurations = urlScrappingConfigurations;
        this.wantedLeagues = EnumSet.of(FootBallTournament.FA_CUP, FootBallTournament.PREMIER_LEAGUE);
    }

    @Override
    public void start() throws Exception {
        ScrappingService.logger.info("Starting data scrapping");
        this.scrapeAndCacheData();
        ScrappingService.logger.info("Data scrapping finished");
    }

    @Override
    public void stop() throws Exception {

    }

    /**
     * Clears the cache and repopulate it with freshly scrapped matches.
     */
    public void scrapeAndCacheData(){
        MatchesDataCacher matchesDataCacher = CachaingService.INSTANCE().getMatchCacher();
        matchesDataCacher.clearMatches();

        List<FootBallMatch> matches = scrapeAllMatches(this.wantedLeagues);
        matchesDataCacher.addMatches(matches);
    }

    private List<FootBallMatch> scrapeAllMatches(Set<FootBallTournament> forLeagues){
        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        getScrappersForUrls(createURLSet()).stream().forEach(scrapper ->{
            scrappedMatches.addAll(scrapeForGivenLeagues(scrapper,forLeagues));
        });

        return scrappedMatches;
    }

    /**
     * Uses given scrapper to scrape matches for all given leagues.
     * @param footBallScrapper
     * @param forLeagues
     * @return
     */
    private List<FootBallMatch> scrapeForGivenLeagues(
            CachableWebScrapper<List<FootBallMatch>,FootBallTournament> footBallScrapper,
            Set<FootBallTournament> forLeagues){

        List<FootBallMatch> scrappedMatches = new LinkedList<>();

        forLeagues.stream().forEach(league ->{
            scrappedMatches.addAll(footBallScrapper.reScrapeData(league));
        });

        return scrappedMatches;
    }


    private List<CachableWebScrapper<List<FootBallMatch>,FootBallTournament>>
    getScrappersForUrls(Set<URL> urls){

        UrlToMatchStatusMapper urlToMatchStatusMapper = new UrlToMatchStatusMapper(this.urlScrappingConfigurations);

        // Create new scrapper for each given url
        List<CachableWebScrapper<List<FootBallMatch>,FootBallTournament>> scrappers = new ArrayList<>();
        urls.stream().forEach(url ->{
            scrappers.add(new BbcFootBallScrapper(url, FootBallTournament.Any,
                    urlToMatchStatusMapper.getStatusFromURL(url)));
        });

        return scrappers;
    }

    private Set<URL> createURLSet(){
        // Hard coded, IRL will be created from an outside file.
        Set<URL> urls = new HashSet<>();
        urls.add(urlScrappingConfigurations.getBbcFixtures());
        urls.add(urlScrappingConfigurations.getBbcResults());
        return urls;
    }

    /**
     * Here for sake of simplicity in assignment.
     * IRL mapping will be found at a different service class and will be read from
     * an outside file.
     */
    private  class UrlToMatchStatusMapper{
        private Map<URL,MatchStatus> urlToStatus;

        private UrlToMatchStatusMapper(UrlScrappingConfigurations configurations){
            this.urlToStatus = this.createFilledMap(configurations);
        }

        private Map<URL,MatchStatus> createFilledMap(UrlScrappingConfigurations configurations){
            Map<URL,MatchStatus> map = new HashMap<>();
            map.put(configurations.getBbcResults(), MatchStatus.PLAYED);
            map.put(configurations.getBbcFixtures(),MatchStatus.UPCOMING);
            return map;
        }

        public MatchStatus getStatusFromURL(URL url){
            return this.urlToStatus.get(url);
        }

    }
}
