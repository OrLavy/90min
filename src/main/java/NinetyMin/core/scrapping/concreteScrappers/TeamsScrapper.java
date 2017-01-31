package NinetyMin.core.scrapping.concreteScrappers;

import NinetyMin.core.scrapping.AbstractCacheableWebScrapper;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

import java.net.URL;

/**
 * Created by orlavy on 1/30/17.
 */
public class TeamsScrapper extends AbstractCacheableWebScrapper {

    public TeamsScrapper(URL urlToScrape) {
        super(urlToScrape);
    }

    @Override
    protected Object scrapeFromDoc(Jerry doc) {
        doc.$("[id^='trc-20-premier-league']  tr.team > td.team-name").each(new JerryFunction() {
            @Override
            public Boolean onNode(Jerry $this, int index) {
                System.out.print(index + "." + $this.text() + " ");
                return true;
            }
        });
        return null;
    }
}
