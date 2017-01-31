package NinetyMin.core.scrapping.concreteScrappers;

import NinetyMin.core.scrapping.AbstractWebScrapper;
import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by orlavy on 1/30/17.
 */
public class TeamsScrapper extends AbstractWebScrapper {

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
