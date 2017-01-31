package NinetyMin.core.scrapping.concreteScrappers;

import NinetyMin.core.scrapping.AbstractWebScrapper;
import jodd.jerry.Jerry;

import java.net.URL;

/**
 * Created by orlavy on 1/31/17.
 */
public class PremiereLeagueScrapper extends AbstractWebScrapper {

    protected PremiereLeagueScrapper(URL urlToScrape) {
        super(urlToScrape);
    }

    @Override
    protected Object scrapeFromDoc(Jerry doc) {
        return null;
    }
}
