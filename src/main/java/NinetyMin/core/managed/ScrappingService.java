package NinetyMin.core.managed;

import NinetyMin.core.configurations.UrlScrappingConfigurations;
import NinetyMin.core.scrapping.PlScrapper;
import NinetyMin.core.scrapping.concreteScrappers.TeamsScrapper;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by orlavy on 1/30/17.
 */
public class ScrappingService implements Managed{
    private final UrlScrappingConfigurations urlScrappingConfigurations;

    public ScrappingService(UrlScrappingConfigurations urlScrappingConfigurations){
        this.urlScrappingConfigurations = urlScrappingConfigurations;
    }

    @Override
    public void start() throws Exception {
        System.out.println("In application start up");
        //TeamsScrapper ts = new TeamsScrapper(this.urlScrappingConfigurations.);
        //ts.scrapeData();

        PlScrapper pls = new PlScrapper();

        //pls.doScrape();
    }

    @Override
    public void stop() throws Exception {

    }
}
